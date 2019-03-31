package com.tokelon.toktales.core.content.manage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.inject.Provider;

import com.tokelon.toktales.core.engine.content.ContentException;
import com.tokelon.toktales.core.engine.log.ILogger;

import java9.util.concurrent.CompletableFuture;

public abstract class AbstractExecutorServiceAssetLoader<T, K, O> implements IExecutorServiceAssetLoader<T, K, O> {
	// Use guava deamon executor and implement starting and stopping (maybe with guava service?)
	// TODO: Handle case when findReader returns null?
	
	public static final String BASE_TAG = "AbstractExecutorServiceAssetLoader";

	private boolean logDebug = false;
	
	
	private ExecutorService currentExecutorService;
	
	private final Lock executorLock;
	
	private final IAssetReaderManager readerManager;
	
	private final Map<K, CompletableFuture<T>> pendingFutures;
	private final List<Runnable> waitingTasks;

	private final ILogger logger;
	private final IAssetDecoder<? extends T, K, O> decoder;
	private final Provider<ExecutorService> executorServiceProvider;

	protected AbstractExecutorServiceAssetLoader(ILogger logger, IAssetReaderManager readerManager, IAssetDecoder<? extends T, K, O> decoder) {
		this(logger, readerManager, decoder, new DefaultExecutorServiceProvider());
	}

	protected AbstractExecutorServiceAssetLoader(ILogger logger, IAssetReaderManager readerManager, IAssetDecoder<? extends T, K, O> decoder, Provider<ExecutorService> executorServiceProvider) {
		if(decoder == null || executorServiceProvider == null) {
			throw new NullPointerException();
		}

		this.logger = logger;
		this.readerManager = readerManager;
		this.decoder = decoder;
		this.executorServiceProvider = executorServiceProvider;

		this.executorLock = new ReentrantLock(true);
		this.pendingFutures = Collections.synchronizedMap(new HashMap<>());
		this.waitingTasks = Collections.synchronizedList(new ArrayList<>());
	}

	
	protected abstract O getDefaultOptions();

	
	protected List<Runnable> getWaitingTasks() {
		return waitingTasks;
	}
	
	protected Map<K, CompletableFuture<T>> getPendingFutures() {
		return pendingFutures;
	}
	
	protected ILogger getLogger() {
		return logger;
	}
	
	protected void setLogDebugEnabled(boolean enabled) {
		this.logDebug = enabled;
	}
	
	protected boolean isLogDebugEnabled() {
		return logDebug;
	}
	
	public String getTag() {
		return BASE_TAG;
	}
	
	@Override
	public Provider<ExecutorService> getExecutorServiceProvider() {
		return executorServiceProvider;
	}
	
	@Override
	public ExecutorService getCurrentExecutorService() {
		return currentExecutorService;
	}

	@Override
	public IAssetDecoder<? extends T, K, O> getDefaultDecoder() {
		return decoder;
	}
	
	@Override
	public IAssetReaderManager getReaderManager() {
		return readerManager;
	}
	
	
	@Override
	public void start() {
		executorLock.lock();
		try {
			this.currentExecutorService = executorServiceProvider.get();
			logger.i(getTag(), "Starting asset loader with executor service: " + currentExecutorService);
			
			synchronized (waitingTasks) {
				logger.i(getTag(), String.format("Adding %d waiting tasks to executor service", waitingTasks.size()));
				
				for(Runnable task: waitingTasks) {
					CompletableFuture.runAsync(task, currentExecutorService);
				}
				
				waitingTasks.clear();
			}
		}
		finally {
			executorLock.unlock();
		}
	}

	@Override
	public void stop() {
		executorLock.lock();
		try {
			if(currentExecutorService == null) {
				logger.i(getTag(), "Stop was called but no executor service is running");
				return;
			}

			List<Runnable> unfinishedTasks = this.currentExecutorService.shutdownNow();
			waitingTasks.addAll(unfinishedTasks);
			
			logger.i(getTag(), String.format("Stopping asset loader and collecting %d unfinished tasks", unfinishedTasks.size()));
		}
		finally {
			executorLock.unlock();
		}
	}

	
	protected ILoaderTask<T> createLoaderTask(K key, O options, IAssetReader reader, IAssetDecoder<? extends T, K, O> decoder) {
		// Really return new object every time? Any way to pass parameters in call?
		return () -> {
			return load(key, options, reader, decoder);
		};
	}

