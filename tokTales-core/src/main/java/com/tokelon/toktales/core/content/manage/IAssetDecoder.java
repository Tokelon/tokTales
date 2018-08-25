package com.tokelon.toktales.core.content.manage;

import java.io.InputStream;

public interface IAssetDecoder<T, K, O> {

	
	public T decode(InputStream inputstream, K key);
	public T decode(InputStream inputstream, K key, O options);
	
}
