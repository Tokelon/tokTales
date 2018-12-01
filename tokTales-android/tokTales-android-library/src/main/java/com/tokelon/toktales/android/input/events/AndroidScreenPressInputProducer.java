package com.tokelon.toktales.android.input.events;

import com.tokelon.toktales.android.input.dispatch.IAndroidInputProducer;
import com.tokelon.toktales.android.input.events.IScreenPressInputEvent.ScreenPressInputEvent;

public class AndroidScreenPressInputProducer {

	
	private final IAndroidInputProducer inputProducer;

	public AndroidScreenPressInputProducer(IAndroidInputProducer inputProducer) {
		this.inputProducer = inputProducer;
	}
	
	
	public void invoke(double xpos, double ypos) {
		inputProducer.postScreenPressInput(getScreenPressInputEvent(xpos, ypos));
	}
	
	
	protected IScreenPressInputEvent getScreenPressInputEvent(double xpos, double ypos) {
		ScreenPressInputEvent screenPressInputEvent = new IScreenPressInputEvent.ScreenPressInputEvent();
		return screenPressInputEvent.set(xpos, ypos);
	}
	
}
