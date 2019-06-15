package com.tokelon.toktales.tools.registry;

public interface IStringRegistry<V> extends IRegistry<String, V> {


	@Override
	public IStringRegistry<V> copy();
	
	@Override
	public IStringRegistry<V> createChildRegistry();
	
}
