package com.tokelon.toktales.core.engine;

public interface IEngineDriver {
	// Add IGame, IEngineRenderer to methods?


	/** Runs the creation of the engine.
	 */
	public void create();
	
	/** Runs the starting of the engine.
	 */
	public void start();
	
	/** Runs the resuming of the engine.
	 */
	public void resume();
	
	/** Runs the pausing of the engine.
	 */
	public void pause();
	
	/** Runs the stopping of the engine.
	 */
	public void stop();
	
	/** Runs the destruction of the engine.
	 */
	public void destroy();

	
	/** Runs the updating of the engine.
	 */
	public void update();
	
	/** Runs the rendering of the engine.
	 */
	public void render();
	
	/** Runs the processing of input of the engine.
	 * 
	 * @param inputProcessor
	 */
	public void processInput(IEngineInputProcessor inputProcessor);
	
}
