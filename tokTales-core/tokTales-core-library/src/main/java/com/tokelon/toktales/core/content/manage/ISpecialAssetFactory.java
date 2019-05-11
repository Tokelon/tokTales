package com.tokelon.toktales.core.content.manage;

import com.tokelon.toktales.core.prog.annotation.CustomFunctionalInterface;

@CustomFunctionalInterface
public interface ISpecialAssetFactory<T> {
	
	
	public T create();
	
}
