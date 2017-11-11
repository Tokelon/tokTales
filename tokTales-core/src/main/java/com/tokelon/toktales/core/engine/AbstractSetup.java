package com.tokelon.toktales.core.engine;

import com.tokelon.toktales.core.engine.Engine.EngineFactory;
import com.tokelon.toktales.core.engine.log.ILogService;
import com.tokelon.toktales.core.engine.log.ILogger;
import com.tokelon.toktales.core.engine.log.MainLogger;
import com.tokelon.toktales.core.game.IGame;
import com.tokelon.toktales.core.game.IGameAdapter;
import com.tokelon.toktales.core.game.Game.GameFactory;

public abstract class AbstractSetup implements IEngineSetup {

	
	private IGame resultGame;
	private IEngine resultEngine;
	private ILogger resultLogger;

	private final IGameAdapter gameAdapter;
	
	protected AbstractSetup(IGameAdapter gameAdapter) {
		this.gameAdapter = gameAdapter;
	}
	
	
	@Override
	public IEngineContext create() {

		/* Setup engine */
		EngineFactory defaultEngineFactory = new EngineFactory();
		
		resultEngine = createEngine(defaultEngineFactory);
		afterCreateEngine(resultEngine);
		
		
		/* Setup main logger */
		ILogService logService = resultEngine.getLogService();
		MainLogger defaultLogger = new MainLogger(logService);
		
		resultLogger = createLogger(resultEngine, defaultLogger);
		afterCreateLogger(resultLogger);
		
		
		/* Setup game */
		GameFactory defaultGameFactory = new GameFactory(resultEngine, gameAdapter);
		
		resultGame = createGame(resultEngine, resultLogger, defaultGameFactory);
		afterCreateGame(resultGame);
		
		
		finishCreate();
		
		return new EngineContext(resultEngine, resultLogger, resultGame);
	}
	
	
	@Override
	public void run(IEngineContext context) {
		doRun(context);
	}
	
	
	
	
	protected abstract IEngine createEngine(EngineFactory defaultEngineFactory);
	
	protected void afterCreateEngine(IEngine engine) {
		// Nothing
	}
	
	
	protected abstract ILogger createLogger(IEngine engine, MainLogger defaultLogger);
	
	protected void afterCreateLogger(ILogger logger) {
		// Nothing
	}
	
	
	protected abstract IGame createGame(IEngine engine, ILogger logger, GameFactory defaultGameFactory);
	
	protected void afterCreateGame(IGame game) {
		// Nothing
	}
	
	
	protected void finishCreate() {
		// Nothing
	}
	
	
	protected abstract void doRun(IEngineContext context);
	

}
