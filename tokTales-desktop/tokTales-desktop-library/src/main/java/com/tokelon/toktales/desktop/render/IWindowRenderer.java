package com.tokelon.toktales.desktop.render;

import com.tokelon.toktales.desktop.ui.window.IWindow;

/** Manages the rendering context and coordinates the frames for a window.
 */
public interface IWindowRenderer {


	/** Creates this renderer using the given window.
	 * <p>
	 * Must be called from the main thread.
	 *
	 * @param window
	 */
	public void create(IWindow window);

	/** Creates the rendering context for this renderer.
	 * <p>
	 * Must be called from the thread that the renderer will be associated to.
	 * <p>
	 * Can only be called after {@link #create(IWindow)} has been called.
	 */
	public void createContext(); // TODO: Add ISurfaceManager here, similar to IViewRenderer?

	/** Destroys the rendering context for this renderer.
	 * <p>
	 * Must be called from the thread that this renderer is associated to.
	 * <p>
	 * Can only be called after {@link #createContext()} has been called.
	 */
	public void destroyContext();

	/** Destroys this renderer.
	 * <p>
	 * Can only be called after {@link #destroyContext()}.
	 */
	public void destroy();


	/** Prepares a new frame to be drawn.
	 */
	public void prepareFrame();

	/** Draws the frame.
	 */
	public void drawFrame();

	/** Commits the frame to the graphics buffer.
	 */
	public void commitFrame();


	/**
	 * @return The window for this renderer.
	 */
	public IWindow getWindow();

}
