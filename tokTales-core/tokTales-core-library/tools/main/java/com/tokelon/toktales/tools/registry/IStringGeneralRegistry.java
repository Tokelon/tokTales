package com.tokelon.toktales.tools.registry;

public interface IStringGeneralRegistry extends IGeneralRegistry<String>, IStringRegistry<Object> {

	
	@Override
	public IStringGeneralRegistry copy();
	
	@Override
	public IStringGeneralRegistry createChildRegistry();
	
}
