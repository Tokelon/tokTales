package com.tokelon.toktales.desktop.engine;

import com.tokelon.toktales.core.engine.EngineException;
import com.tokelon.toktales.core.engine.IEngineContext;
import com.tokelon.toktales.core.engine.IEngineInputProcessor;
import com.tokelon.toktales.core.engine.IEngineLooper;

public abstract class AbstractEngineLooper implements IEngineLooper {


	private boolean isStopped;
	
	private final IEngineInputProcessor inputProcessor;
	
	/** Default constructor.
	 * <p>
	 * A no-op input processor will be used.
	 * 
	 */
	public AbstractEngineLooper() {
		this(() -> {});
	}
	
	/** Constructor with an input processor.
	 * 
	 * @param inputProcessor
	 */
	public AbstractEngineLooper(IEngineInputProcessor inputProcessor) {
		this.inputProcessor = inputProcessor;
	}
	
	
	@Override
	public void loop(IEngineContext engineContext) throws EngineException {
		this.isStopped = false;
		
		while (shouldLoop(engineContext)) {
			update(engineContext);
			
			render(engineContext);
			
			processInput(engineContext);
		}
	}
	

	@Override
	public void stop() {
		this.isStopped = true;
	}

	
	/** Runs the rendering of the engine.
	 * 
	 * @param engineContext
	 */
	protected abstract void render(IEngineContext engineContext);
	

	/** Returns whether this looper should continue looping, or stop.
	 * <p>
	 * The default implementation returns false if this looper has been stopped.
	 * 
	 * @param engineContext
	 * @return True if looping should be continued, false if not.
	 */
	protected boolean shouldLoop(IEngineContext engineContext) {
		return !isStopped();
	}
	
	/** Runs the updating of the engine.
	 * <p>
	 * The default implementation updates the game.
	 * 
	 * @param engineContext
	 */
	protected void update(IEngineContext engineContext) {
		engineContext.getEngine().getEngineDriver().update();
	}
	
	/** Runs the processing of input of the engine.
	 * <p>
	 * The default implementation calls the processing of this looper's input processor.
	 * 
	 * @param engineContext
	 */
	protected void processInput(IEngineContext engineContext) {
		engineContext.getEngine().getEngineDriver().processInput(inputProcessor);
	}
	
	
	/** Returns whether this looper has been stopped.
	 * 
	 * @return True if this looper has been stopped, false if not.
	 */
	protected boolean isStopped() {
		return isStopped;
	}
	
	/**
	 * @return The input processor for this looper.
	 */
	protected IEngineInputProcessor getInputProcessor() {
		return inputProcessor;
	}
	
}
