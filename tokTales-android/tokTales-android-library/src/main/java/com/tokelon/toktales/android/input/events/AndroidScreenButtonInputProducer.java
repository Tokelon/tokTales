package com.tokelon.toktales.android.input.events;

import com.tokelon.toktales.android.input.dispatch.IAndroidInputProducer;
import com.tokelon.toktales.android.input.events.IScreenButtonInputEvent.ScreenButtonInputEvent;

public class AndroidScreenButtonInputProducer {

	
	private final IAndroidInputProducer inputProducer;

	public AndroidScreenButtonInputProducer(IAndroidInputProducer inputProducer) {
		this.inputProducer = inputProducer;
	}
	
	
	public void invoke(int button, int action) {
		//logger.d(TAG, String.format("Button %s went down", TokelonTypeAInputs.inputToString(buttonCode)));

		inputProducer.postScreenButtonInput(getScreenButtonInputEvent(button, action));
	}
	
	
	protected IScreenButtonInputEvent getScreenButtonInputEvent(int button, int action) {
		ScreenButtonInputEvent screenButtonInputEvent = new IScreenButtonInputEvent.ScreenButtonInputEvent();
		return screenButtonInputEvent.set(button, action);
	}
	
	

	/* Implement regulation?
	 * 
	 */
	
	// Different regulate intervals for controls and buttons
	private static final int TIME_INTERVAL_CONTROL = 30;	//100
	private static final int TIME_INTERVAL_BUTTON = 100;
	
	private long lastTimeButton;
	
	private boolean regulateButton() {
		long now = System.currentTimeMillis();
		if(now - lastTimeButton >= TIME_INTERVAL_BUTTON) {
			lastTimeButton = now;
			return false;
		}
		else {
			return true;
		}
	}

}
