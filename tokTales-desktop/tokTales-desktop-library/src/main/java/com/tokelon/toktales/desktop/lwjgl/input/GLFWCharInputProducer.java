package com.tokelon.toktales.desktop.lwjgl.input;

import org.lwjgl.glfw.GLFWCharCallback;

import com.tokelon.toktales.core.util.IObjectPool;
import com.tokelon.toktales.desktop.input.dispatch.IDesktopInputProducer;
import com.tokelon.toktales.desktop.input.events.ICharInputEvent.CharInputEvent;

/**
 * Note: Keep a reference to instances of this class.
 */
public class GLFWCharInputProducer extends GLFWCharCallback {


	private final IDesktopInputProducer inputProducer;
	private final IObjectPool<CharInputEvent> eventPool;
	
	public GLFWCharInputProducer(IDesktopInputProducer inputProducer, IObjectPool<CharInputEvent> eventPool) {
		this.inputProducer = inputProducer;
		this.eventPool = eventPool;
	}
	
	
	@Override
	public void invoke(long window, int codepoint) {
		//logger.d(TAG, String.format("Char action for codepoint: %s", new String(Character.toChars(codepoint))));
		
		CharInputEvent event = eventPool.getObject();
		try {
			inputProducer.postCharInput(event.set(window, codepoint));	
		}
		finally {
			eventPool.returnObject(event);
		}
	}
	
}
