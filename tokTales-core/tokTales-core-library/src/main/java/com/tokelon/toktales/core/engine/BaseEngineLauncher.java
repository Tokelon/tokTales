package com.tokelon.toktales.core.engine;

import com.tokelon.toktales.core.engine.inject.BaseLauncherInjectModule;
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
	
	
	/**
	 * @return The inject config for this launcher.
	 */
	public IHierarchicalInjectConfig getInjectConfig() {
		return injectConfig;
	}

	/**
	 * @return The default looper for this launcher.
	 */
	public IEngineLooper getDefaultLooper() {
		return defaultLooper;
	}
	
	/**
	 * @return The logger for this launcher.
	 */
	public ILogger getLogger() {
		return logger;
	}
	

	@Override
	public void launch(Class<? extends IGameAdapter> adapter) throws EngineException {
		launchWithSetup(adapter, createDefaultSetup());
	}
	
	
	@Override
	public void launchWithSetup(Class<? extends IGameAdapter> adapter, IEngineSetup setup) throws EngineException {
		// Extend inject config
		extendInjectConfig(adapter, setup);
		
		// Create the engine
		IEngineContext engineContext = createEngine(adapter, setup);

		// Load into TokTales
		TokTales.load(engineContext);

		
		// Setup the engine
		setupEngine(setup, engineContext);
		
		
		// Start the engine
		runEngine(engineContext);
	}
	
	
	@Override
	public void terminate() {
		getDefaultLooper().stop();
	}
	
	
	/** Creates a default setup that will be used in case no setup was given. 
	 * 
	 * @return A new instance of the default setup implementation.
	 */
	protected IEngineSetup createDefaultSetup() {
		return new BaseInjectSetup();
	}
	
	
	/** Extends the inject config with launching modules.
	 * <p>
	 * The default implementation registers this launcher and the given game adapter.
	 * 
	 * @param adapter
	 * @param setup
	 */
	protected void extendInjectConfig(Class<? extends IGameAdapter> adapter, IEngineSetup setup) {
		// Inject this launcher
		getInjectConfig().extend(new BaseLauncherInjectModule(this));
		
		// Inject game adapter
		getInjectConfig().extend(new BaseSetupInjectModule(adapter));
	}


	/** Creates the engine context.
	 * 
	 * @param adapter
	 * @param setup
	 * @return The engine context.
	 * @throws EngineException If an error occurs during creation.
	 */
	protected IEngineContext createEngine(Class<? extends IGameAdapter> adapter, IEngineSetup setup) throws EngineException {
		return setup.create(getInjectConfig());
	}
	
	
	/** Runs the engine setup with the given context.
	 * 
	 * @param setup
	 * @param engineContext
	 * @throws EngineException If an error occurs during setup.
	 */
	protected void setupEngine(IEngineSetup setup, IEngineContext engineContext) throws EngineException {
		// Run the given setup with the created engine context
		setup.run(engineContext);
	}
	
	
	/** Runs the engine.<br>
	 * The default implementation steps are as follows:<br>
	 * 1. Start up the engine by calling {@link #startupEngine(IEngineContext)}.<br>
	 * 2. Run the main loop by calling {@link #loop(IEngineContext, IEngineLooper)}.<br>
	 * 3. Shut down the engine by calling {@link #shutdownEngine(IEngineContext)}, after {@link #terminate()} has been called or the looper has stopped. 
	 * <p>
	 * Generally this method will not return until the engine should exit.
	 * 
	 * @param engineContext
	 * @throws EngineException If an error occurs during execution.
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
	
	/** Runs the startup logic for the engine.
	 * <p>
	 * The default implementation calls the methods responsible for starting the game lifecycle.
	 * 
	 * @param engineContext
	 * @throws EngineException If an error occurs during startup.
	 */
	protected void startupEngine(IEngineContext engineContext) throws EngineException {
		engineContext.getEngine().getEngineDriver().create(); // calls onCreate on adapter

		engineContext.getEngine().getEngineDriver().start();
		engineContext.getEngine().getEngineDriver().resume();
	}
	
	/** Runs the shutdown logic for the engine.
	 * <p>
	 * The default implementation calls the methods responsible for ending the game lifecycle.
	 * 
	 * @param engineContext
	 * @throws EngineException If an error occurs during shutdown.
	 */
	protected void shutdownEngine(IEngineContext engineContext) throws EngineException {
		engineContext.getEngine().getEngineDriver().pause();
		engineContext.getEngine().getEngineDriver().stop();
		
		engineContext.getEngine().getEngineDriver().destroy();
	}
	
	
	/** Runs the main loop.
	 * <p>
	 * The default implementation simply calls the default looper.
	 * 
	 * @param engineContext
	 * @param defaultLooper
	 * @throws EngineException If an error occurs while looping.
	 */
	protected void loop(IEngineContext engineContext, IEngineLooper defaultLooper) throws EngineException {
		defaultLooper.loop(engineContext);
	}
	
}
