package com.tokelon.toktales.core.engine;

import com.tokelon.toktales.core.engine.log.ILogger;
import com.tokelon.toktales.core.game.IGame;

public class EngineContext implements IEngineContext {

	
	private final IEngine engine;
	private final ILogger log;
	private final IGame game;
	
	public EngineContext(IEngine engine, ILogger log, IGame game) {
		this.engine = engine;
		this.log = log;
		this.game = game;
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
