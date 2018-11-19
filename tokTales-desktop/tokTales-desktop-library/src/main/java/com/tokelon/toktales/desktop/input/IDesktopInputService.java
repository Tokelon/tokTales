package com.tokelon.toktales.desktop.input;

import com.tokelon.toktales.core.engine.input.IInputService;

public interface IDesktopInputService extends IInputService {

	@Override
	public IDesktopInputProducer getInputPoster();
	
	@Override
	public IDesktopInputConsumer getInputDispatcher();
	

	// registerInputDriver
	public void setInputDriver(IDesktopInputDriver inputDriver);
	
}
