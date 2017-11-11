package com.tokelon.toktales.core.render;

import com.tokelon.toktales.core.util.IParams;

public interface ITextureManagerFactory {

	// TODO: Consider moving this into the interface itself
	
	public ITextureManager newTextureManager(IParams params);
	
}
