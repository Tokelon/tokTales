package com.tokelon.toktales.core.engine;

import com.tokelon.toktales.core.engine.inject.BaseSetupInjectModule;
import com.tokelon.toktales.core.engine.log.ILogger;
import com.tokelon.toktales.core.engine.log.ILoggerFactory;
import com.tokelon.toktales.core.engine.log.LoggingManager;
import com.tokelon.toktales.core.engine.setup.BaseInjectSetup;
import com.tokelon.toktales.core.engine.setup.IEngineSetup;
import com.tokelon.toktales.core.game.IGameAdapter;
import com.tokelon.toktales.tools.core.sub.inject.config.IHierarchicalInjectConfig;

public class BaseEngineLauncher implements IEngineLauncher {


	private final IHierarchicalInjectConfig injectConfig;
	private final IEngineLooper defaultLooper;
	private final ILogger logger;

	/** Constructor with an inject config.
	 * <p>
	 * A no-op looper will be used.
	 * 
	 * @param injectConfig
	 */
	protected BaseEngineLauncher(IHierarchicalInjectConfig injectConfig) {
		this(injectConfig, new NoopEngineLooper(), LoggingManager.getLoggerFactory());
	}
	
	/** Constructor with an inject config and a logger factory.
	 * <p>
	 * A no-op looper will be used.
	 * 
	 * @param injectConfig
	 * @param loggerFactory
	 */
	protected BaseEngineLauncher(IHierarchicalInjectConfig injectConfig, ILoggerFactory loggerFactory) {
		this(injectConfig, new NoopEngineLooper(), loggerFactory);
	}
	
	/** Constructor with an inject config and a looper.
	 * 
	 * @param injectConfig
	 * @param defaultLooper
	 */
	public BaseEngineLauncher(IHierarchicalInjectConfig injectConfig, IEngineLooper defaultLooper) {
		this(injectConfig, defaultLooper, LoggingManager.getLoggerFactory());
	}
	
	/** Constructor with an inject config, a logger factory and a looper.
	 * 
	 * @param injectConfig
	 * @param defaultLooper
	 * @param loggerFactory
	 */
	public BaseEngineLauncher(IHierarchicalInjectConfig injectConfig, IEngineLooper defaultLooper, ILoggerFactory loggerFactory) {
		this.injectConfig = injectConfig;
		this.defaultLooper = defaultLooper;
		this.logger = loggerFactory.getLogger(getClass());
	}
	
	
	public IHierarchicalInjectConfig getInjectConfig() {
		return injectConfig;
	}
	
	public IEngineLooper getDefaultLooper() {
		return defaultLooper;
	}
	
	public ILogger getLogger() {
		return logger;
	}
	

	@Override
	public void launch(Class<? extends IGameAdapter> adapter) throws EngineException {
		BaseInjectSetup setup = new BaseInjectSetup();
		launchWithSetup(adapter, setup);
	}
	
	
	@Override
	public void launchWithSetup(Class<? extends IGameAdapter> adapter, IEngineSetup setup) throws EngineException {
		// Create the engine
		IEngineContext engineContext = createEngine(adapter, setup);

		// Load into TokTales
		TokTales.load(engineContext);

		
		// Setup the engine
		setupEngine(setup, engineContext);
		
		
		// Start the engine
		runEngine(engineContext);
	}


	/** Creates the engine context.
	 * 
	 * @param adapter
	 * @param setup
	 * @return
	 * @throws EngineException If an error occurs during creation.
	 */
	protected IEngineContext createEngine(Class<? extends IGameAdapter> adapter, IEngineSetup setup) throws EngineException {
		// Inject game adapter
		getInjectConfig().extend(new BaseSetupInjectModule(adapter));

		// Create engine context
		IEngineContext engineContext = setup.create(getInjectConfig());

		// Return the result
		return engineContext;
	}
	
	
	/** Set's up the engine.
	 * 
	 * @param setup
	 * @param engineContext
	 * @throws EngineException If an error occurs during setup.
	 */
	protected void setupEngine(IEngineSetup setup, IEngineContext engineContext) throws EngineException {
		// Run the given setup with the created engine context
		setup.run(engineContext);
	}
	
	
	/** Starts the game and runs the main loop after the engine has been setup.
	 * <p>
	 * You can override this method but you should call super to include game life-cycle handling.
	 * <p>
	 * Note: This method will only return when the game has ended.
	 * 
	 * @param engineContext
	 * @throws EngineException If an error occurs during execution.
	 * 
	 * @see #loop()
	 */
	protected void runEngine(IEngineContext engineContext) throws EngineException {
		startupEngine(engineContext);

		try {
			loop(engineContext, getDefaultLooper());
		}
		finally {
			shutdownEngine(engineContext);
		}
	}
	
	protected void startupEngine(IEngineContext engineContext) throws EngineException {
		engineContext.getGame().getGameControl().createGame(); // calls onCreate on adapter

		engineContext.getGame().getGameControl().startGame();
		engineContext.getGame().getGameControl().resumeGame();
	}
	
	protected void shutdownEngine(IEngineContext engineContext) throws EngineException {
		engineContext.getGame().getGameControl().pauseGame();
		engineContext.getGame().getGameControl().stopGame();
		
		engineContext.getGame().getGameControl().destroyGame();
	}
	
	
	/** The actual loop.
	 * The default implementation calls the looper.
	 * 
	 * @param defaultLooper
	 * @throws EngineException If an error occurs while looping.
	 */
	protected void loop(IEngineContext engineContext, IEngineLooper defaultLooper) throws EngineException {
		defaultLooper.loop(engineContext);
	}
	
}
