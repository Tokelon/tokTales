package com.tokelon.toktales.core.engine;

import com.tokelon.toktales.core.engine.setup.IEngineSetup;
import com.tokelon.toktales.core.game.IGameAdapter;

public interface IEngineLauncher {
	
	/* IGameAdapter binding:
	 * External binding of game adapter is discouraged,
	 * to avoid problematic bindings (no scope, to instance) and further confusion.
	 * 
	 * If truly necessary, the binding can be overridden in custom IEngineSetup.
	 */
	
	
	/** Launches with the given game adapter and the default setup.
	 * 
	 * @param adapter
	 * @throws EngineException
	 */
	public void launch(Class<? extends IGameAdapter> adapter) throws EngineException;


	/** Launches with the given game adapter and setup.
	 * 
	 * @param setup
	 * @throws EngineException
	 */
	public void launchWithSetup(Class<? extends IGameAdapter> adapter, IEngineSetup setup) throws EngineException;
	

}
