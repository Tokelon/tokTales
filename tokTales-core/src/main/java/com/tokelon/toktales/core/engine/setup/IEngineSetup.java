package com.tokelon.toktales.core.engine.setup;

import com.tokelon.toktales.core.engine.EngineException;
import com.tokelon.toktales.core.engine.IEngineContext;
import com.tokelon.toktales.core.engine.inject.IHierarchicalInjectConfig;

public interface IEngineSetup {
	
	
	/** Creates the engine context.
	 * 
	 * @param injectConfig The inject config.
	 * 
	 * @return The engine context including the engine and the game.
	 */
	public IEngineContext create(IHierarchicalInjectConfig injectConfig) throws EngineException;
	
	
	/** Runs any logic to setup the engine and game.
	 * <p>
	 * Called after {@link #create()}, so the engine context is available at this point.
	 * 
	 * @param context The engine context.
	 */
	public void run(IEngineContext context) throws EngineException;
	
	
}
