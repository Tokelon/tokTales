package com.tokelon.toktales.core.engine.setup;

import com.tokelon.toktales.core.engine.EngineException;
import com.tokelon.toktales.core.engine.IEngineContext;
import com.tokelon.toktales.tools.core.sub.inject.config.IHierarchicalInjectConfig;

public interface IEngineSetup {
	
	
	/** Creates the engine context.
	 * 
	 * @param injectConfig The inject config that should be used.
	 * 
	 * @return The engine context including the engine and the game.
	 */
	public IEngineContext create(IHierarchicalInjectConfig injectConfig) throws EngineException;
	
	/** Runs any logic to setup the engine and game.
	 * <p>
	 * Called after {@link #create}, so the engine context is available at this point.
	 * 
	 * @param context The engine context.
	 */
	public void run(IEngineContext context) throws EngineException;
	
	
	/** Sets the mode in which the setup should be run.
	 * @param mode A setup mode.
	 */
	public void setSetupMode(SetupMode mode);
	
	/** Returns the mode in which the setup should be run.
	 * @return The current setup mode.
	 */
	public SetupMode getSetupMode();
	
	
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
	
}
