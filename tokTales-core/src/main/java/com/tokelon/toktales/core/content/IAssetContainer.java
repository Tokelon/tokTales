package com.tokelon.toktales.core.content;

public interface IAssetContainer<T> {

	public T getAsset();
	
	public void freeAsset();
	
}
