package com.tokelon.toktales.desktop.lwjgl.input;

import org.lwjgl.glfw.GLFWCursorEnterCallback;

import com.tokelon.toktales.desktop.input.dispatch.IDesktopInputProducer;
import com.tokelon.toktales.desktop.input.events.ICursorEnterInputEvent;
import com.tokelon.toktales.desktop.input.events.ICursorEnterInputEvent.CursorEnterInputEvent;

/**
 * Note: Keep a reference to instances of this class.
 */
public class GLFWCursorEnterInputProducer extends GLFWCursorEnterCallback {

	
	private IDesktopInputProducer inputProducer;
	
	public GLFWCursorEnterInputProducer(IDesktopInputProducer inputProducer) {
		this.inputProducer = inputProducer;
	}
	

	@Override
	public void invoke(long window, boolean entered) {
		//logger.d(TAG, String.format("Cursor: %s", entered ? "entered" : "left"));
		
		inputProducer.postCursorEnterInput(getCursorEnterInputEvent(window, entered));
	}

	
	protected ICursorEnterInputEvent getCursorEnterInputEvent(long window, boolean entered) {
		CursorEnterInputEvent cursorEnterInputEvent = new ICursorEnterInputEvent.CursorEnterInputEvent();
		return cursorEnterInputEvent.set(window, entered);
	}
	
}
