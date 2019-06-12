package com.tokelon.toktales.core.content.manage;

import java.util.concurrent.ExecutorService;

import javax.inject.Inject;
import javax.inject.Provider;

import com.tokelon.toktales.core.content.manage.ILoaderTask.ILoaderTaskFactory;
import com.tokelon.toktales.core.engine.content.ContentException;
import com.tokelon.toktales.core.engine.inject.annotation.AssetLoader;
import com.tokelon.toktales.core.engine.log.ILogger;

public class LoaderTaskExecutorServiceAssetLoader<T, K, O> extends AbstractExecutorServiceAssetLoader<T, K, O> {

	public static final String TAG = "LoaderTaskExecutorServiceAssetLoader";

	
	private final ILoaderTaskFactory<T, K, O> loaderTaskFactory;

	public LoaderTaskExecutorServiceAssetLoader(ILogger logger, IAssetReaderManager readerManager, IAssetDecoder<T, K, O> decoder, ILoaderTaskFactory<T, K, O> loaderTaskFactory) {
		super(logger, readerManager, decoder);
		
		this.loaderTaskFactory = loaderTaskFactory;
	}

	@Inject
	public LoaderTaskExecutorServiceAssetLoader(ILogger logger, IAssetReaderManager readerManager, IAssetDecoder<T, K, O> decoder, ILoaderTaskFactory<T, K, O> loaderTaskFactory, @AssetLoader Provider<ExecutorService> executorServiceProvider) {
		super(logger, readerManager, decoder, executorServiceProvider);
		
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
	protected ILoaderTask<T> createLoaderTask(K key, O options, IAssetReader reader, IAssetDecoder<? extends T, K, O> decoder) {
		return loaderTaskFactory.create(key, options, reader, decoder);
	}
	
	
	@Override
	public T load(K key, O options, IAssetReader reader, IAssetDecoder<? extends T, K, O> decoder) throws ContentException {
		ILoaderTask<T> loaderTask = createLoaderTask(key, options, reader, decoder);
		return loaderTask.load();
	}
	
}
