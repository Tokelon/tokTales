package com.tokelon.toktales.core.content.manage;

import com.tokelon.toktales.core.engine.content.ContentException;
import com.tokelon.toktales.tools.core.annotations.CompatFunctionalInterface;

@CompatFunctionalInterface
public interface ILoaderTask<T> {
	
	
	public T load() throws ContentException;
	
	
	public interface ILoaderTaskFactory<T, K, O> {
		
		public O getDefaultOptions();
		
		public ILoaderTask<T> create(K key, O options, IAssetReader reader, IAssetDecoder<? extends T, K, O> decoder);
	}
	
}
