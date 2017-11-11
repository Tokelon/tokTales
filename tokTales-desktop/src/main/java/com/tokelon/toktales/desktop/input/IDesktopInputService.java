package com.tokelon.toktales.desktop.input;

import com.tokelon.toktales.core.engine.input.IInputService;

public interface IDesktopInputService extends IInputService {

	@Override
	public IDesktopInputPoster getInputPoster();
	
	@Override
	public IDesktopInputDispatcher getInputDispatcher();
	

	// registerInputDriver
	public void setInputDriver(IDesktopInputDriver inputDriver);
	
}
