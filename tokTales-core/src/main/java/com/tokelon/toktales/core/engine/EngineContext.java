package com.tokelon.toktales.core.engine;

import com.google.inject.Injector;
import com.tokelon.toktales.core.engine.log.ILogger;
import com.tokelon.toktales.core.game.IGame;

public class EngineContext implements IEngineContext {

	private final Injector injector;
	private final IEngine engine;
	private final ILogger log;
	private final IGame game;
	
	public EngineContext(Injector injector, IEngine engine, ILogger log, IGame game) {
	    this.injector = injector;
		this.engine = engine;
		this.log = log;
		this.game = game;
	}
	
	
	@Override
	public Injector getInjector() {
	    return injector;
	}
	
	@Override
	public IEngine getEngine() {
		return engine;
	}

	@Override
	public ILogger getLog() {
		return log;
	}

	@Override
	public IGame getGame() {
		return game;
	}

}
