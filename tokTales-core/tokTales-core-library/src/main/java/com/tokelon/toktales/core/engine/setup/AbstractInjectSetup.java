package com.tokelon.toktales.core.engine.setup;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import com.google.inject.Injector;
import com.google.inject.Stage;
import com.tokelon.toktales.core.engine.EngineException;
import com.tokelon.toktales.core.engine.IEngineContext;
import com.tokelon.toktales.core.engine.log.ILogger;
import com.tokelon.toktales.core.engine.log.ILoggerFactory;
import com.tokelon.toktales.core.engine.log.LoggingManager;
import com.tokelon.toktales.core.game.IGameAdapter;
import com.tokelon.toktales.tools.core.sub.inject.config.IHierarchicalInjectConfig;
import com.tokelon.toktales.tools.core.sub.inject.config.IInjectConfig;

import java9.util.Comparators;
import java9.util.stream.Stream;
import java9.util.stream.StreamSupport;

public abstract class AbstractInjectSetup implements IStepsEngineSetup {


	private SetupMode setupMode = SetupMode.PRODUCTION; // Default setup mode is production
	
	private final ISetupSteps steps;
	private final ILogger logger;
	
	/** Default constructor.
	 */
	protected AbstractInjectSetup() {
		this(new SetupSteps(), LoggingManager.getLoggerFactory());
	}
	
	/** Constructor with setup steps.
	 * 
	 * @param steps
	 */
	protected AbstractInjectSetup(ISetupSteps steps) {
		this(steps, LoggingManager.getLoggerFactory());
	}

	/** Constructor with a logger factory.
	 * 
	 * @param loggerFactory
	 */
	protected AbstractInjectSetup(ILoggerFactory loggerFactory) {
		this(new SetupSteps(), loggerFactory);
	}
	
	/** Constructor with setup steps and a logger factory.
	 * 
	 * @param steps
	 * @param loggerFactory
	 */
	protected AbstractInjectSetup(ISetupSteps steps, ILoggerFactory loggerFactory) {
		this.logger = loggerFactory.getLogger(getClass());
		this.steps = new SetupSteps();
	}

	
	@Override
	public IEngineContext create(IHierarchicalInjectConfig injectConfig) throws EngineException {
		logger.info("Engine creation started");
		logger.info("Engine setup is running in mode {}", setupMode);
		
		Injector injector = createInjector(injectConfig, setupMode);
		IEngineContext engineContext = createEngineContext(injector);
		
		return engineContext;
	}

	
	@Override
	public void buildUp(IEngineContext engineContext) throws EngineException {
		buildUpSteps(getSteps(), engineContext);
		
		createGameAdapter(engineContext.getInjector());
	}
	
	@Override
	public void tearDown(IEngineContext engineContext) throws EngineException {
		tearDownSteps(getSteps(), engineContext);
	}
	
	
	protected void buildUpSteps(ISetupSteps setupSteps, IEngineContext engineContext) throws EngineException {
		Map<String, ISetupStep> stepsFromNames = setupSteps.createNamesToSteps();
		Map<String, Double> positionsFromNames = setupSteps.createStepNamesToPositions();
		
		Stream<String> sortedStepNames = StreamSupport.stream(positionsFromNames.entrySet())
				.sequential()
				.sorted(Comparators.comparingDouble(e -> e.getValue()))
				.map(entry -> entry.getKey());
		
		for (Iterator<String> iterator = sortedStepNames.iterator(); iterator.hasNext();) {
			String stepName = iterator.next();
			ISetupStep step = stepsFromNames.get(stepName);
			
			step.onBuildUp(engineContext);
		}
	}
	
	protected void tearDownSteps(ISetupSteps setupSteps, IEngineContext engineContext) throws EngineException {
		Map<String, ISetupStep> stepsFromNames = setupSteps.createNamesToSteps();
		Map<String, Double> positionsFromNames = setupSteps.createStepNamesToPositions();
		
		Stream<String> sortedStepNames = StreamSupport.stream(positionsFromNames.entrySet())
				.sequential()
				.sorted(Comparators.comparingDouble((Entry<String, Double> e) -> e.getValue()).reversed())
				.map(entry -> entry.getKey());
		
		for (Iterator<String> iterator = sortedStepNames.iterator(); iterator.hasNext();) {
			String stepName = iterator.next();
			ISetupStep step = stepsFromNames.get(stepName);
			
			step.onTearDown(engineContext);
		}
	}



	/**
	 * @return A logger for this setup.
	 */
	protected ILogger getLogger() {
		return logger;
	}
	
	@Override
	public ISetupSteps getSteps() {
		return steps;
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
