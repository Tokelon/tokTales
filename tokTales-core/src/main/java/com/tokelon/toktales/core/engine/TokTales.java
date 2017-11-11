package com.tokelon.toktales.core.engine;

import com.tokelon.toktales.core.engine.log.ILogger;
import com.tokelon.toktales.core.game.IGame;

public final class TokTales {

	
	
	private static IEngine engine;
	
	private static IGame game;
	
	private static ILogger logger;
	
	private static IEngineContext context;
	
	
	
	public static IEngine getEngine() {
		return engine;
	}
	
	public static IGame getGame() {
		return game;
	}
	
	public static ILogger getLog() {
		return logger;
	}
	
	public static IEngineContext getContext() {
		return context;
	}
	
	
	public static void load(IEngineContext engineContext) {
		engine = engineContext.getEngine();
		game = engineContext.getGame();
		logger = engineContext.getLog();
		
		context = engineContext;
	}
	
	
}
