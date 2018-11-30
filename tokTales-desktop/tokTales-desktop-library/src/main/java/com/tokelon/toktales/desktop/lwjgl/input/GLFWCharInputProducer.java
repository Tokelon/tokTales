package com.tokelon.toktales.desktop.lwjgl.input;

import org.lwjgl.glfw.GLFWCharCallback;

import com.tokelon.toktales.desktop.input.dispatch.IDesktopInputProducer;
import com.tokelon.toktales.desktop.input.events.ICharInputEvent;
import com.tokelon.toktales.desktop.input.events.ICharInputEvent.CharInputEvent;

/**
 * Note: Keep a reference to instances of this class.
 */
public class GLFWCharInputProducer extends GLFWCharCallback {


	private IDesktopInputProducer inputProducer;
	
	public GLFWCharInputProducer(IDesktopInputProducer inputProducer) {
		this.inputProducer = inputProducer;
	}
	
	
	@Override
	public void invoke(long window, int codepoint) {
		//logger.d(TAG, String.format("Char action for codepoint: %s", new String(Character.toChars(codepoint))));
		
		inputProducer.postCharInput(getCharInputEvent(window, codepoint));
	}
	

	protected ICharInputEvent getCharInputEvent(long window, int codepoint) {
		CharInputEvent charInputEvent = new ICharInputEvent.CharInputEvent();
		return charInputEvent.set(window, codepoint);
	}
	
}
