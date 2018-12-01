package com.tokelon.toktales.android.input.dispatch;

import java.util.Set;

import com.tokelon.toktales.android.input.events.IScreenButtonInputEvent;
import com.tokelon.toktales.android.input.events.IScreenPointerInputEvent;
import com.tokelon.toktales.android.input.events.IScreenPressInputEvent;
import com.tokelon.toktales.core.engine.input.IInputCallback;
import com.tokelon.toktales.core.engine.input.IInputEvent;

public class AndroidInputConsumer extends AndroidInputRegistration implements IAndroidInputConsumer {

	
	@Override
	public boolean handle(IInputEvent event) {
		boolean handledHere = false;
		
		Set<IInputCallback> generalCallbackSet = getGeneralInputCallbackSet(); 
		synchronized (generalCallbackSet) {
			for(IInputCallback callback: generalCallbackSet) {
				boolean callbackHandled = callback.handle(event);
				handledHere = callbackHandled || handledHere;
				event.markHandledIf(callbackHandled);
			}
		}
		
		return handledHere;
	}

	@Override
	public boolean handleScreenButtonInput(IScreenButtonInputEvent event) {
		boolean handledHere = false;
		
		Set<IScreenButtonCallback> screenButtonCallbackSet = getScreenButtonCallbackSet(); 
		synchronized (screenButtonCallbackSet) {
			for(IScreenButtonCallback callback: screenButtonCallbackSet) {
				boolean callbackHandled = callback.handleScreenButtonInput(event);
				handledHere = callbackHandled || handledHere;
				event.markHandledIf(callbackHandled);
			}
		}
		
		return handledHere;
	}


	@Override
	public boolean handleScreenPressInput(IScreenPressInputEvent event) {
		boolean handledHere = false;
		
		Set<IScreenPressCallback> screenPressCallbackSet = getScreenPressCallbackSet();
		synchronized (screenPressCallbackSet) {
			for(IScreenPressCallback callback: screenPressCallbackSet) {
				boolean callbackHandled = callback.handleScreenPressInput(event);
				handledHere = callbackHandled || handledHere;
				event.markHandledIf(callbackHandled);
			}
		}
		
		return handledHere;
	}
	
	@Override
	public boolean handleScreenPointerInput(IScreenPointerInputEvent event) {
		boolean handledHere = false;
		
		Set<IScreenPointerCallback> screenPointerCallbackSet = getScreenPointerCallbackSet();
		synchronized (screenPointerCallbackSet) {
			for(IScreenPointerCallback callback: screenPointerCallbackSet) {
				boolean callbackHandled = callback.handleScreenPointerInput(event);
				handledHere = callbackHandled || handledHere;
				event.markHandledIf(callbackHandled);
			}
		}
		
		return handledHere;
	}
	
	
	
	public static class AndroidInputConsumerFactory implements IAndroidInputConsumerFactory {

		@Override
		public IAndroidInputConsumer create(IAndroidInputDispatch dispatch) {
			return new AndroidInputConsumer();
		}
	}
	
}
