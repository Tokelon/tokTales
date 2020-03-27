package com.tokelon.toktales.desktop.lwjgl.ui;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWVidMode;

import com.tokelon.toktales.desktop.ui.window.IWindow;
import com.tokelon.toktales.desktop.ui.window.IWindowBuilder;
import com.tokelon.toktales.desktop.ui.window.IWindowFactory;
import com.tokelon.toktales.desktop.ui.window.IWindowToolkit;

public class LWJGLWindowFactory implements IWindowFactory {


	public static final String DEFAULT_WINDOW_TITLE = "Application";
	public static final int DEFAULT_WINDOW_WIDTH = 1280;
	public static final int DEFAULT_WINDOW_HEIGHT = 720;


	private final IWindowToolkit windowToolkit;

	public LWJGLWindowFactory() {
		this(new LWJGLWindowToolkit());
	}

	public LWJGLWindowFactory(IWindowToolkit windowToolkit) {
		this.windowToolkit = windowToolkit;
	}


	@Override
	public IWindowFactory withDefaultHints() {
		// Set color bits for monitor here?
		windowToolkit.setWindowHint(GLFW.GLFW_VISIBLE, GLFW.GLFW_FALSE);
		windowToolkit.setWindowHint(GLFW.GLFW_RESIZABLE, GLFW.GLFW_FALSE);
		windowToolkit.setWindowHint(GLFW.GLFW_DECORATED, GLFW.GLFW_TRUE);

		return this;
	}

	@Override
	public IWindow createDefault() {
		withDefaultHints();
		return new LWJGLWindow(DEFAULT_WINDOW_WIDTH, DEFAULT_WINDOW_HEIGHT, DEFAULT_WINDOW_TITLE);
	}

	@Override
	public IWindowBuilder createDefaultBuilder() {
		return (windowFactory, windowToolkit) -> {
			return createDefault();
		};
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

	@Override
	public IWindowBuilder createBuilder(int width, int height, String title) {
		return (windowFactory, windowToolkit) -> {
			return create(width, height, title);
		};
	}

	@Override
	public IWindowBuilder createBuilder(int width, int height, String title, long monitor) {
		return (windowFactory, windowToolkit) -> {
			return create(width, height, title, monitor);
		};
	}

	@Override
	public IWindowBuilder createBuilder(int width, int height, String title, long monitor, long share) {
		return (windowFactory, windowToolkit) -> {
			return create(width, height, title, monitor, share);
		};
	}


	@Override
	public IWindow createFullscreen(String title) {
		long monitor = windowToolkit.getPrimaryMonitor();
		GLFWVidMode videoMode = GLFW.glfwGetVideoMode(monitor); // TODO: Replace with windowToolkit call

		windowToolkit.setWindowHint(GLFW.GLFW_RED_BITS, videoMode.redBits());
		windowToolkit.setWindowHint(GLFW.GLFW_GREEN_BITS, videoMode.greenBits());
		windowToolkit.setWindowHint(GLFW.GLFW_BLUE_BITS, videoMode.blueBits());

		windowToolkit.setWindowHint(GLFW.GLFW_VISIBLE, GLFW.GLFW_FALSE);
		windowToolkit.setWindowHint(GLFW.GLFW_RESIZABLE, GLFW.GLFW_FALSE);

		return new LWJGLWindow(videoMode.width(), videoMode.height(), title, monitor);
	}

	@Override
	public IWindowBuilder createFullscreenBuilder(String title) {
		return (windowFactory, windowToolkit) -> {
			return createFullscreen(title);
		};
	}


	@Override
	public IWindow createBorderless(String title) {
		long monitor = windowToolkit.getPrimaryMonitor();
		GLFWVidMode videoMode = GLFW.glfwGetVideoMode(monitor); // TODO: Replace with windowToolkit call

		windowToolkit.setWindowHint(GLFW.GLFW_RED_BITS, videoMode.redBits());
		windowToolkit.setWindowHint(GLFW.GLFW_GREEN_BITS, videoMode.greenBits());
		windowToolkit.setWindowHint(GLFW.GLFW_BLUE_BITS, videoMode.blueBits());

		windowToolkit.setWindowHint(GLFW.GLFW_VISIBLE, GLFW.GLFW_FALSE);
		windowToolkit.setWindowHint(GLFW.GLFW_RESIZABLE, GLFW.GLFW_FALSE);
		windowToolkit.setWindowHint(GLFW.GLFW_DECORATED, GLFW.GLFW_FALSE);

		return new LWJGLWindow(videoMode.width(), videoMode.height(), title);
	}

	@Override
	public IWindowBuilder createBorderlessBuilder(String title) {
		return (windowFactory, windowToolkit) -> {
			return createBorderless(title);
		};
	}

}
