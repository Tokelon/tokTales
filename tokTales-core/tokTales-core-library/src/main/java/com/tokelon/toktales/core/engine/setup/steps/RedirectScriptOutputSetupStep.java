package com.tokelon.toktales.core.engine.setup.steps;

import java.io.PrintStream;

import com.tokelon.toktales.core.engine.EngineException;
import com.tokelon.toktales.core.engine.IEngineContext;
import com.tokelon.toktales.core.engine.log.ILogger;
import com.tokelon.toktales.core.engine.log.LoggingOutputStream;
import com.tokelon.toktales.core.engine.setup.ISetupStep;
import com.tokelon.toktales.core.game.IGameScriptManager;

public class RedirectScriptOutputSetupStep implements ISetupStep {


	private PrintStream outStream;
	private PrintStream errStream;


	@Override
	public void onBuildUp(IEngineContext engineContext) throws EngineException {
		ILogger luajLogger = engineContext.getLogging().getLogger("Script");
		this.outStream = new PrintStream(new LoggingOutputStream((String msg) -> luajLogger.info(msg)), true);
		this.errStream = new PrintStream(new LoggingOutputStream((String msg) -> luajLogger.error(msg)), true);

		IGameScriptManager gameScriptManager = engineContext.getGame().getScriptManager();
		gameScriptManager.setOutputStreams(outStream, errStream);
	}


	@Override
	public void onTearDown(IEngineContext engineContext) throws EngineException {
		IGameScriptManager gameScriptManager = engineContext.getGame().getScriptManager();
		gameScriptManager.setOutputStreams(System.out, System.err);

		this.outStream.close();
		this.errStream.close();
	}

}
