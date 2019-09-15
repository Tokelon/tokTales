package com.tokelon.toktales.core.engine.setup;

import com.google.inject.Injector;
import com.google.inject.Stage;
import com.tokelon.toktales.core.engine.EngineException;
import com.tokelon.toktales.core.engine.IEngineContext;
import com.tokelon.toktales.core.engine.inject.IHierarchicalInjectConfig;
import com.tokelon.toktales.core.engine.inject.IInjectConfig;
import com.tokelon.toktales.core.engine.log.ILogger;
import com.tokelon.toktales.core.engine.log.ILoggerFactory;
import com.tokelon.toktales.core.engine.log.LoggingManager;
import com.tokelon.toktales.core.game.IGameAdapter;

public abstract class AbstractInjectSetup implements IEngineSetup {


	// Default setup mode is production
	private SetupMode setupMode = SetupMode.PRODUCTION;
	
	private final ILogger logger;
	
	/** Default constructor.
	 */
	protected AbstractInjectSetup() {
		this(LoggingManager.getLoggerFactory());
	}

	/** Constructor with a logger factory.
	 * 
	 * @param loggerFactory
	 */
	protected AbstractInjectSetup(ILoggerFactory loggerFactory) {
		this.logger = loggerFactory.getLogger(getClass());
	}
	
	
	/**
	 * @return A logger for this setup.
	 */
	protected ILogger getLogger() {
		return logger;
	}

	/** Implement your custom setup logic here.
	 * <p>
	 * Called in {@link #run(IEngineContext)}.
	 * 
	 * @param context The engine context created.
	 * @throws EngineException If there was an error while running the setup.
	 */
	protected abstract void doRun(IEngineContext context) throws EngineException;

	
	@Override
	public IEngineContext create(IHierarchicalInjectConfig injectConfig) throws EngineException {
		logger.info("Engine creation started");
		logger.info("Engine setup is running in mode {}", setupMode);
		
		Injector injector = createInjector(injectConfig, setupMode);
		IEngineContext engineContext = createEngineContext(injector);
		
		return engineContext;
	}

	
	@Override
	public void run(IEngineContext context) throws EngineException {
		doRun(context);
		
		createGameAdapter(context.getInjector());
	}

	
	@Override
	public void setSetupMode(SetupMode mode) {
		this.setupMode = mode;
	}
	
	@Override
	public SetupMode getSetupMode() {
		return setupMode;
	}


	protected Injector createInjector(IInjectConfig injectConfig, SetupMode mode) throws EngineException {
		Stage stage = mode == SetupMode.DEVELOPMENT ? Stage.DEVELOPMENT : Stage.PRODUCTION;
		logger.info("Determined injector stage as {}", stage);
		
		logger.info("Creating engine injector...");
		long before = System.currentTimeMillis();

		Injector injector = injectConfig.createInjector(stage);
		
		logger.info("...injector creation took {} ms", (System.currentTimeMillis() - before));
		return injector;
	}

	protected IEngineContext createEngineContext(Injector injector) {
		logger.info("Creating engine context...");
		long before = System.currentTimeMillis();
		
		IEngineContext engineContext = injector.getInstance(IEngineContext.class);
		
		logger.info("...engine context creation took {} ms", (System.currentTimeMillis() - before));
		return engineContext;
	}
	
	protected IGameAdapter createGameAdapter(Injector injector) {
		logger.info("Creating game adapter...");
		long before = System.currentTimeMillis();

		IGameAdapter gameAdapter = injector.getInstance(IGameAdapter.class);
		
		logger.info("...game adapter creation took {} ms", (System.currentTimeMillis() - before));
		return gameAdapter;
	}
	
}
