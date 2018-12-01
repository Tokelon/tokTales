package com.tokelon.toktales.android.input.events;

import com.tokelon.toktales.android.input.dispatch.IAndroidInputProducer;
import com.tokelon.toktales.android.input.events.IScreenButtonInputEvent.ScreenButtonInputEvent;
import com.tokelon.toktales.core.util.IObjectPool;

public class AndroidScreenButtonInputProducer {

	
	private final IAndroidInputProducer inputProducer;
	private final IObjectPool<ScreenButtonInputEvent> eventPool;

	public AndroidScreenButtonInputProducer(IAndroidInputProducer inputProducer, IObjectPool<ScreenButtonInputEvent> eventPool) {
		this.inputProducer = inputProducer;
		this.eventPool = eventPool;
	}
	
	
	public void invoke(int button, int action) {
		//logger.d(TAG, String.format("Button %s went down", TokelonTypeAInputs.inputToString(buttonCode)));

		/*
		try(IObjectLease<ScreenButtonInputEvent> lease = eventPool.getObject()) {
			inputProducer.postScreenButtonInput(lease.getProperty().set(button, action));
		} catch (Exception e) { }
		*/
		
		ScreenButtonInputEvent event = eventPool.getObject();
		try {
			inputProducer.postScreenButtonInput(event.set(button, action));
		}
		finally {
			eventPool.returnObject(event);
		}
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
