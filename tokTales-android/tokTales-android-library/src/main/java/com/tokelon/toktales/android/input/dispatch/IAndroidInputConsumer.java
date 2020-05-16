package com.tokelon.toktales.android.input.dispatch;

import com.tokelon.toktales.core.engine.input.IInputConsumer;

public interface IAndroidInputConsumer extends IInputConsumer, IAndroidInputRegistration {


	public IScreenButtonCallback getMasterScreenButtonCallback();
	public IScreenPointerCallback getMasterScreenPointerCallback();
	public IScreenPressCallback getMasterScreenPressCallback();


	public interface IAndroidInputConsumerFactory {

		public IAndroidInputConsumer create(IAndroidInputDispatch dispatch);
	}

}
