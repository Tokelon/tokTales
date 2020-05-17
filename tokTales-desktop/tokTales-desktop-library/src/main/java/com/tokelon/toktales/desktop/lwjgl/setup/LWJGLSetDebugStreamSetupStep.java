package com.tokelon.toktales.desktop.lwjgl.setup;

import java.io.PrintStream;

import org.lwjgl.system.Configuration;

import com.tokelon.toktales.core.engine.EngineException;
import com.tokelon.toktales.core.engine.IEngineContext;
import com.tokelon.toktales.core.engine.log.ILogger;
import com.tokelon.toktales.core.engine.log.LoggingOutputStream;
import com.tokelon.toktales.core.engine.setup.ISetupStep;

public class LWJGLSetDebugStreamSetupStep implements ISetupStep {


	public static final String LWJGL_LOGGER_NAME = "LWJGL";


	@Override
	public void onBuildUp(IEngineContext engineContext) throws EngineException {
		ILogger lwjglLogger = engineContext.getLogging().getLogger(LWJGL_LOGGER_NAME);
		LoggingOutputStream redirectOutputStream = new LoggingOutputStream((String msg) -> lwjglLogger.debug(msg));

		Configuration.DEBUG_STREAM.set(new PrintStream(redirectOutputStream, true));
	}

	@Override
	public void onTearDown(IEngineContext engineContext) throws EngineException {
		// Nothing
	}

}
