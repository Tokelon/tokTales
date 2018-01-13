package com.tokelon.toktales.core.engine.setup;

import com.tokelon.toktales.core.engine.EngineException;
import com.tokelon.toktales.core.engine.IEngineContext;
import com.tokelon.toktales.core.engine.inject.IHierarchicalInjectConfig;

public interface IEngineSetup {
	
	
	/** Creates the engine context.
	 * <br><br> 
	 * Put any code in here that creates your dependencies.
	 * 
	 * @param injectConfig The inject config.
	 * 
	 * @return The engine context including the engine and the game.
	 */
	public IEngineContext create(IHierarchicalInjectConfig injectConfig) throws EngineException;
	
	
	/** Runs any logic to setup the engine and game.
	 * Called after {@link #create()}, so all dependencies should be available.
	 * <br><br>
	 * Put any code in here that configures the engine.
	 * 
	 * @param context The engine context.
	 */
	public void run(IEngineContext context) throws EngineException;
	
	
}
