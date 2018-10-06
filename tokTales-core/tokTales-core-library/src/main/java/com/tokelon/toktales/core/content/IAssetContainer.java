package com.tokelon.toktales.core.content;

public interface IAssetContainer<T> {
	// TODO: Refactor - What is the point of freeAsset() ?
	
	
	public T getAsset();
	
	public void freeAsset(); // Rename to disposeAsset or implement IDisposable?
	
}
