package com.tokelon.toktales.core.content.manage;

import javax.inject.Inject;

public class DefaultSpecialAssetManager<T> implements ISpecialAssetManager<T> {
	// Use getters for inheritance support?
	
	private final T specialAssetNull;
	private final T specialAssetLoadError;

	@Inject
	public DefaultSpecialAssetManager(ISpecialAssetFactory<T> specialAssetFactory) {
		this.specialAssetNull = specialAssetFactory.create();
		this.specialAssetLoadError = specialAssetFactory.create();
	}

	
	@Override
	public T getSpecialAssetNull() {
		return specialAssetNull;
	}
	
	@Override
	public T getSpecialAssetLoadError() {
		return specialAssetLoadError;
	}
	
	
	@Override
	public boolean isAssetValid(T asset) {
		return asset != null && // Is this okay?
				asset != specialAssetNull &&
				asset != specialAssetLoadError;
	}
	
	@Override
	public boolean isAssetSpecial(T asset) {
		return asset == specialAssetNull ||
				asset == specialAssetLoadError;
	}

}
