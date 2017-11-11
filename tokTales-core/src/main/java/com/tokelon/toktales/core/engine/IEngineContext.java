package com.tokelon.toktales.core.engine;

import com.tokelon.toktales.core.engine.log.ILogger;
import com.tokelon.toktales.core.game.IGame;

public interface IEngineContext {	// Rename to IEngineContainer or even IGameContainer?
	
	public IEngine getEngine();
	
	public ILogger getLog();
	
	public IGame getGame();

}
