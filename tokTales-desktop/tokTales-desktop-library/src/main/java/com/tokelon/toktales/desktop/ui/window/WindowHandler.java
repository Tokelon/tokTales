package com.tokelon.toktales.desktop.ui.window;

import com.tokelon.toktales.core.engine.IEngineContext;
import com.tokelon.toktales.desktop.render.IWindowRenderer;

/** Default implementation of {@link IWindowHandler}.
 */
public class WindowHandler implements IWindowHandler {


	private final IWindowContext windowContext;

	/** Constructor with a window context.
	 *
	 * @param windowContext
	 */
	public WindowHandler(IWindowContext windowContext) {
		this.windowContext = windowContext;
	}


	@Override
	public void createWindowContext(IEngineContext engineContext) {
		windowContext.create(engineContext);
	}

	@Override
	public void destroyWindowContext() {
		windowContext.destroy();
	}

	@Override
	public IWindowContext getWindowContext() {
		return windowContext;
	}


	@Override
	public boolean windowShouldClose() {
		return windowContext.getWindow().shouldClose();
	}

	@Override
	public void renderFrame() {
		IWindowRenderer renderer = windowContext.getRenderer();

		renderer.prepareFrame();

		//engineDriver.render(); // Move this out of the renderer and into here?
		renderer.drawFrame();

		renderer.commitFrame();
	}

	@Override
	public void processWindowInput() {
		// Nothing - Input processing is generally done externally, once for all windows
	}

}
