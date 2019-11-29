package com.tokelon.toktales.desktop.lwjgl;

import org.lwjgl.PointerBuffer;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.system.MemoryUtil;

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
	
	
	public static class LWJGLWindowBuilder implements IWindowBuilder {
		private int width = 0;
		private int height = 0;
		private String title = "";
		private long monitor = 0L;
		private long share = 0L;

		@Override
		public IWindow build() {
			long id = GLFW.glfwCreateWindow(width, height, title, monitor, share);
			
			if(id == MemoryUtil.NULL) {
				throw new RuntimeException("Failed to create window");
			}
			
			IWindow window = new LWJGLWindow(id, title);
			return window;
		}

		
		@Override
		public IWindowBuilder withSize(int width, int height) {
			this.width = width;
			this.height = height;
			return this;
		}

		@Override
		public IWindowBuilder withTitle(String title) {
			this.title = title;
			return this;
		}

		@Override
		public IWindowBuilder withMonitor(long monitor) {
			this.monitor = monitor;
			return this;
		}
		
		@Override
		public IWindowBuilder withPrimaryMonitor() {
			this.monitor = GLFW.glfwGetPrimaryMonitor();
			return this;
		}

		@Override
		public IWindowBuilder withShare(long share) {
			this.share = share;
			return this;
		}

		@Override
		public IWindowBuilder withDefaultHints() {
			GLFW.glfwDefaultWindowHints();
			return this;
		}

		@Override
		public IWindowBuilder withWindowHint(int hint, int value) {
			GLFW.glfwWindowHint(hint, value);
			return this;
		}
	}
	
}