	protected Runnable createTaskRunnable(ILoaderTask<T> loaderTask, CompletableFuture<T> wrapperFuture) {
		return () -> {
			try {
				T result = loaderTask.load();
				wrapperFuture.complete(result);
			} catch (ContentException ce) {
				wrapperFuture.completeExceptionally(ce);
			} //catch (InterruptedException ie) // Needed?
		};
	}
	
	protected IAssetReader findReader(K key, O options) {
		return readerManager.findReader(key, options);
	}

	@Override
	public CompletableFuture<T> enqueue(K key) {
		return enqueue(key, getDefaultOptions(), findReader(key, getDefaultOptions()), getDefaultDecoder());
	}

	@Override
	public CompletableFuture<T> enqueue(K key, O options) {
		return enqueue(key, options, findReader(key, options), getDefaultDecoder());
	}
	
	@Override
	public CompletableFuture<T> enqueue(K key, IAssetReader reader) {
		return enqueue(key, getDefaultOptions(), reader, getDefaultDecoder());
	}
	
	@Override
	public CompletableFuture<T> enqueue(K key, O options, IAssetReader reader) {
		return enqueue(key, options, reader, getDefaultDecoder());
	}

	@Override
	public CompletableFuture<T> enqueue(K key, IAssetDecoder<? extends T, K, O> decoder) {
		return enqueue(key, getDefaultOptions(), findReader(key, getDefaultOptions()), decoder);
	}

	@Override
	public CompletableFuture<T> enqueue(K key, O options, IAssetDecoder<? extends T, K, O> decoder) {
		return enqueue(key, options, findReader(key, options), decoder);
	}
	
	@Override
	public CompletableFuture<T> enqueue(K key, O options, IAssetReader reader, IAssetDecoder<? extends T, K, O> decoder) {
		synchronized (pendingFutures) {
			CompletableFuture<T> cachedFuture = pendingFutures.get(key);
			if(cachedFuture != null) {
				if(logDebug) logger.d(getTag(), "Loader future already exists for key: " + key);
				return cachedFuture;
			}

			
			if(logDebug) logger.d(getTag(), String.format("Creating loader task for [key=%s, options=%s, reader=%s, decoder=%s]", key, options, reader, decoder));
			ILoaderTask<T> loaderTask = createLoaderTask(key, options, reader, decoder);
			
			CompletableFuture<T> wrapperFuture = new CompletableFuture<>();
			Runnable taskRunner = createTaskRunnable(loaderTask, wrapperFuture);
			
			executorLock.lock();
			try {
				if(currentExecutorService == null) {
					waitingTasks.add(taskRunner);
					if(logDebug) logger.d(getTag(), "Loader task was added to waiting list for key: " + key);
				}
				else {
					CompletableFuture.runAsync(taskRunner, currentExecutorService);
					if(logDebug) logger.d(getTag(), "Loader task was started with executor for key: " + key);
				}
			}
			finally {
				executorLock.unlock();
			}
			
			// Always add to pending futures, whether it's waiting or in queue
			pendingFutures.put(key, wrapperFuture);
			if(logDebug) logger.d(getTag(), "Loader future was added for key: " + key);

			// Will be run by the current thread if already finished, otherwise by the thread that calls complete()
			wrapperFuture.whenComplete((result, exception) -> {
				pendingFutures.remove(key); // Remove pending task regardless of result
				
				if(exception == null) {
					if(logDebug) logger.d(getTag(), "Loader wrapper future completed for key: " + key);
				}
				else {
					logger.w(getTag(), "Loader wrapper future completed with exception: " + exception);
				}
			});

			return wrapperFuture;
		}
	}


	@Override
	public T load(K key) throws ContentException {
		return load(key, getDefaultOptions(), findReader(key, getDefaultOptions()), getDefaultDecoder());
	}

	@Override
	public T load(K key, O options) throws ContentException {
		return load(key, options, findReader(key, options), getDefaultDecoder());
	}
	
	@Override
	public T load(K key, IAssetReader reader) throws ContentException {
		return load(key, getDefaultOptions(), reader, getDefaultDecoder());
	}
	
	@Override
	public T load(K key, O options, IAssetReader reader) throws ContentException {
		return load(key, options, reader, getDefaultDecoder());
	}
	
	@Override
	public T load(K key, O options, IAssetDecoder<? extends T, K, O> decoder) throws ContentException {
		return load(key, options, findReader(key, options), decoder);
	}
	
	@Override
	public T load(K key, IAssetDecoder<? extends T, K, O> decoder) throws ContentException {
		return load(key, getDefaultOptions(), findReader(key, getDefaultOptions()), decoder);
	}

}
