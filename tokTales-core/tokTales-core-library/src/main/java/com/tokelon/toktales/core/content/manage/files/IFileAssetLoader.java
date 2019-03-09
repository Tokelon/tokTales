package com.tokelon.toktales.core.content.manage.files;

import java.util.concurrent.ExecutorService;

import javax.inject.Provider;

import com.tokelon.toktales.core.content.manage.IAssetDecoder;
import com.tokelon.toktales.core.content.manage.IAssetLoader;
import com.tokelon.toktales.core.engine.content.ContentException;

public interface IFileAssetLoader<T, K extends IFileKey, O> extends IAssetLoader<T, K, O> {

	
	public <U, V, W> U loadWithCustomDecoder(V key, W options, IAssetDecoder<U, V, W> decoder, IFileKey fileKey) throws ContentException;
	
	
	public interface IFileAssetLoaderFactory {
		
		public <T, K extends IFileKey, O> IFileAssetLoader<T, K, O> create();
		public <T, K extends IFileKey, O> IFileAssetLoader<T, K, O> create(IAssetDecoder<T, K, O> decoder);
		public <T, K extends IFileKey, O> IFileAssetLoader<T, K, O> create(Provider<ExecutorService> executorServiceProvider);
		public <T, K extends IFileKey, O> IFileAssetLoader<T, K, O> create(IAssetDecoder<T, K, O> decoder, Provider<ExecutorService> executorServiceProvider);
	}
	
}
