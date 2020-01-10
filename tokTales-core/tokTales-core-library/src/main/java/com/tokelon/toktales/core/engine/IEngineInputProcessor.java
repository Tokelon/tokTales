package com.tokelon.toktales.core.engine;

public interface IEngineInputProcessor {


	/** Runs the logic that will cause input events to be processed.
	 * <p>
	 * Usually this will happen in a global scope and therefore only a single input processor is needed.
	 * 
	 */
	public void process();
	
}
