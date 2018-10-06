package com.tokelon.toktales.core.content;

public class AssetContainer<T extends IDisposable> implements IAssetContainer<T> {

	private final T asset;
	
	public AssetContainer(T asset) {
		this.asset = asset;
	}
	
	public T getAsset() {
		return asset;
	}
	
	@Override
	public void freeAsset() {
		asset.dispose();
	}
	
}
