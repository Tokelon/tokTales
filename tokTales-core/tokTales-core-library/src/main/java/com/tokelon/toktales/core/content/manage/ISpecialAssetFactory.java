package com.tokelon.toktales.core.content.manage;

import com.tokelon.toktales.tools.core.annotations.CompatFunctionalInterface;

@CompatFunctionalInterface
public interface ISpecialAssetFactory<T> {
	
	
	public T create();
	
}
