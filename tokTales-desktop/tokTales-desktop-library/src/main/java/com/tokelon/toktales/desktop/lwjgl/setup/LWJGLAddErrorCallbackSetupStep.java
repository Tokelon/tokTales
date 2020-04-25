package com.tokelon.toktales.desktop.lwjgl.setup;

import java.io.PrintStream;

import org.lwjgl.glfw.GLFWErrorCallback;

import com.tokelon.toktales.core.engine.EngineException;
import com.tokelon.toktales.core.engine.IEngineContext;
import com.tokelon.toktales.core.engine.log.ILogger;
import com.tokelon.toktales.core.engine.log.LoggingOutputStream;
import com.tokelon.toktales.core.engine.setup.ISetupStep;
import com.tokelon.toktales.desktop.lwjgl.ILWJGLInputService;
import com.tokelon.toktales.desktop.lwjgl.input.IGLFWInputConsumer;

public class LWJGLAddErrorCallbackSetupStep implements ISetupStep {


	private GLFWErrorCallback errorCallback;


	@Override
	public void onBuildUp(IEngineContext engineContext) throws EngineException {
		this.errorCallback = createLoggingErrorCallback(engineContext);
		getMainGLFWInputConsumer(engineContext).registerErrorCallback(errorCallback);
	}

	@Override
	public void onTearDown(IEngineContext engineContext) throws EngineException {
		getMainGLFWInputConsumer(engineContext).unregisterErrorCallback(errorCallback);
		errorCallback.free();
		this.errorCallback = null;
	}


	protected IGLFWInputConsumer getMainGLFWInputConsumer(IEngineContext engineContext) {
		// We assume the instance will be resolved, otherwise we cannot use this setup step
		ILWJGLInputService inputService = engineContext.getInjector().getInstance(ILWJGLInputService.class);
		return inputService.getMainInputDispatch().getGLFWInputConsumer();
	}

	protected GLFWErrorCallback createLoggingErrorCallback(IEngineContext engineContext) {
		ILogger errorCallbackLogger = engineContext.getLogging().getLogger(GLFWErrorCallback.class);
		LoggingOutputStream redirectOutputStream = new LoggingOutputStream((String msg) -> errorCallbackLogger.error(msg));

		return GLFWErrorCallback.createPrint(new PrintStream(redirectOutputStream, true)); // TODO: Select encoding?
	}

}
