package com.tokelon.toktales.core.content.manage;

import java.util.concurrent.ExecutorService;

import javax.inject.Provider;

public interface IExecutorServiceAssetLoader<T, K, O> extends IAssetLoader<T, K, O> {

	
	public Provider<ExecutorService> getExecutorServiceProvider();
	public ExecutorService getCurrentExecutorService();
	
	
	/* TODO: Add with default binding?
	public interface IExecutorServiceAssetLoaderFactory {
		
		public <T, K, O> IExecutorServiceAssetLoader<T, K, O> create(IAssetDecoder<T, K, O> decoder);
		public <T, K, O> IExecutorServiceAssetLoader<T, K, O> create(IAssetDecoder<T, K, O> decoder, Provider<ExecutorService> executorServiceProvider);
	}
	*/
	
}
