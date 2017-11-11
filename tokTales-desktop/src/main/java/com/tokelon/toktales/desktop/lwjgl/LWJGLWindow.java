package com.tokelon.toktales.desktop.lwjgl;

import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.MemoryUtil;

public class LWJGLWindow {

	
	private final long id;
	
	private int width;
	private int height;

	public LWJGLWindow(long windowID) {
		this.id = windowID;
		
		
		IntBuffer wBuffer = BufferUtils.createIntBuffer(1);
		IntBuffer hBuffer = BufferUtils.createIntBuffer(1);
		
		GLFW.glfwGetWindowSize(id, wBuffer, hBuffer);
		
		width = wBuffer.get(0);
		height = hBuffer.get(0);
		
	}
	
	
	public long getID() {
		return id;
	}
	
	

	
	public void create() {
		
		makeContextCurrent();
		
		GL.createCapabilities();
		
		
		
		/* Where to put this ? */
		//glfwSetInputMode(id, GLFW_CURSOR, GLFW_CURSOR_DISABLED);

	}
	
	
	public void show() {
		GLFW.glfwShowWindow(id);		
	}
	

	public void destroy() {
        // Free the window callbacks and destroy the window
        //glfwFreeCallbacks(id);
		GLFW.glfwDestroyWindow(id);
	}
	
	
	public void makeContextCurrent() {
		GLFW.glfwMakeContextCurrent(id);
	}

	
	public void setSwapInterval(int interval) {
		GLFW.glfwSwapInterval(interval);
	}
	
	
	public boolean shouldClose() {
		return GLFW.glfwWindowShouldClose(id) == GLFW.GLFW_TRUE;
	}

	public void swapBuffers() {
		GLFW.glfwSwapBuffers(id);
	}
	
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	
	
	public static class WindowFactory {
		
		
		public WindowFactory() {
			this(true);
		}
		
		public WindowFactory(boolean defaultWindowHints) {
			if(defaultWindowHints) {
				resetWindowHints();
			}
		}
		
		
		public void resetWindowHints() {
			GLFW.glfwDefaultWindowHints();
		}
		
		public void setWindowHint(int type, int value) {
			GLFW.glfwWindowHint(type, value);
		}
	
		
		public LWJGLWindow create(int width, int height, CharSequence title, long monitor, long share) throws LWJGLException {
			long id = GLFW.glfwCreateWindow(width, height, title, monitor, share);
			
			if(id == MemoryUtil.NULL) {
				throw new LWJGLException("Failed to create window");
			}
			
			
			return new LWJGLWindow(id);
		}
		
	}
	
	
}
