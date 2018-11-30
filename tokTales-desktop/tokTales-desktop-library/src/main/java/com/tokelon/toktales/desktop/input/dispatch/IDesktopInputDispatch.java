package com.tokelon.toktales.desktop.input.dispatch;

import com.tokelon.toktales.core.engine.input.IInputDispatch;

public interface IDesktopInputDispatch extends IInputDispatch {

	
	@Override
	public IDesktopInputProducer getInputProducer();
	
	@Override
	public IDesktopInputConsumer getInputConsumer();
	
}
