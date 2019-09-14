package com.tokelon.toktales.core.engine;

import com.google.inject.Injector;
import com.tokelon.toktales.core.engine.log.ILogger;
import com.tokelon.toktales.core.engine.log.ILogging;
import com.tokelon.toktales.core.game.IGame;

public interface IEngineContext {


	public Injector getInjector();
    
	public IEngine getEngine();
	
	public ILogging getLogging();
	
	public ILogger getLogger();
	
	public IGame getGame();

}
