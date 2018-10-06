package com.tokelon.toktales.core.engine;

import com.google.inject.Injector;
import com.tokelon.toktales.core.engine.log.ILogger;
import com.tokelon.toktales.core.game.IGame;

public final class TokTales {

    private static Injector injector;

    private static IEngineContext context;

	private static IEngine engine;
	private static IGame game;
	private static ILogger logger;


    public static Injector getInjector() {
        return injector;
    }
	
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
	    context = engineContext;
	    
	    injector = engineContext.getInjector();
	    engine = engineContext.getEngine();
		game = engineContext.getGame();
		logger = engineContext.getLog();
	}
	
}
