package com.tokelon.toktales.desktop.lwjgl.ui;

import org.lwjgl.PointerBuffer;
import org.lwjgl.glfw.GLFW;

import com.tokelon.toktales.desktop.ui.window.IWindow;
import com.tokelon.toktales.desktop.ui.window.IWindowFactory;

public class LWJGLWindowFactory implements IWindowFactory {


	@Override
	public long getPrimaryMonitor() {
		return GLFW.glfwGetPrimaryMonitor();
	}
	
	@Override
	public long[] getMonitors() {
		PointerBuffer monitorsBuffer = GLFW.glfwGetMonitors();
		
		long[] monitorsArray = new long[monitorsBuffer.capacity()];
		monitorsBuffer.get(monitorsArray);
		
		return monitorsArray;
	}

	@Override
	public void setWindowHint(int hint, int value) {
		GLFW.glfwWindowHint(hint, value);
	}
	
	@Override
	public void setWindowHintString(int hint, String value) {
		GLFW.glfwWindowHintString(hint, value);
	}
	
	@Override
	public void setDefaultWindowHints() {
		GLFW.glfwDefaultWindowHints();
	}
	

	@Override
	public IWindow create(int width, int height, String title) {
		return new LWJGLWindow(width, height, title);
	}
	
	@Override
	public IWindow create(int width, int height, String title, long monitor) {
		return new LWJGLWindow(width, height, title, monitor);
	}
	
	@Override
	public IWindow create(int width, int height, String title, long monitor, long share) {
		return new LWJGLWindow(width, height, title, monitor, share);
	}
	
}
