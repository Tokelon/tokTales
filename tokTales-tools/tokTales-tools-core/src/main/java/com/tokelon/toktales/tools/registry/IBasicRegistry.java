package com.tokelon.toktales.tools.registry;

public interface IBasicRegistry extends IGeneralRegistry<Object>, IGenericRegistry<Object> {


	@Override
	public IBasicRegistry copy();
	
	@Override
	public IBasicRegistry createChildRegistry();
	
}
