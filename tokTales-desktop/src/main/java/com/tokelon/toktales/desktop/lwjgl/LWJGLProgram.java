package com.tokelon.toktales.desktop.lwjgl;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;

public class LWJGLProgram {
	
	
	/*
	//private final ExecutorService executor;
	
	public LWJGLProgram() {
		//main = mainThread;
		//executor = new ThreadPoolExecutor(1, 1, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>()
		//		, new MainThreadFactory(), new ThreadPoolExecutor.DiscardPolicy()); 
		//executor = Executors.newSingleThreadExecutor(new MainThreadFactory());
	}
	*/
	
	
	public void setup() throws LWJGLException {
		GLFWErrorCallback errorCallback;
		GLFW.glfwSetErrorCallback(errorCallback = GLFWErrorCallback.createPrint(System.err));

		if (GLFW.glfwInit() != GLFW.GLFW_TRUE) {
			throw new LWJGLException("Unable to initialize GLFW");
		}
	}
	
	
	public void tearDown() {
		// Terminate GLFW and free the error callback
		GLFW.glfwTerminate();
		GLFW.glfwSetErrorCallback(null);//.free();
	}

	
	
}
