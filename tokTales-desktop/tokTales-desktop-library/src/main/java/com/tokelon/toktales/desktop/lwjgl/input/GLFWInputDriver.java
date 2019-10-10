package com.tokelon.toktales.desktop.lwjgl.input;

import org.lwjgl.glfw.GLFW;

import com.tokelon.toktales.desktop.input.IDesktopInputDriver;
import com.tokelon.toktales.desktop.input.TInput;
import com.tokelon.toktales.desktop.input.dispatch.IDesktopInputProducer;
import com.tokelon.toktales.desktop.input.events.ICharInputEvent.CharInputEvent;
import com.tokelon.toktales.desktop.input.events.ICursorEnterInputEvent.CursorEnterInputEvent;
import com.tokelon.toktales.desktop.input.events.ICursorPosInputEvent.CursorPosInputEvent;
import com.tokelon.toktales.desktop.input.events.IKeyInputEvent.KeyInputEvent;
import com.tokelon.toktales.desktop.input.events.IMouseButtonInputEvent.MouseButtonInputEvent;
import com.tokelon.toktales.desktop.lwjgl.LWJGLWindow;
import com.tokelon.toktales.tools.core.objects.pools.IObjectPool.IObjectPoolFactory;

public class GLFWInputDriver implements IDesktopInputDriver {
	
	private static final int EVENT_POOL_CAPACITY_MOUSE_BUTTON_INPUT = 20;
	private static final int EVENT_POOL_INITIAL_SIZE_MOUSE_BUTTON_INPUT = 5;
	private static final int EVENT_POOL_CAPACITY_CURSOR_ENTER_INPUT = 10;
	private static final int EVENT_POOL_INITIAL_SIZE_CURSOR_ENTER_INPUT = 2;
	private static final int EVENT_POOL_CAPACITY_CURSOR_POS_INPUT = 100;
	private static final int EVENT_POOL_INITIAL_SIZE_CURSOR_POS_INPUT = 20;
	private static final int EVENT_POOL_CAPACITY_KEY_INPUT = 50;
	private static final int EVENT_POOL_INITIAL_SIZE_KEY_INPUT = 20;
	private static final int EVENT_POOL_CAPACITY_CHAR_INPUT = 50;
	private static final int EVENT_POOL_INITIAL_SIZE_CHAR_INPUT = 20;
	
	
	private final GLFWMouseButtonInputProducer glfwButtonCallback;
	private final GLFWCursorEnterInputProducer glfwCursorEnterCallback;
	private final GLFWCursorPosInputProducer glfwCursorPosCallback;
	private final GLFWKeyInputProducer glfwKeyCallback;
	private final GLFWCharInputProducer glfwCharCallback;
	
	
	private final LWJGLWindow window;
	private final long windowHandle;

	public GLFWInputDriver(LWJGLWindow window, IDesktopInputProducer inputProducer, IObjectPoolFactory eventPoolFactory) {
		this.window = window;
		this.windowHandle = window.getID();
		
		
		GLFW.glfwSetMouseButtonCallback(windowHandle, glfwButtonCallback = new GLFWMouseButtonInputProducer(inputProducer, eventPoolFactory.create(() -> new MouseButtonInputEvent(), EVENT_POOL_CAPACITY_MOUSE_BUTTON_INPUT, EVENT_POOL_INITIAL_SIZE_MOUSE_BUTTON_INPUT)));
		GLFW.glfwSetCursorEnterCallback(windowHandle, glfwCursorEnterCallback = new GLFWCursorEnterInputProducer(inputProducer, eventPoolFactory.create(() -> new CursorEnterInputEvent(), EVENT_POOL_CAPACITY_CURSOR_ENTER_INPUT, EVENT_POOL_INITIAL_SIZE_CURSOR_ENTER_INPUT)));
		GLFW.glfwSetCursorPosCallback(windowHandle, glfwCursorPosCallback = new GLFWCursorPosInputProducer(inputProducer, eventPoolFactory.create(() -> new CursorPosInputEvent(), EVENT_POOL_CAPACITY_CURSOR_POS_INPUT, EVENT_POOL_INITIAL_SIZE_CURSOR_POS_INPUT)));
		
		GLFW.glfwSetKeyCallback(windowHandle, glfwKeyCallback = new GLFWKeyInputProducer(inputProducer, eventPoolFactory.create(() -> new KeyInputEvent(), EVENT_POOL_CAPACITY_KEY_INPUT, EVENT_POOL_INITIAL_SIZE_KEY_INPUT)));
		GLFW.glfwSetCharCallback(windowHandle, glfwCharCallback = new GLFWCharInputProducer(inputProducer, eventPoolFactory.create(() -> new CharInputEvent(), EVENT_POOL_CAPACITY_CHAR_INPUT, EVENT_POOL_INITIAL_SIZE_CHAR_INPUT)));
	}

	
	@Override
	public int getKeyState(int vk) {
		int glfwKey = GLFWInput.keyVkToGlfw(vk);
		int glfwState = GLFW.glfwGetKey(windowHandle, glfwKey);
		
		return GLFWInput.stateGlfwToInput(glfwState);
	}
	
	@Override
	public boolean isKeyPressed(int vk) {
		return getKeyState(vk) == TInput.KEY_PRESS;
	}
	
}
