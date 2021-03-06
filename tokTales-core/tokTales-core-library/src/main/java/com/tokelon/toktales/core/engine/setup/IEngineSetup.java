package com.tokelon.toktales.core.engine.setup;

import com.tokelon.toktales.core.engine.EngineException;
import com.tokelon.toktales.core.engine.IEngineContext;
import com.tokelon.toktales.tools.core.sub.inject.config.IHierarchicalInjectConfig;

/** Manages the engine context including it's creation, configuration and destruction.
 */
public interface IEngineSetup {


	/** Modes in which the engine setup can be run.
	 * <p>
	 * Unless you need debug information you should use {@link #PRODUCTION}.
	 * <p>
	 * The mode dictates in which Stage the injector will be created.
	 *
	 */
	public enum SetupMode {
		DEVELOPMENT,
		PRODUCTION
	}


	/** Creates the engine context using the given inject config.
	 *
	 * @param injectConfig
	 *
	 * @return A new engine context.
	 */
	public IEngineContext create(IHierarchicalInjectConfig injectConfig) throws EngineException; // Hierarchical inject config is passed here to allow the setup to modify modules

	/** Prepares the engine for running.
	 *
	 * @param engineContext
	 * @throws EngineException If there is an error during setup.
	 */
	public void buildUp(IEngineContext engineContext) throws EngineException;

	/** Cleans up after the engine has ran.
	 *
	 * @param engineContext
	 * @throws EngineException If there is an error during setup.
	 */
	public void tearDown(IEngineContext engineContext) throws EngineException;


	/**
	 * @return The steps that should be used for this setup.
	 */
	public ISetupSteps getSteps();


	/** Sets the mode in which the setup should be run.
	 * @param mode A setup mode.
	 */
	public void setSetupMode(SetupMode mode);

	/** Returns the mode in which the setup should be run.
	 * @return The current setup mode.
	 */
	public SetupMode getSetupMode();

}
