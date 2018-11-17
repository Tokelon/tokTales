package com.tokelon.toktales.core.content.manage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.inject.Provider;

import com.tokelon.toktales.core.engine.content.ContentException;
import com.tokelon.toktales.core.engine.log.ILogger;

import java9.util.concurrent.CompletableFuture;

public abstract class AbstractExecutorServiceAssetLoader<T, K, O> implements IExecutorServiceAssetLoader<T, K, O> {
	// Use guava deamon executor and implement starting and stopping (maybe with guava service?)
	
	public static final String BASE_TAG = "AbstractExecutorServiceAssetLoader";

	private boolean logDebug = false;
	
	
	private ExecutorService currentExecutorService;
	
	private final Lock executorLock;
	
	private final Map<K, CompletableFuture<T>> pendingFutures;
	private final List<Runnable> waitingTasks;

	private final ILogger logger;
	private final IAssetDecoder<? extends T, K, O> decoder;
	private final Provider<ExecutorService> executorServiceProvider;

	protected AbstractExecutorServiceAssetLoader(ILogger logger, IAssetDecoder<? extends T, K, O> decoder) {
		this(logger, decoder, defaultExecutorServiceProvider());
	}

	protected AbstractExecutorServiceAssetLoader(ILogger logger, IAssetDecoder<? extends T, K, O> decoder, Provider<ExecutorService> executorServiceProvider) {
		if(decoder == null || executorServiceProvider == null) {
			throw new NullPointerException();
		}

		this.logger = logger;
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

	
	protected ILoaderTask<T> createLoaderTask(K key, O options, IAssetDecoder<? extends T, K, O> decoder) {
		// Really return new object every time? Any way to pass parameters in call?
		return () -> {
			return load(key, options, decoder);
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

	@Override
	public CompletableFuture<T> enqueue(K key) {
		return enqueue(key, getDefaultOptions(), this.decoder);
	}

	@Override
	public CompletableFuture<T> enqueue(K key, IAssetDecoder<? extends T, K, O> decoder) {
		return enqueue(key, getDefaultOptions(), decoder);
	}

	@Override
	public CompletableFuture<T> enqueue(K key, O options) {
		return enqueue(key, options, this.decoder);
	}

	@Override
	public CompletableFuture<T> enqueue(K key, O options, IAssetDecoder<? extends T, K, O> decoder) {
		synchronized (pendingFutures) {
			CompletableFuture<T> cachedFuture = pendingFutures.get(key);
			if(cachedFuture != null) {
				if(logDebug) logger.d(getTag(), "Loader future already exists for key: " + key);
				return cachedFuture;
			}

			
			if(logDebug) logger.d(getTag(), String.format("Creating loader task for [key=%s, options=%s, decoder=%s]", key, options, decoder));
			ILoaderTask<T> loaderTask = createLoaderTask(key, options, decoder);
			
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
		return load(key, this.decoder);
	}

	@Override
	public T load(K key, IAssetDecoder<? extends T, K, O> decoder) throws ContentException {
		return load(key, getDefaultOptions(), decoder);
	}

	@Override
	public T load(K key, O options) throws ContentException {
		return load(key, options, this.decoder);
	}


	protected static Provider<ExecutorService> defaultExecutorServiceProvider() {
		// TODO: Use Guava executor ?
		//MoreExecutors.getExitingExecutorService(new ThreadPoolExecutor(1, 1, keepAliveTime, unit, workQueue), 0, TimeUnit.MILLISECONDS);
		return () -> Executors.newSingleThreadExecutor(new ExecutorServiceDaemonThreadFactory());
	}
	
	protected static class ExecutorServiceDaemonThreadFactory implements ThreadFactory {
		private final ThreadFactory defaultThreadFactory = Executors.defaultThreadFactory();
		
		public Thread newThread(Runnable runnable) {
			Thread newThread = defaultThreadFactory.newThread(runnable);
			newThread.setDaemon(true);
			
			return newThread;
		};
	}

}
