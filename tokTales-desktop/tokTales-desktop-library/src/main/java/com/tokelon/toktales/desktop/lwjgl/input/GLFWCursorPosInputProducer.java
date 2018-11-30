package com.tokelon.toktales.desktop.lwjgl.input;

import org.lwjgl.glfw.GLFWCursorPosCallback;

import com.tokelon.toktales.desktop.input.dispatch.IDesktopInputProducer;
import com.tokelon.toktales.desktop.input.events.ICursorPosInputEvent;
import com.tokelon.toktales.desktop.input.events.ICursorPosInputEvent.CursorPosInputEvent;

/**
 * Note: Keep a reference to instances of this class.
 */
public class GLFWCursorPosInputProducer extends GLFWCursorPosCallback {

	// TODO: Implement support for this here?
	// cursorInWindow would have to be updated from a GLFWCursorEnterCallback
	private boolean ignoreCursorOutsideOfWindow = false;
	private boolean cursorInWindow = true;
	
	
	private IDesktopInputProducer inputProducer;
	
	public GLFWCursorPosInputProducer(IDesktopInputProducer inputProducer) {
		this.inputProducer = inputProducer;
	}
	

	@Override
	public void invoke(long window, double xpos, double ypos) {
		if(ignoreCursorOutsideOfWindow && !cursorInWindow) {
			//logger.d(TAG, String.format("IGNORED: Cursor move to (x=%.2f, y=%.2f)", xpos, ypos));
			
			// Ignore actions where the cursor is outside the window
			return;
		}
		//logger.d(TAG, String.format("Cursor move to (x=%.2f, y=%.2f)", xpos, ypos));
		
		
		inputProducer.postCursorPosInput(geCursorPosInputEvent(window, xpos, ypos));
	}
	
	
	protected ICursorPosInputEvent geCursorPosInputEvent(long window, double xpos, double ypos) {
		CursorPosInputEvent cursorPosInputEvent = new ICursorPosInputEvent.CursorPosInputEvent();
		return cursorPosInputEvent.set(window, xpos, ypos);
	}
	
}
