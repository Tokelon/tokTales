package com.tokelon.toktales.core.render;

import com.tokelon.toktales.core.util.IParams;

public interface IKeyedTextureManagerFactory {
	// TODO: Consider moving this into the interface itself

	
	public <T> IKeyedTextureManager<T> newKeyedTextureManager(Class<T> keyClass, IParams params);
	
}
