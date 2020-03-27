package com.tokelon.toktales.desktop.lwjgl.ui;

import org.lwjgl.PointerBuffer;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.system.MemoryUtil;

import com.tokelon.toktales.desktop.ui.window.IWindow;
import com.tokelon.toktales.desktop.ui.window.WindowException;

public class LWJGLWindow implements IWindow {


	private boolean initialized = false;

	private long windowId = -1;


	// Add protected getters for these?
	private int lastWindowWidth;
	private int lastWindowHeight;
	private int lastWindowPosX;
	private int lastWindowPosY;

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
		this(width, height, title, MemoryUtil.NULL, MemoryUtil.NULL);
	}

	public LWJGLWindow(int width, int height, String title, long monitor) {
		this(width, height, title, monitor, MemoryUtil.NULL);
	}

	public LWJGLWindow(int width, int height, String title, long monitor, long share) {
		this.initialWidth = width;
		this.initialHeight = height;
		this.initialTitle = title;
		this.initialMonitor = monitor;
		this.initialShare = share;

		this.windowTitle = title;

		this.lastWindowWidth = width;
		this.lastWindowHeight = height;
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
	public void detachContext() {
		GLFW.glfwMakeContextCurrent(MemoryUtil.NULL);
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
	public int getPositionX() {
		int[] posx = new int[1];
		GLFW.glfwGetWindowPos(windowId, posx, new int[1]);

		return posx[0];
	}

	@Override
	public int getPositionY() {
		int[] posy = new int[1];
		GLFW.glfwGetWindowPos(windowId, new int[1], posy);

		return posy[0];
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
	public int getAttribute(int attribute) {
		return GLFW.glfwGetWindowAttrib(windowId, attribute);
	}

	@Override
	public int getFrameBufferWidth() {
		int[] width = new int[1];
		GLFW.glfwGetFramebufferSize(windowId, width, new int[1]);

		return width[0];
	}

	@Override
	public int getFrameBufferHeight() {
		int[] height = new int[1];
		GLFW.glfwGetFramebufferSize(windowId, new int[1], height);

		return height[0];
	}

	@Override
	public boolean isVisible() {
		return GLFW.glfwGetWindowAttrib(windowId, GLFW.GLFW_VISIBLE) == 1;
	}

	@Override
	public boolean isFullscreen() {
		return getMonitor() != MemoryUtil.NULL;
	}

	@Override
	public boolean isBorderless() {
		if(isFullscreen()) {
			return false;
		}

		long currentMonitor = getCurrentMonitor();

		int[] xpos = new int[1];
		int[] ypos = new int[1];
		GLFW.glfwGetMonitorPos(currentMonitor, xpos, ypos);
		GLFWVidMode videoMode = GLFW.glfwGetVideoMode(currentMonitor);

		return getPositionX() == xpos[0] && getPositionY() == ypos[0]
				&& getWidth() == videoMode.width() && getHeight() == videoMode.height();
	}

	@Override
	public long getCurrentMonitor() {
		int winWidth = getWidth();
		int winHeight = getHeight();
		int winPosX = getPositionX();
		int winPoxY = getPositionY();

		int bestMatch = 0;
		long bestMonitor = 0;

		int[] posX = new int[1];
		int[] posY = new int[1];
		PointerBuffer monitorsBuffer = GLFW.glfwGetMonitors();
		while(monitorsBuffer.hasRemaining()) {
			long monitor = monitorsBuffer.get();

			GLFWVidMode videoMode = GLFW.glfwGetVideoMode(monitor);
			int monWidth = videoMode.width();
			int monHeight = videoMode.height();

			GLFW.glfwGetMonitorPos(monitor, posX, posY);
			int monPosX = posX[0];
			int monPosY = posY[0];

			int area = Math.max(0, Math.min(winPosX + winWidth, monPosX + monWidth) - Math.max(winPosX, monPosX))
					* Math.max(0, Math.min(winPoxY + winHeight, monPosY + monHeight) - Math.max(winPoxY, monPosY));

			if(area > bestMatch) {
				bestMatch = area;
				bestMonitor = monitor;
			}
		}

		return bestMonitor > 0 ? bestMonitor : GLFW.glfwGetPrimaryMonitor();
	}


	@Override
	public void setSize(int width, int height) {
		GLFW.glfwSetWindowSize(windowId, width, height);
	}

	@Override
	public void setPosition(int x, int y) {
		GLFW.glfwSetWindowPos(windowId, x, y);
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

	@Override
	public void setAttribute(int attribute, int value) {
		GLFW.glfwSetWindowAttrib(windowId, attribute, value);
	}


	@Override
	public void setWindowed() {
		setWindowed(lastWindowPosX, lastWindowPosY, lastWindowWidth, lastWindowHeight);
	}

	@Override
	public void setWindowed(long monitor, int width, int height) {
		int[] monPosX = new int[1];
		int[] monPosY = new int[1];
		GLFW.glfwGetMonitorPos(monitor, monPosX, monPosY);
		int xpos = monPosX[0];
		int ypos = monPosY[0];

		setWindowed(xpos, ypos, width, height);
	}

	@Override
	public void setWindowed(int x, int y, int width, int height) {
		setMonitor(0, x, y, width, height, 0); // refreshRate is ignored
		setAttribute(GLFW.GLFW_DECORATED, GLFW.GLFW_TRUE);
	}


	@Override
	public void setFullscreen() {
		setFullscreen(getCurrentMonitor());
	}

	@Override
	public void setFullscreen(long monitor) {
		saveWindowDimensionsIfWindowed();

		// Cannot change color modes after creation
		GLFWVidMode videoMode = GLFW.glfwGetVideoMode(monitor);
		setMonitor(monitor, 0, 0, videoMode.width(), videoMode.height(), videoMode.refreshRate());
	}


	@Override
	public void setBorderless() {
		setBorderless(getCurrentMonitor());
	}

	@Override
	public void setBorderless(long monitor) {
		saveWindowDimensionsIfWindowed();

		GLFWVidMode videoMode = GLFW.glfwGetVideoMode(monitor);
		int[] monPosX = new int[1];
		int[] monPosY = new int[1];
		GLFW.glfwGetMonitorPos(monitor, monPosX, monPosY);
		int xpos = monPosX[0];
		int ypos = monPosY[0];

		// Cannot change color modes after creation
		setMonitor(0, xpos, ypos, videoMode.width(), videoMode.height(), videoMode.refreshRate());
		setAttribute(GLFW.GLFW_DECORATED, GLFW.GLFW_FALSE);
	}


	protected void saveWindowDimensionsIfWindowed() {
		if(isFullscreen() || isBorderless()) {
			return;
		}

		this.lastWindowWidth = getWidth();
		this.lastWindowHeight = getHeight();
		this.lastWindowPosX = getPositionX();
		this.lastWindowPosY = getPositionY();
	}

}
