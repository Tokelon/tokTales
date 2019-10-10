package com.tokelon.toktales.desktop.lwjgl.input;

import org.lwjgl.glfw.GLFWKeyCallback;

import com.tokelon.toktales.desktop.input.dispatch.IDesktopInputProducer;
import com.tokelon.toktales.desktop.input.events.IKeyInputEvent.KeyInputEvent;
import com.tokelon.toktales.tools.core.objects.pools.IObjectPool;

/**
 * Note: Keep a reference to instances of this class.
 */
public class GLFWKeyInputProducer extends GLFWKeyCallback {

	
	private final IDesktopInputProducer inputProducer;
	private final IObjectPool<KeyInputEvent> eventPool;
	
	public GLFWKeyInputProducer(IDesktopInputProducer inputProducer, IObjectPool<KeyInputEvent> eventPool) {
		this.inputProducer = inputProducer;
		this.eventPool = eventPool;
	}

	
	@Override
	public void invoke(long window, int key, int scancode, int action, int mods) {
		//TokTales.getLog().d(TAG, String.format("Key action=%d for key=%d with scancode=%d, mods=%d", action, key, scancode, mods));

		
		int vk = GLFWInput.keyGlfwToVk(key);
		int vkAction = GLFWInput.stateGlfwToInput(action);
		int vbMods = GLFWInput.modGlfwToInput(mods);
		int vbScanCode = scancode; // scancodeGlfwToInput not needed because there is no known scancodes
		
		KeyInputEvent event = eventPool.getObject();
		try {
			inputProducer.postKeyInput(event.set(window, vk, vbScanCode, vkAction, vbMods));	
		}
		finally {
			eventPool.returnObject(event);
		}
	}
	
}
