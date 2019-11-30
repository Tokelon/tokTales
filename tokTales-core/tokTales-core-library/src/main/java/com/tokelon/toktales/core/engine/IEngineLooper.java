package com.tokelon.toktales.core.engine;

public interface IEngineLooper {


	/** The main engine loop.
	 * 
	 * @param engineContext
	 * @throws EngineException If an error occurs while looping.
	 */
	public void loop(IEngineContext engineContext) throws EngineException;
	
}
