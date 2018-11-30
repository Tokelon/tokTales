package com.tokelon.toktales.android.input;

import com.tokelon.toktales.android.input.dispatch.IAndroidInputProducer;
import com.tokelon.toktales.android.input.events.IScreenPointerInputEvent;
import com.tokelon.toktales.android.input.events.IScreenPointerInputEvent.ScreenPointerInputEvent;

public class AndroidScreenPointerInputProducer {

	
	private final IAndroidInputProducer inputProducer;

	public AndroidScreenPointerInputProducer(IAndroidInputProducer inputProducer) {
		this.inputProducer = inputProducer;
	}
	
	
	public void invoke(int pointerId, int action, double xpos, double ypos) {
		inputProducer.postScreenPointerInput(getScreenPointerInputEvent(pointerId, action, xpos, ypos));
	}
	
	
	protected IScreenPointerInputEvent getScreenPointerInputEvent(int pointerId, int action, double xpos, double ypos) {
		ScreenPointerInputEvent screenPointerInputEvent = new IScreenPointerInputEvent.ScreenPointerInputEvent();
		return screenPointerInputEvent.set(pointerId, action, xpos, ypos);
	}
	
}
