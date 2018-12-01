package com.tokelon.toktales.android.input.dispatch;

import com.tokelon.toktales.android.input.dispatch.IAndroidInputRegistration.IScreenButtonCallback;
import com.tokelon.toktales.android.input.dispatch.IAndroidInputRegistration.IScreenPointerCallback;
import com.tokelon.toktales.android.input.dispatch.IAndroidInputRegistration.IScreenPressCallback;
import com.tokelon.toktales.core.engine.input.IInputConsumer;

public interface IAndroidInputConsumer extends IInputConsumer, IAndroidInputRegistration,
	IScreenButtonCallback, IScreenPointerCallback, IScreenPressCallback {

	
	public interface IAndroidInputConsumerFactory {
		
		public IAndroidInputConsumer create(IAndroidInputDispatch dispatch);
	}
	
}
