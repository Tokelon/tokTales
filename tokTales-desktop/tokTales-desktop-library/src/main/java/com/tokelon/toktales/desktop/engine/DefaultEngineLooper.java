package com.tokelon.toktales.desktop.engine;

import com.tokelon.toktales.core.engine.EngineException;
import com.tokelon.toktales.core.engine.IEngineContext;
import com.tokelon.toktales.core.engine.IEngineInputProcessor;
import com.tokelon.toktales.core.engine.IEngineLooper;
import com.tokelon.toktales.desktop.render.IWindowRenderer;

public class DefaultEngineLooper implements IEngineLooper {


	private boolean isStopped;
	
	private final IWindowRenderer renderer;
	private final IEngineInputProcessor inputProcessor;

	/** Constructor with a window renderer.
	 * <p>
	 * A no-op input processor will be used.
	 * 
	 * @param renderer
	 */
	public DefaultEngineLooper(IWindowRenderer renderer) {
		this(renderer, () -> {});
	}
	
	/** Constructor with a window renderer and an input processor.
	 * 
	 * @param renderer
	 * @param inputProcessor
	 */
	public DefaultEngineLooper(IWindowRenderer renderer, IEngineInputProcessor inputProcessor) {
		this.renderer = renderer;
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

	
	/** Returns whether this looper should continue looping, or stop.
	 * <p>
	 * The default implementation considers whether the renderer window should close,
	 * and whether this looper has been stopped.
	 * 
	 * @param engineContext
	 * @return True if looping should be continued, false if not.
	 */
	protected boolean shouldLoop(IEngineContext engineContext) {
		/* Run the loop until the user has attempted to close the window,
		 * or the looper has been stopped manually.
		 */
		return !isStopped && !getRenderer().getWindow().shouldClose();
	}
	
	/** Runs the updating of the engine.
	 * <p>
	 * The default implementation updates the game.
	 * 
	 * @param engineContext
	 */
	protected void update(IEngineContext engineContext) {
		engineContext.getGame().getGameControl().updateGame();
	}
	
	/** Runs the rendering of the engine.
	 * <p>
	 * The default implementation calls the frame rendering methods of this looper's renderer.
	 * 
	 * @param engineContext
	 */
	protected void render(IEngineContext engineContext) {
		getRenderer().prepareFrame();
		
		getRenderer().drawFrame();
		
		getRenderer().commitFrame();
	}
	
	/** Runs the processing of input of the engine.
	 * <p>
	 * The default implementation calls the processing of this looper's input processor.
	 * 
	 * @param engineContext
	 */
	protected void processInput(IEngineContext engineContext) {
		getInputProcessor().process();
	}
	
	
	/**
	 * @return The window renderer for this looper.
	 */
	protected IWindowRenderer getRenderer() {
		return renderer;
	}

	/**
	 * @return The input processor for this looper.
	 */
	protected IEngineInputProcessor getInputProcessor() {
		return inputProcessor;
	}
	
}
