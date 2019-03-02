package com.tokelon.toktales.core.content.manage;

import java.io.InputStream;

import com.tokelon.toktales.core.engine.content.ContentException;

public interface IAssetDecoder<T, K, O> {

	
	public T decode(InputStream inputstream, K key) throws ContentException;
	public T decode(InputStream inputstream, K key, O options) throws ContentException;
	
}
