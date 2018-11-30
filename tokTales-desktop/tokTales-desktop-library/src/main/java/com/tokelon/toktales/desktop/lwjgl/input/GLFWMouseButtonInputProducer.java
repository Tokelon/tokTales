package com.tokelon.toktales.desktop.lwjgl.input;

import org.lwjgl.glfw.GLFWMouseButtonCallback;

import com.tokelon.toktales.desktop.input.dispatch.IDesktopInputProducer;
import com.tokelon.toktales.desktop.input.events.IMouseButtonInputEvent;
import com.tokelon.toktales.desktop.input.events.IMouseButtonInputEvent.MouseButtonInputEvent;

/**
 * Note: Keep a reference to instances of this class.
 */
public class GLFWMouseButtonInputProducer extends GLFWMouseButtonCallback {

	// TODO: Implement support for this here?
	// cursorInWindow would have to be updated from a GLFWCursorEnterCallback
	private boolean ignoreCursorOutsideOfWindow = false;
	private boolean cursorInWindow = true;

	
	private IDesktopInputProducer inputProducer;
	
	public GLFWMouseButtonInputProducer(IDesktopInputProducer inputProducer) {
		this.inputProducer = inputProducer;
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

		inputProducer.postMouseButtonInput(getMouseButtonInputEvent(window, vb, vbAction, vbMods));
	}
	
	
	protected IMouseButtonInputEvent getMouseButtonInputEvent(long window, int button, int action, int mods) {
		MouseButtonInputEvent mouseButtonInputEvent = new IMouseButtonInputEvent.MouseButtonInputEvent();
		return mouseButtonInputEvent.set(window, button, action, mods);
	}
	
}
