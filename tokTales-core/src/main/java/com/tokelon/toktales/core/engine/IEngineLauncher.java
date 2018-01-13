package com.tokelon.toktales.core.engine;

import com.tokelon.toktales.core.engine.setup.IEngineSetup;
import com.tokelon.toktales.core.game.IGameAdapter;

public interface IEngineLauncher {

	
	/** Launches with the given game adapter and a default setup.
	 * 
	 * @param adapter
	 * @throws EngineException
	 */
	public void launch(IGameAdapter adapter) throws EngineException;
	
	
	/** Launches with the given game adapter and setup.
	 * 
	 * @param setup
	 * @throws EngineException
	 */
	public void launchWithSetup(IGameAdapter adapter, IEngineSetup setup) throws EngineException;
	
}
