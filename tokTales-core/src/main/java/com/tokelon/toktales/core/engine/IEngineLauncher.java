package com.tokelon.toktales.core.engine;

import com.tokelon.toktales.core.game.IGameAdapter;

public interface IEngineLauncher {

	
	/** Launches the engine with the given game adapter.
	 * The default setup will be used.
	 * 
	 * @param adapter
	 * @throws EngineException
	 */
	public void launch(IGameAdapter adapter) throws EngineException;
	
	
	/** Launches the engine with a custom setup.
	 * 
	 * @param setup
	 * @throws EngineException
	 */
	public void launchAndSetup(IEngineSetup setup) throws EngineException;
	
}
