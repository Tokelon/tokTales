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
import com.tokelon.toktales.desktop.ui.window.IWindow;
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


	private GLFWMouseButtonInputProducer glfwButtonCallback;
	private GLFWCursorEnterInputProducer glfwCursorEnterCallback;
	private GLFWCursorPosInputProducer glfwCursorPosCallback;
	private GLFWKeyInputProducer glfwKeyCallback;
	private GLFWCharInputProducer glfwCharCallback;


	private IWindow window;
	private long windowHandle;

	private final IDesktopInputProducer inputProducer;
	private final IObjectPoolFactory eventPoolFactory;
	private final IGLFWInputConsumer inputConsumer;

	public GLFWInputDriver(IDesktopInputProducer inputProducer, IGLFWInputConsumer inputConsumer, IObjectPoolFactory eventPoolFactory) {
		this.inputProducer = inputProducer;
		this.inputConsumer = inputConsumer;
		this.eventPoolFactory = eventPoolFactory;
	}


	@Override
	public void register(IWindow window) {
		if(this.window != null) {
			throw new IllegalStateException("Input driver has already been registered");
		}

		this.window = window;
		this.windowHandle = window.getId();


		inputConsumer.registerWindowCallbacks(windowHandle);

		inputConsumer.registerMouseButtonCallback(windowHandle, glfwButtonCallback = new GLFWMouseButtonInputProducer(inputProducer, eventPoolFactory.create(() -> new MouseButtonInputEvent(), EVENT_POOL_CAPACITY_MOUSE_BUTTON_INPUT, EVENT_POOL_INITIAL_SIZE_MOUSE_BUTTON_INPUT)));
		inputConsumer.registerCursorEnterCallback(windowHandle, glfwCursorEnterCallback = new GLFWCursorEnterInputProducer(inputProducer, eventPoolFactory.create(() -> new CursorEnterInputEvent(), EVENT_POOL_CAPACITY_CURSOR_ENTER_INPUT, EVENT_POOL_INITIAL_SIZE_CURSOR_ENTER_INPUT)));
		inputConsumer.registerCursorPosCallback(windowHandle, glfwCursorPosCallback = new GLFWCursorPosInputProducer(inputProducer, eventPoolFactory.create(() -> new CursorPosInputEvent(), EVENT_POOL_CAPACITY_CURSOR_POS_INPUT, EVENT_POOL_INITIAL_SIZE_CURSOR_POS_INPUT)));
		inputConsumer.registerKeyCallback(windowHandle, glfwKeyCallback = new GLFWKeyInputProducer(inputProducer, eventPoolFactory.create(() -> new KeyInputEvent(), EVENT_POOL_CAPACITY_KEY_INPUT, EVENT_POOL_INITIAL_SIZE_KEY_INPUT)));
		inputConsumer.registerCharCallback(windowHandle, glfwCharCallback = new GLFWCharInputProducer(inputProducer, eventPoolFactory.create(() -> new CharInputEvent(), EVENT_POOL_CAPACITY_CHAR_INPUT, EVENT_POOL_INITIAL_SIZE_CHAR_INPUT)));
	}

	@Override
	public void unregister() {
		if(this.window == null) {
			throw new IllegalStateException("Input driver has not been registered");
		}


		inputConsumer.unregisterWindowCallbacks(windowHandle);

		inputConsumer.unregisterMouseButtonCallback(windowHandle, glfwButtonCallback);
		inputConsumer.unregisterCursorEnterCallback(windowHandle, glfwCursorEnterCallback);
		inputConsumer.unregisterCursorPosCallback(windowHandle, glfwCursorPosCallback);
		inputConsumer.unregisterKeyCallback(windowHandle, glfwKeyCallback);
		inputConsumer.unregisterCharCallback(windowHandle, glfwCharCallback);

		glfwButtonCallback = null;
		glfwCursorEnterCallback = null;
		glfwCursorPosCallback = null;
		glfwKeyCallback = null;
		glfwCharCallback = null;

		window = null;
		windowHandle = 0L;
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
