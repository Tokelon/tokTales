package com.tokelon.toktales.core.engine.input;

public interface ICustomInputCallback extends IInputCallback {

	
	@Override
	public default boolean handle(IInputEvent event) {
		return false;
	}
	
}
