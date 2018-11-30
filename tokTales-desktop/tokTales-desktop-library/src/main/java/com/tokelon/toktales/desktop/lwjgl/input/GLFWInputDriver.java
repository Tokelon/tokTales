package com.tokelon.toktales.desktop.lwjgl.input;

import org.lwjgl.glfw.GLFW;

import com.tokelon.toktales.desktop.input.IDesktopInputDriver;
import com.tokelon.toktales.desktop.input.TInput;
import com.tokelon.toktales.desktop.input.dispatch.IDesktopInputProducer;
import com.tokelon.toktales.desktop.lwjgl.LWJGLWindow;

public class GLFWInputDriver implements IDesktopInputDriver {
	
	
	private final GLFWMouseButtonInputProducer glfwButtonCallback;
	private final GLFWCursorEnterInputProducer glfwCursorEnterCallback;
	private final GLFWCursorPosInputProducer glfwCursorPosCallback;
	private final GLFWKeyInputProducer glfwKeyCallback;
	private final GLFWCharInputProducer glfwCharCallback;
	
	
	private final LWJGLWindow window;
	private final long windowHandle;

	public GLFWInputDriver(LWJGLWindow window, IDesktopInputProducer inputProducer) {
		this.window = window;
		this.windowHandle = window.getID();
		
		
		GLFW.glfwSetMouseButtonCallback(windowHandle, glfwButtonCallback = new GLFWMouseButtonInputProducer(inputProducer));
		GLFW.glfwSetCursorEnterCallback(windowHandle, glfwCursorEnterCallback = new GLFWCursorEnterInputProducer(inputProducer));
		GLFW.glfwSetCursorPosCallback(windowHandle, glfwCursorPosCallback = new GLFWCursorPosInputProducer(inputProducer));
		
		GLFW.glfwSetKeyCallback(windowHandle, glfwKeyCallback = new GLFWKeyInputProducer(inputProducer));
		GLFW.glfwSetCharCallback(windowHandle, glfwCharCallback = new GLFWCharInputProducer(inputProducer));
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
