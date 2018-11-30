package com.tokelon.toktales.desktop.lwjgl.input;

import org.lwjgl.glfw.GLFWKeyCallback;

import com.tokelon.toktales.desktop.input.dispatch.IDesktopInputProducer;
import com.tokelon.toktales.desktop.input.events.IKeyInputEvent;
import com.tokelon.toktales.desktop.input.events.IKeyInputEvent.KeyInputEvent;

/**
 * Note: Keep a reference to instances of this class.
 */
public class GLFWKeyInputProducer extends GLFWKeyCallback {

	
	private IDesktopInputProducer inputProducer;
	
	public GLFWKeyInputProducer(IDesktopInputProducer inputProducer) {
		this.inputProducer = inputProducer;
	}

	
	@Override
	public void invoke(long window, int key, int scancode, int action, int mods) {
		//TokTales.getLog().d(TAG, String.format("Key action=%d for key=%d with scancode=%d, mods=%d", action, key, scancode, mods));

		
		int vk = GLFWInput.keyGlfwToVk(key);
		int vkAction = GLFWInput.stateGlfwToInput(action);
		int vbMods = GLFWInput.modGlfwToInput(mods);
		int vbScanCode = scancode; // scancodeGlfwToInput not needed because there is no known scancodes
		
		inputProducer.postKeyInput(getKeyInputEvent(window, vk, vbScanCode, vkAction, vbMods));
	}
	
	
	protected IKeyInputEvent getKeyInputEvent(long window, int key, int scancode, int action, int mods) {
		KeyInputEvent keyInputEvent = new IKeyInputEvent.KeyInputEvent();
		return keyInputEvent.set(window, key, scancode, action, mods);
	}
	
}
