package com.tokelon.toktales.desktop.lwjgl.input;

import org.lwjgl.glfw.GLFWCursorEnterCallback;

import com.tokelon.toktales.desktop.input.dispatch.IDesktopInputProducer;
import com.tokelon.toktales.desktop.input.events.ICursorEnterInputEvent.CursorEnterInputEvent;
import com.tokelon.toktales.tools.core.objects.pools.IObjectPool;

/**
 * Note: Keep a reference to instances of this class.
 */
public class GLFWCursorEnterInputProducer extends GLFWCursorEnterCallback {

	
	private final IDesktopInputProducer inputProducer;
	private final IObjectPool<CursorEnterInputEvent> eventPool;
	
	public GLFWCursorEnterInputProducer(IDesktopInputProducer inputProducer, IObjectPool<CursorEnterInputEvent> eventPool) {
		this.inputProducer = inputProducer;
		this.eventPool = eventPool;
	}
	

	@Override
	public void invoke(long window, boolean entered) {
		//logger.d(TAG, String.format("Cursor: %s", entered ? "entered" : "left"));
		
		CursorEnterInputEvent event = eventPool.getObject();
		try {
			inputProducer.postCursorEnterInput(event.set(window, entered));	
		}
		finally {
			eventPool.returnObject(event);
		}
	}
	
}
