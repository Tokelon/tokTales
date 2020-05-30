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

/** Platform independent implementation of {@link IEngineSetup}.
 */
public class BaseEngineSetup implements IEngineSetup {


	private SetupMode setupMode = SetupMode.PRODUCTION; // Default setup mode is production

	private final ISetupSteps steps;
	private final ILogger logger;

	/** Default constructor.
	 */
	protected BaseEngineSetup() {
		this(new SetupSteps(), LoggingManager.getLoggerFactory());
	}

	/** Constructor with setup steps.
	 *
	 * @param steps
	 */
	protected BaseEngineSetup(ISetupSteps steps) {
		this(steps, LoggingManager.getLoggerFactory());
	}

	/** Constructor with a logger factory.
	 *
	 * @param loggerFactory
	 */
	protected BaseEngineSetup(ILoggerFactory loggerFactory) {
		this(new SetupSteps(), loggerFactory);
	}

	/** Constructor with setup steps and a logger factory.
	 *
	 * @param steps
	 * @param loggerFactory
	 */
	protected BaseEngineSetup(ISetupSteps steps, ILoggerFactory loggerFactory) {
		this.logger = loggerFactory.getLogger(getClass());
		this.steps = new SetupSteps();
	}


	@Override
	public IEngineContext create(IHierarchicalInjectConfig injectConfig) throws EngineException {
		getLogger().info("Engine creation started");
		getLogger().info("Engine setup is running in mode {}", setupMode);

		Injector injector = createInjector(injectConfig, setupMode);
		IEngineContext engineContext = createEngineContext(injector);

		return engineContext;
	}


	@Override
	public void buildUp(IEngineContext engineContext) throws EngineException {
		getLogger().debug("Running setup build up");
		buildUpSteps(getSteps(), engineContext);

		createGameAdapter(engineContext.getInjector());
	}

	@Override
	public void tearDown(IEngineContext engineContext) throws EngineException {
		getLogger().debug("Running setup tear down");
		tearDownSteps(getSteps(), engineContext);
	}


	/** Calls build up for all given setup steps, in order of their positions.
	 *
	 * @param setupSteps
	 * @param engineContext
	 * @throws EngineException If there are errors during build up.
	 */
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

			getLogger().debug("Running build up for setup step: {}", stepName);
			step.onBuildUp(engineContext);
		}
	}

	/** Calls tear down for all given setup steps, in reverse order of their positions.
	 *
	 * @param setupSteps
	 * @param engineContext
	 * @throws EngineException If there are errors during tear down.
	 */
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

			getLogger().debug("Running tear down for setup step: {}", stepName);
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


	/** Creates a dependency injector using the given inject config and setup mode.
	 *
	 * @param injectConfig
	 * @param mode
	 * @return An injector for the engine.
	 * @throws EngineException If there are errors during injector creation.
	 */
	protected Injector createInjector(IInjectConfig injectConfig, SetupMode mode) throws EngineException { // Exception is not actually needed
		Stage stage = mode == SetupMode.DEVELOPMENT ? Stage.DEVELOPMENT : Stage.PRODUCTION;
		getLogger().info("Determined injector stage as {}", stage);

		getLogger().info("Creating engine injector...");
		long before = System.currentTimeMillis();

		Injector injector = injectConfig.createInjector(stage);

		getLogger().info("...injector creation took {} ms", (System.currentTimeMillis() - before));
		return injector;
	}

	/** Creates the engine context using the given injector.
	 *
	 * @param injector
	 * @return An engine context for the engine.
	 */
	protected IEngineContext createEngineContext(Injector injector) {
		getLogger().info("Creating engine context...");
		long before = System.currentTimeMillis();

		IEngineContext engineContext = injector.getInstance(IEngineContext.class);

		getLogger().info("...engine context creation took {} ms", (System.currentTimeMillis() - before));
		return engineContext;
	}

	/** Creates a game adapter using the given injector.
	 *
	 * @param injector
	 * @return A game adapter for the engine.
	 */
	protected IGameAdapter createGameAdapter(Injector injector) {
		getLogger().info("Creating game adapter...");
		long before = System.currentTimeMillis();

		IGameAdapter gameAdapter = injector.getInstance(IGameAdapter.class);

		getLogger().info("...game adapter creation took {} ms", (System.currentTimeMillis() - before));
		return gameAdapter;
	}

}
