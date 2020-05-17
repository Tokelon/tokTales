package com.tokelon.toktales.desktop.ui.window;

import com.tokelon.toktales.core.engine.IEngineContext;

/** Manages the life-cycle of a window context.
 */
public interface IWindowHandler {


	/** Creates the window context.
	 *
	 * @param engineContext
	 */
	public void createWindowContext(IEngineContext engineContext);

	/** Destroys the window context, freeing it's resources.
	 */
	public void destroyWindowContext();

	/**
	 * @return The window context, or null if none has been created.
	 */
	public IWindowContext getWindowContext();


	/**
	 * @return True if the context's window should close, false if not.
	 */
	public boolean windowShouldClose();

	/** Renders a frame of the window context.
	 */
	public void renderFrame();

	/** Processes any input of the window context.
	 */
	public void processWindowInput();

}
