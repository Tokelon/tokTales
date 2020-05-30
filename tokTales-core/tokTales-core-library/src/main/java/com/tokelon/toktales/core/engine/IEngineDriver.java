package com.tokelon.toktales.core.engine;

/** Connects the engine lifecycle to the game lifecycle.
 * <p>
 * Any actions originating from the engine towards the game should go through the driver.
 */
public interface IEngineDriver {
	// Should this be called IGameDriver?
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
