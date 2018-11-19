package com.tokelon.toktales.android.input;

import com.tokelon.toktales.android.input.IAndroidInputDriver.InputScreenButtonCallback;
import com.tokelon.toktales.android.input.IAndroidInputDriver.InputScreenPointerCallback;
import com.tokelon.toktales.android.input.IAndroidInputDriver.InputScreenPressCallback;
import com.tokelon.toktales.core.engine.input.IInputProducer;

public interface IAndroidInputProducer extends IInputProducer {

	
	public InputScreenButtonCallback getScreenButtonInput();
	
	public InputScreenPressCallback getScreenPressInput();
	
	public InputScreenPointerCallback getScreenPointerInput();
	
}
