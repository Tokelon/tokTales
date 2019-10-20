package com.tokelon.toktales.tools.assets.loader;

import com.tokelon.toktales.tools.assets.exception.AssetException;
import com.tokelon.toktales.tools.assets.reader.IAssetReader;
import com.tokelon.toktales.tools.core.annotations.compatibility.CompatFunctionalInterface;

@CompatFunctionalInterface
public interface ILoaderTask<T> {
	
	
	public T load() throws AssetException;
	
	
	public interface ILoaderTaskFactory<T, K, O> {
		
		public O getDefaultOptions();
		
		public ILoaderTask<T> create(K key, O options, IAssetReader reader, IAssetDecoder<? extends T, K, O> decoder);
	}
	
}
