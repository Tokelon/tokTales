package com.tokelon.toktales.core.engine;

/** Manages the main loop of the engine.
 */
public interface IEngineLooper {


	/** Runs the main engine loop.
	 *
	 * @param engineContext
	 * @throws EngineException If an error occurs while looping.
	 */
	public void loop(IEngineContext engineContext) throws EngineException;


	/** Sets this looper to stop on the next loop.
	 */
	public void stop();

}
