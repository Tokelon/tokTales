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


	private final ILogger logger;
	private final IHierarchicalInjectConfig injectConfig;
	private final IEngineLooper looper;

	/** Constructor with an inject config.
	 * <p>
	 * A no-op looper will be used.
	 * 
	 * @param injectConfig
	 */
	protected BaseEngineLauncher(IHierarchicalInjectConfig injectConfig) {
		this(LoggingManager.getLoggerFactory(), injectConfig, () -> {});
	}
	
	/** Constructor with a logger factory and and inject config.
	 * <p>
	 * A no-op looper will be used.
	 * 
	 * @param loggerFactory
	 * @param injectConfig
	 */
	protected BaseEngineLauncher(ILoggerFactory loggerFactory, IHierarchicalInjectConfig injectConfig) {
		this(loggerFactory, injectConfig, () -> {});
	}
	
	/** Constructor with an inject config and a looper.
	 * 
	 * @param injectConfig
	 * @param looper
	 */
	public BaseEngineLauncher(IHierarchicalInjectConfig injectConfig, IEngineLooper looper) {
		this(LoggingManager.getLoggerFactory(), injectConfig, looper);
	}
	
	/** Constructor with a logger factory, an inject config and a looper.
	 * 
	 * @param loggerFactory
	 * @param injectConfig
	 * @param looper
	 */
	public BaseEngineLauncher(ILoggerFactory loggerFactory, IHierarchicalInjectConfig injectConfig, IEngineLooper looper) {
		this.logger = loggerFactory.getLogger(getClass());
		this.injectConfig = injectConfig;
		this.looper = looper;
	}
	
	
	public ILogger getLogger() {
		return logger;
	}
	
	public IHierarchicalInjectConfig getInjectConfig() {
		return injectConfig;
	}
	
	public IEngineLooper getEngineLooper() {
		return looper;
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
		startEngine(engineContext);
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
		injectConfig.extend(new BaseSetupInjectModule(adapter));

		// Create engine context
		IEngineContext engineContext = setup.create(injectConfig);

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
	protected void startEngine(IEngineContext engineContext) throws EngineException {
		engineContext.getGame().getGameControl().createGame(); // calls onCreate on adapter

		engineContext.getGame().getGameControl().startGame();
		engineContext.getGame().getGameControl().resumeGame();
		try {
			loop();
		}
		finally {
			engineContext.getGame().getGameControl().pauseGame();
			engineContext.getGame().getGameControl().stopGame();
			
			engineContext.getGame().getGameControl().destroyGame();
		}
	}
	
	/** The actual loop.
	 * The default implementation calls the looper.
	 * 
	 * @throws EngineException If an error occurs while looping.
	 */
	protected void loop() throws EngineException {
		looper.loop();
	}
	
}
