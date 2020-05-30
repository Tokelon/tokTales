package com.tokelon.toktales.core.engine;

/** Generates input events for the engine.
 * <p>
 * This may include calling external libraries or triggering the dispatching of such events.
 */
public interface IEngineInputProcessor {


	/** Runs the logic that will cause input events to be processed.
	 * <p>
	 * Often this will happen in a global scope and therefore only a single input processor will be needed.
	 *
	 */
	public void process();

}
