package com.tokelon.toktales.tools.assets.loader;

import java.util.concurrent.ExecutorService;

import javax.inject.Inject;
import javax.inject.Provider;

import org.slf4j.ILoggerFactory;

import com.tokelon.toktales.tools.assets.annotation.AssetLoader;
import com.tokelon.toktales.tools.assets.exception.AssetException;
import com.tokelon.toktales.tools.assets.loader.ILoaderTask.ILoaderTaskFactory;
import com.tokelon.toktales.tools.assets.reader.IAssetReader;
import com.tokelon.toktales.tools.assets.reader.IAssetReaderManager;

public class LoaderTaskExecutorServiceAssetLoader<T, K, O> extends AbstractExecutorServiceAssetLoader<T, K, O> {


	private final ILoaderTaskFactory<T, K, O> loaderTaskFactory;

	public LoaderTaskExecutorServiceAssetLoader(ILoggerFactory loggerFactory, IAssetReaderManager readerManager, IAssetDecoder<T, K, O> decoder, ILoaderTaskFactory<T, K, O> loaderTaskFactory) {
		super(loggerFactory, readerManager, decoder);
		
		this.loaderTaskFactory = loaderTaskFactory;
	}

	@Inject
	public LoaderTaskExecutorServiceAssetLoader(ILoggerFactory loggerFactory, IAssetReaderManager readerManager, IAssetDecoder<T, K, O> decoder, ILoaderTaskFactory<T, K, O> loaderTaskFactory, @AssetLoader Provider<ExecutorService> executorServiceProvider) {
		super(loggerFactory, readerManager, decoder, executorServiceProvider);
		
		this.loaderTaskFactory = loaderTaskFactory;
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
	public T load(K key, O options, IAssetReader reader, IAssetDecoder<? extends T, K, O> decoder) throws AssetException {
		ILoaderTask<T> loaderTask = createLoaderTask(key, options, reader, decoder);
		return loaderTask.load();
	}
	
}
