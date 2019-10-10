package com.tokelon.toktales.android.input.events;

import com.tokelon.toktales.android.input.dispatch.IAndroidInputProducer;
import com.tokelon.toktales.android.input.events.IScreenPressInputEvent.ScreenPressInputEvent;
import com.tokelon.toktales.tools.core.objects.pools.IObjectPool;

public class AndroidScreenPressInputProducer {

	
	private final IAndroidInputProducer inputProducer;
	private final IObjectPool<ScreenPressInputEvent> eventPool;

	public AndroidScreenPressInputProducer(IAndroidInputProducer inputProducer, IObjectPool<ScreenPressInputEvent> eventPool) {
		this.inputProducer = inputProducer;
		this.eventPool = eventPool;
	}
	
	
	public void invoke(double xpos, double ypos) {
		ScreenPressInputEvent event = eventPool.getObject();
		try {
			inputProducer.postScreenPressInput(event.set(xpos, ypos));	
		}
		finally {
			eventPool.returnObject(event);
		}
	}
	
}
