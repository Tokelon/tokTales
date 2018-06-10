package com.tokelon.toktales.core.engine;

public interface IEngineLooper {

	
	/** The main engine loop.
	 * 
	 * @throws EngineException If an error occurs while looping.
	 */
	public void loop() throws EngineException;
	
}
