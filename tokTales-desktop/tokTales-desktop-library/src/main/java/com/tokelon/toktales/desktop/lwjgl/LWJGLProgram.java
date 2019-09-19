package com.tokelon.toktales.desktop.lwjgl;

import java.io.PrintStream;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;

import com.tokelon.toktales.core.engine.log.ILogger;
import com.tokelon.toktales.core.engine.log.ILoggerFactory;
import com.tokelon.toktales.core.engine.log.LoggingManager;
import com.tokelon.toktales.core.engine.log.LoggingOutputStream;

public class LWJGLProgram {


	private GLFWErrorCallback errorCallback;
	
	private final ILogger logger;
	private final ILoggerFactory loggerFactory;
	
	public LWJGLProgram() {
		this(LoggingManager.getLoggerFactory());
	}
	
	public LWJGLProgram(ILoggerFactory loggerFactory) {
		this.loggerFactory = loggerFactory;
		this.logger = loggerFactory.getLogger(getClass());
	}
	
	
	public void setup() throws LWJGLException {
		errorCallback = createErrorCallback();
		GLFW.glfwSetErrorCallback(errorCallback);
		

		if (!GLFW.glfwInit()) {
			throw new LWJGLException("Failed to initialize GLFW");
		}
	}
	
	
	protected GLFWErrorCallback createErrorCallback() {
		ILogger errorCallbackLogger = loggerFactory.getLogger(GLFWErrorCallback.class);
		LoggingOutputStream redirectOutputStream = new LoggingOutputStream((String msg) -> errorCallbackLogger.error(msg));
		
		return GLFWErrorCallback.createPrint(new PrintStream(redirectOutputStream, true)); // TODO: Select encoding?
	}
	
	public void tearDown() {
		// Terminate GLFW and free the error callback
		GLFW.glfwTerminate();
		GLFW.glfwSetErrorCallback(null);//.free();
	}
	

	/*
	//private final ExecutorService executor;
	
	public LWJGLProgram() {
		//main = mainThread;
		//executor = new ThreadPoolExecutor(1, 1, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>()
		//		, new MainThreadFactory(), new ThreadPoolExecutor.DiscardPolicy()); 
		//executor = Executors.newSingleThreadExecutor(new MainThreadFactory());
	}
	*/
	
}
