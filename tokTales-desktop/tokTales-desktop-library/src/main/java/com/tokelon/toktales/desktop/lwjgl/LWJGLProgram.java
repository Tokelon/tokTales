package com.tokelon.toktales.desktop.lwjgl;

import java.io.PrintStream;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;

import com.tokelon.toktales.core.engine.log.ILogger;
import com.tokelon.toktales.core.engine.log.ILoggerFactory;
import com.tokelon.toktales.core.engine.log.LoggingManager;
import com.tokelon.toktales.core.engine.log.LoggingOutputStream;
import com.tokelon.toktales.desktop.lwjgl.input.IGLFWInputRegistration;

public class LWJGLProgram {


	private GLFWErrorCallback errorCallback;

	private final IGLFWInputRegistration inputRegistration;
	private final ILoggerFactory loggerFactory;

	public LWJGLProgram(IGLFWInputRegistration inputRegistration) {
		this(inputRegistration, LoggingManager.getLoggerFactory());
	}

	public LWJGLProgram(IGLFWInputRegistration inputRegistration, ILoggerFactory loggerFactory) {
		this.inputRegistration = inputRegistration;
		this.loggerFactory = loggerFactory;
	}


	public void setup() throws LWJGLException {
		this.errorCallback = createLoggingErrorCallback();
		inputRegistration.registerErrorCallback(errorCallback);


		if (!GLFW.glfwInit()) {
			throw new LWJGLException("Failed to initialize GLFW");
		}
	}


	protected GLFWErrorCallback createLoggingErrorCallback() {
		ILogger errorCallbackLogger = loggerFactory.getLogger(GLFWErrorCallback.class);
		LoggingOutputStream redirectOutputStream = new LoggingOutputStream((String msg) -> errorCallbackLogger.error(msg));

		return GLFWErrorCallback.createPrint(new PrintStream(redirectOutputStream, true)); // TODO: Select encoding?
	}

	public void tearDown() {
		GLFW.glfwTerminate();

		inputRegistration.unregisterErrorCallback(errorCallback);
		errorCallback.free();
		this.errorCallback = null;
	}

}
