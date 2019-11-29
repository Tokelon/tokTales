package com.tokelon.toktales.desktop.lwjgl;

import org.lwjgl.glfw.GLFW;

import com.tokelon.toktales.desktop.ui.window.IWindow;
import com.tokelon.toktales.desktop.ui.window.WindowException;

public class LWJGLWindow implements IWindow {


	private boolean initialized = false;
	
	private long windowId = -1;
	
	
	private int windowSwapInterval = 0;
	
	private String windowTitle;
	
	private final int initialWidth;
	private final int initialHeight;
	private final String initialTitle;
	private final long initialMonitor;
	private final long initialShare;
	
	public LWJGLWindow(long id, String title) {
		this(-1, -1, title, -1, -1);
		this.windowId = id;
		this.initialized = true;
	}

	public LWJGLWindow(int width, int height, String title) {
		this(width, height, title, 0L, 0L);
	}
	
	public LWJGLWindow(int width, int height, String title, long monitor) {
		this(width, height, title, monitor, 0L);
	}
	
	public LWJGLWindow(int width, int height, String title, long monitor, long share) {
		this.initialWidth = width;
		this.initialHeight = height;
		this.initialTitle = title;
		this.initialMonitor = monitor;
		this.initialShare = share;
		
		this.windowTitle = title;
	}
	
	
	@Override
	public void create() {
		if(!initialized) {
			windowId = GLFW.glfwCreateWindow(this.initialWidth, this.initialHeight, this.initialTitle, this.initialMonitor, this.initialShare);
			
			if(windowId <= 0) {
				throw new WindowException("Window creation failed");
			}
			
			initialized = true;
		}
	}
	
	@Override
	public void show() {
		GLFW.glfwShowWindow(windowId);
	}
	
	@Override
	public void hide() {
		GLFW.glfwHideWindow(windowId);
	}
	
	@Override
	public void destroy() {
		// Free the window callbacks and destroy the window
		//glfwFreeCallbacks(id);
		
		GLFW.glfwDestroyWindow(windowId);
	}


	@Override
	public boolean shouldClose() {
		return GLFW.glfwWindowShouldClose(windowId);
	}
	
	@Override
	public void makeContextCurrent() {
		GLFW.glfwMakeContextCurrent(windowId);
	}

	@Override
	public void swapBuffers() {
		GLFW.glfwSwapBuffers(windowId);
	}
	

	public void disableContextCreation() {
		GLFW.glfwWindowHint(GLFW.GLFW_CLIENT_API, GLFW.GLFW_NO_API);
	}
	
	

	@Override
	public long getId() {
		if(!initialized) {
			throw new WindowException("Window has not been initialized yet. Call create() first");
		}
		
		return windowId;
	}
	
	@Override
	public int getWidth() {
		int[] width = new int[1];
		GLFW.glfwGetWindowSize(windowId, width, new int[1]);
		
		return width[0];
	}
	
	@Override
	public int getHeight() {
		int[] height = new int[1];
		GLFW.glfwGetWindowSize(windowId, new int[1], height);
		
		return height[0];
	}
	
	@Override
	public String getTitle() {
		return windowTitle;
	}
	
	@Override
	public long getMonitor() {
		return GLFW.glfwGetWindowMonitor(windowId);
	}
	
	@Override
	public int getSwapInterval() {
		return windowSwapInterval;
	}
	
	@Override
	public int getInputMode(int mode) {
		return GLFW.glfwGetInputMode(windowId, mode);
	}
	
	
	@Override
	public void setSize(int width, int height) {
		GLFW.glfwSetWindowSize(windowId, width, height);
	}
	
	@Override
	public void setTitle(String title) {
		GLFW.glfwSetWindowTitle(windowId, title);
	}

	@Override
	public void setMonitor(long monitor) {
		GLFW.glfwSetWindowMonitor(windowId, monitor, 0, 0, getWidth(), getHeight(), GLFW.GLFW_DONT_CARE);
	}
	
	@Override
	public void setMonitor(long monitor, int xpos, int ypos, int width, int height, int refreshRate) {
		GLFW.glfwSetWindowMonitor(windowId, monitor, xpos, ypos, width, height, refreshRate);
	}
	
	@Override
	public void setSwapInterval(int interval) {
		this.windowSwapInterval = interval;
		
		GLFW.glfwSwapInterval(interval);
	}
	
	@Override
	public void setInputMode(int mode, int value) {
		GLFW.glfwSetInputMode(windowId, mode, value);
	}
	
}
