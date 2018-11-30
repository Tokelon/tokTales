package com.tokelon.toktales.android.input.dispatch;

import java.util.Set;

import com.tokelon.toktales.android.input.AndroidInputRegistration;
import com.tokelon.toktales.android.input.events.IScreenButtonInputEvent;
import com.tokelon.toktales.android.input.events.IScreenPointerInputEvent;
import com.tokelon.toktales.android.input.events.IScreenPressInputEvent;
import com.tokelon.toktales.core.engine.input.IInputCallback;
import com.tokelon.toktales.core.engine.input.IInputEvent;

public class AndroidInputConsumer extends AndroidInputRegistration implements IAndroidInputConsumer {

	
	@Override
	public boolean handle(IInputEvent event) {
		boolean wasHandled = false;
		
		Set<IInputCallback> generalCallbackSet = getGeneralInputCallbackSet(); 
		synchronized (generalCallbackSet) {
			for(IInputCallback callback: generalCallbackSet) {
				wasHandled = callback.handle(event) || wasHandled;
			}
		}
		
		return wasHandled;
	}

	@Override
	public boolean handleScreenButtonInput(IScreenButtonInputEvent event) {
		boolean wasHandled = false;
		
		Set<IScreenButtonCallback> screenButtonCallbackSet = getScreenButtonCallbackSet(); 
		synchronized (screenButtonCallbackSet) {
			for(IScreenButtonCallback callback: screenButtonCallbackSet) {
				wasHandled = callback.handleScreenButtonInput(event) || wasHandled;
			}
		}
		
		return wasHandled;
	}


	@Override
	public boolean handleScreenPressInput(IScreenPressInputEvent event) {
		boolean wasHandled = false;
		
		Set<IScreenPressCallback> screenPressCallbackSet = getScreenPressCallbackSet();
		synchronized (screenPressCallbackSet) {
			for(IScreenPressCallback callback: screenPressCallbackSet) {
				wasHandled = callback.handleScreenPressInput(event) || wasHandled;
			}
		}
		
		return wasHandled;
	}
	
	@Override
	public boolean handleScreenPointerInput(IScreenPointerInputEvent event) {
		boolean wasHandled = false;
		
		Set<IScreenPointerCallback> screenPointerCallbackSet = getScreenPointerCallbackSet();
		synchronized (screenPointerCallbackSet) {
			for(IScreenPointerCallback callback: screenPointerCallbackSet) {
				wasHandled = callback.handleScreenPointerInput(event) || wasHandled;
			}
		}
		
		return wasHandled;
	}
	
	
	
	public static class AndroidInputConsumerFactory implements IAndroidInputConsumerFactory {

		@Override
		public IAndroidInputConsumer create(IAndroidInputDispatch dispatch) {
			return new AndroidInputConsumer();
		}
	}
	
}
