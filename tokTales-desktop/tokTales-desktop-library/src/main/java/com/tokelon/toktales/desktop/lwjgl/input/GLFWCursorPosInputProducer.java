package com.tokelon.toktales.desktop.lwjgl.input;

import org.lwjgl.glfw.GLFWCursorPosCallback;

import com.tokelon.toktales.core.util.IObjectPool;
import com.tokelon.toktales.desktop.input.dispatch.IDesktopInputProducer;
import com.tokelon.toktales.desktop.input.events.ICursorPosInputEvent.CursorPosInputEvent;

/**
 * Note: Keep a reference to instances of this class.
 */
public class GLFWCursorPosInputProducer extends GLFWCursorPosCallback {

	// TODO: Implement support for this here?
	// cursorInWindow would have to be updated from a GLFWCursorEnterCallback
	private boolean ignoreCursorOutsideOfWindow = false;
	private boolean cursorInWindow = true;
	
	
	private final IDesktopInputProducer inputProducer;
	private final IObjectPool<CursorPosInputEvent> eventPool;
	
	public GLFWCursorPosInputProducer(IDesktopInputProducer inputProducer, IObjectPool<CursorPosInputEvent> eventPool) {
		this.inputProducer = inputProducer;
		this.eventPool = eventPool;
	}
	

	@Override
	public void invoke(long window, double xpos, double ypos) {
		if(ignoreCursorOutsideOfWindow && !cursorInWindow) {
			//logger.d(TAG, String.format("IGNORED: Cursor move to (x=%.2f, y=%.2f)", xpos, ypos));
			
			// Ignore actions where the cursor is outside the window
			return;
		}
		//logger.d(TAG, String.format("Cursor move to (x=%.2f, y=%.2f)", xpos, ypos));
		
		
		CursorPosInputEvent event = eventPool.getObject();
		try {
			inputProducer.postCursorPosInput(event.set(window, xpos, ypos));	
		}
		finally {
			eventPool.returnObject(event);
		}
	}
	
}
