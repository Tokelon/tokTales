package com.tokelon.toktales.android.input.events;

import com.tokelon.toktales.android.input.dispatch.IAndroidInputProducer;
import com.tokelon.toktales.android.input.events.IScreenPointerInputEvent.ScreenPointerInputEvent;
import com.tokelon.toktales.tools.core.objects.pools.IObjectPool;

public class AndroidScreenPointerInputProducer {

	
	private final IAndroidInputProducer inputProducer;
	private final IObjectPool<ScreenPointerInputEvent> eventPool;

	public AndroidScreenPointerInputProducer(IAndroidInputProducer inputProducer, IObjectPool<ScreenPointerInputEvent> eventPool) {
		this.inputProducer = inputProducer;
		this.eventPool = eventPool;
	}
	
	
	public void invoke(int pointerId, int action, double xpos, double ypos) {
		ScreenPointerInputEvent event = eventPool.getObject();
		try {
			inputProducer.postScreenPointerInput(event.set(pointerId, action, xpos, ypos));			
		}
		finally {
			eventPool.returnObject(event);
		}
	}
	
}
