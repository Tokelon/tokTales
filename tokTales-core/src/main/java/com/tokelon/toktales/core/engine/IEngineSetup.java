package com.tokelon.toktales.core.engine;


public interface IEngineSetup {
	
	
	// ConfigurationModule
	
	/** Creates the engine context.
	 * <br><br> 
	 * Put any code in here that creates your dependencies.
	 * 
	 * @return The engine context including the engine and the game.
	 */
	public IEngineContext create();
	
	
	/** Runs any logic to setup the engine and game.
	 * Called after {@link #create()}, so all dependencies should be available.
	 * <br><br>
	 * Put any code in here that configures the engine.
	 * 
	 * @param context The engine context.
	 */
	public void run(IEngineContext context);
	
	
}
