package com.tokelon.toktales.core.engine;

import javax.inject.Inject;

import com.google.inject.Injector;
import com.tokelon.toktales.core.engine.log.ILogger;
import com.tokelon.toktales.core.engine.log.ILogging;
import com.tokelon.toktales.core.game.IGame;

public class EngineContext implements IEngineContext {


	private final Injector injector;
	private final IEngine engine;
	private final ILogging logging;
	private final ILogger logger;
	private final IGame game;

	@Inject
	public EngineContext(Injector injector, IEngine engine, ILogging logging, IGame game) {
		this.injector = injector;
		this.engine = engine;
		this.logging = logging;
		this.logger = logging.getLogger(getClass());
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
	public ILogging getLogging() {
		return logging;
	}
	
	@Override
	public ILogger getLogger() {
		return logger;
	}

	@Override
	public IGame getGame() {
		return game;
	}

}
