package com.tokelon.toktales.core.content.manage;

import java.util.concurrent.ExecutorService;

import javax.inject.Provider;

import com.tokelon.toktales.core.content.manage.ILoaderTask.ILoaderTaskFactory;
import com.tokelon.toktales.core.engine.content.ContentException;
import com.tokelon.toktales.core.engine.log.ILogger;

public class LoaderTaskExecutorServiceAssetLoader<T, K, O> extends AbstractExecutorServiceAssetLoader<T, K, O> {

	public static final String TAG = "LoaderTaskExecutorServiceAssetLoader";

	
	private final ILoaderTaskFactory<T, K, O> loaderTaskFactory;

	public LoaderTaskExecutorServiceAssetLoader(ILogger logger, IAssetDecoder<T, K, O> decoder, ILoaderTaskFactory<T, K, O> loaderTaskFactory) {
		super(logger, decoder);
		
		this.loaderTaskFactory = loaderTaskFactory;
	}

	public LoaderTaskExecutorServiceAssetLoader(ILogger logger, IAssetDecoder<T, K, O> decoder, ILoaderTaskFactory<T, K, O> loaderTaskFactory, Provider<ExecutorService> executorServiceProvider) {
		super(logger,decoder, executorServiceProvider);
		
		this.loaderTaskFactory = loaderTaskFactory;
	}
	
	
	@Override
	public String getTag() {
		return TAG;
	}

	@Override
	protected O getDefaultOptions() {
		return loaderTaskFactory.getDefaultOptions();
	}
	
	@Override
	protected ILoaderTask<T> createLoaderTask(K key, O options, IAssetDecoder<? extends T, K, O> decoder) {
		return loaderTaskFactory.create(key, options, decoder);
	}
	
	
	@Override
	public T load(K key, O options, IAssetDecoder<? extends T, K, O> decoder) throws ContentException {
		ILoaderTask<T> loaderTask = createLoaderTask(key, options, decoder);
		return loaderTask.load();
	}
	
}
