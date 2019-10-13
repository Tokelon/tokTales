package com.tokelon.toktales.tools.assets.manager;

import com.tokelon.toktales.tools.core.annotations.CompatFunctionalInterface;

@CompatFunctionalInterface
public interface ISpecialAssetFactory<T> {
	
	
	public T create();
	
}
