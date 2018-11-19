package com.tokelon.toktales.android.input;

import com.tokelon.toktales.core.engine.input.IInputService;

public interface IAndroidInputService extends IInputService {

	@Override
	public IAndroidInputProducer getInputPoster();
	
	@Override
	public IAndroidInputConsumer getInputDispatcher();


	// registerInputDriver
	public void setInputDriver(IAndroidInputDriver inputDriver);

	
}
