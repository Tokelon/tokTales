package com.tokelon.toktales.desktop.engine;

import com.tokelon.toktales.core.engine.IEngineContext;
import com.tokelon.toktales.core.engine.IEngineInputProcessor;
import com.tokelon.toktales.core.engine.IEngineLooper;
import com.tokelon.toktales.desktop.ui.window.IWindowHandler;

/** Implementation of {@link IEngineLooper} around a window.
 * <p>
 * Supports rendering of the window, as well as updating state and processing input through the engine driver.
 * <p>
 * Remember to pass an input processor for the window if needed.
 */
public class WindowEngineLooper extends AbstractEngineLooper {


	private final IWindowHandler windowHandler;

	/** Constructor with a window handler.
	 * <p>
	 * A no-op input processor will be used.
	 *
	 * @param windowHandler
	 */
	public WindowEngineLooper(IWindowHandler windowHandler) {
		super();

		this.windowHandler = windowHandler;
	}

	/** Constructor with a window handler and an input processor.
	 *
	 * @param windowHandler
	 * @param inputProcessor
	 */
	public WindowEngineLooper(IWindowHandler windowHandler, IEngineInputProcessor inputProcessor) {
		super(inputProcessor);

		this.windowHandler = windowHandler;
	}


	@Override
	protected boolean shouldLoop(IEngineContext engineContext) {
		/* Run the loop until the user has attempted to close the window,
		 * or the looper has been stopped manually.
		 */
		return super.shouldLoop(engineContext) && !windowHandler.windowShouldClose();
	}

	@Override
	protected void render(IEngineContext engineContext) {
		windowHandler.renderFrame();
	}

	/**
	 * @return The window handler for this looper.
	 */
	protected IWindowHandler getWindowHandler() {
		return windowHandler;
	}

}
