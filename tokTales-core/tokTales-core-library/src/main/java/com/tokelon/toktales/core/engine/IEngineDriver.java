package com.tokelon.toktales.core.engine;

public interface IEngineDriver {
	// Add IGame, IEngineRenderer to methods?


	/*
	public void create();
	
	public void start();
	
	public void resume();
	
	public void pause();
	
	public void stop();
	
	public void destroy();
	*/
	
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
