package com.tokelon.toktales.desktop.engine;

import com.tokelon.toktales.core.engine.EngineException;
import com.tokelon.toktales.core.engine.IEngineContext;
import com.tokelon.toktales.core.engine.IEngineLooper;
import com.tokelon.toktales.desktop.render.IWindowRenderer;

public class DefaultEngineLooper implements IEngineLooper {


	private boolean running;
	
	private final IWindowRenderer renderer;

	public DefaultEngineLooper(IWindowRenderer renderer) {
		this.renderer = renderer;
	}
	
	
	@Override
	public void loop(IEngineContext engineContext) throws EngineException {
		this.running = true;
		
		while (shouldLoop(engineContext)) {
			update(engineContext);
			
			render(engineContext);
			
			processInput(engineContext);
		}
	}
	
	
	protected boolean shouldLoop(IEngineContext engineContext) {
		/* Run the loop until the user has attempted to close the window,
		 * or the looper has been stopped manually.
		 */
		return running && !getRenderer().getWindow().shouldClose();
	}
	
	protected void update(IEngineContext engineContext) {
		engineContext.getGame().getGameControl().updateGame();
	}
	
	protected void render(IEngineContext engineContext) {
		getRenderer().prepareFrame();
		
		getRenderer().drawFrame();
		
		getRenderer().commitFrame();
	}
	
	protected void processInput(IEngineContext engineContext) {
		// Nothing
	}
	
	
	public void stop() {
		this.running = false;
	}
	
	protected IWindowRenderer getRenderer() {
		return renderer;
	}
	
}
