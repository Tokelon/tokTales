package com.tokelon.toktales.desktop.lwjgl.input;

import org.lwjgl.glfw.GLFWMouseButtonCallback;

import com.tokelon.toktales.desktop.input.dispatch.IDesktopInputProducer;
import com.tokelon.toktales.desktop.input.events.IMouseButtonInputEvent.MouseButtonInputEvent;
import com.tokelon.toktales.tools.core.objects.pools.IObjectPool;

/**
 * Note: Keep a reference to instances of this class.
 */
public class GLFWMouseButtonInputProducer extends GLFWMouseButtonCallback {

	// TODO: Implement support for this here?
	// cursorInWindow would have to be updated from a GLFWCursorEnterCallback
	private boolean ignoreCursorOutsideOfWindow = false;
	private boolean cursorInWindow = true;

	
	private final IDesktopInputProducer inputProducer;
	private final IObjectPool<MouseButtonInputEvent> eventPool;
	
	public GLFWMouseButtonInputProducer(IDesktopInputProducer inputProducer, IObjectPool<MouseButtonInputEvent> eventPool) {
		this.inputProducer = inputProducer;
		this.eventPool = eventPool;
	}
	
	
	@Override
	public void invoke(long window, int button, int action, int mods) {
		if(ignoreCursorOutsideOfWindow && !cursorInWindow) {
			//logger.d(TAG, String.format("IGNORED: Mouse action=%d for Button %d with mods=%d", action, button, mods));
			
			// Ignore actions where the cursor is outside the window
			return;
		}
		//logger.d(TAG, String.format("Mouse action=%d for Button %d with mods=%d", action, button, mods));
		
		
		int vb = GLFWInput.keyGlfwToVk(button);
		int vbAction = GLFWInput.stateGlfwToInput(action);
		int vbMods = GLFWInput.modGlfwToInput(mods);

		MouseButtonInputEvent event = eventPool.getObject();
		try {
			inputProducer.postMouseButtonInput(event.set(window, vb, vbAction, vbMods));	
		}
		finally {
			eventPool.returnObject(event);
		}
	}
	
}
