package com.tokelon.toktales.android.input.dispatch;

import com.tokelon.toktales.android.input.IAndroidInputRegistration;
import com.tokelon.toktales.android.input.IAndroidInputRegistration.IScreenButtonCallback;
import com.tokelon.toktales.android.input.IAndroidInputRegistration.IScreenPointerCallback;
import com.tokelon.toktales.android.input.IAndroidInputRegistration.IScreenPressCallback;
import com.tokelon.toktales.core.engine.input.IInputConsumer;

public interface IAndroidInputConsumer extends IInputConsumer, IAndroidInputRegistration,
	IScreenButtonCallback, IScreenPointerCallback, IScreenPressCallback {

	
	public interface IAndroidInputConsumerFactory {
		
		public IAndroidInputConsumer create(IAndroidInputDispatch dispatch);
	}
	
}
