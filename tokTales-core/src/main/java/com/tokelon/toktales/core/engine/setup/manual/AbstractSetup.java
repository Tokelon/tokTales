package com.tokelon.toktales.core.engine.setup.manual;

import com.google.inject.Injector;
import com.tokelon.toktales.core.engine.Engine.EngineFactory;
import com.tokelon.toktales.core.engine.EngineContext;
import com.tokelon.toktales.core.engine.EngineException;
import com.tokelon.toktales.core.engine.IEngine;
import com.tokelon.toktales.core.engine.IEngineContext;
import com.tokelon.toktales.core.engine.IEngineSetup;
import com.tokelon.toktales.core.engine.IHierarchicalInjectConfig;
import com.tokelon.toktales.core.engine.IInjectConfig;
import com.tokelon.toktales.core.engine.log.ILogService;
import com.tokelon.toktales.core.engine.log.ILogger;
import com.tokelon.toktales.core.engine.log.MainLogger;
import com.tokelon.toktales.core.game.Game.GameFactory;
import com.tokelon.toktales.core.game.IGame;
import com.tokelon.toktales.core.game.IGameAdapter;

public abstract class AbstractSetup implements IEngineSetup {

	private Injector injector;
	private IGame resultGame;
	private IEngine resultEngine;
	private ILogger resultLogger;

	private final IGameAdapter gameAdapter;
	
	protected AbstractSetup(IGameAdapter gameAdapter) {
		this.gameAdapter = gameAdapter;
	}
	
	
	@Override
	public IEngineContext create(IHierarchicalInjectConfig injectConfig) throws EngineException {
	    /* Setup injector */
        injector = createInjector(injectConfig);

        
		/* Setup engine */
		EngineFactory defaultEngineFactory = new EngineFactory();
		
		resultEngine = createEngine(injector, defaultEngineFactory);
		afterCreateEngine(resultEngine);
		
		
		/* Setup main logger */
		ILogService logService = resultEngine.getLogService();
		MainLogger defaultLogger = new MainLogger(logService);
		
		resultLogger = createLogger(injector, resultEngine, defaultLogger);
		afterCreateLogger(resultLogger);
		
		
		/* Setup game */
		GameFactory defaultGameFactory = new GameFactory(resultEngine, gameAdapter);
		
		resultGame = createGame(injector, resultEngine, resultLogger, defaultGameFactory);
		afterCreateGame(resultGame);
		
		
		finishCreate();
		
		return new EngineContext(injector, resultEngine, resultLogger, resultGame);
	}
	
	
	@Override
	public void run(IEngineContext context) throws EngineException {
		doRun(context);
	}
	
	

	protected Injector createInjector(IInjectConfig injectConfig) throws EngineException {
	    long before = System.currentTimeMillis();
	    injector = injectConfig.createInjector();

	    //Object obj = injector.getInstance(Object.class);
	    System.out.println("Injector creation time (ms): " + (System.currentTimeMillis() - before));        

	    return injector;
	}

	protected void afterCreateInjector(Injector injector) throws EngineException {
	    // Nothing
	}
	
	
	protected abstract IEngine createEngine(Injector injector, EngineFactory defaultEngineFactory) throws EngineException;
	
	protected void afterCreateEngine(IEngine engine) throws EngineException {
		// Nothing
	}
	
	
	protected abstract ILogger createLogger(Injector injector, IEngine engine, MainLogger defaultLogger) throws EngineException;
	
	protected void afterCreateLogger(ILogger logger) throws EngineException {
		// Nothing
	}
	
	
	protected abstract IGame createGame(Injector injector, IEngine engine, ILogger logger, GameFactory defaultGameFactory) throws EngineException;
	
	protected void afterCreateGame(IGame game) throws EngineException {
		// Nothing
	}
	
	
	protected void finishCreate() throws EngineException {
		// Nothing
	}
	
	
	protected abstract void doRun(IEngineContext context) throws EngineException;
	

}
