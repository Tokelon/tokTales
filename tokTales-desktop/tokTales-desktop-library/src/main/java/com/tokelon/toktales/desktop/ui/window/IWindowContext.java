package com.tokelon.toktales.desktop.ui.window;

import com.tokelon.toktales.core.engine.IEngineContext;
import com.tokelon.toktales.desktop.input.IDesktopInputDriver;
import com.tokelon.toktales.desktop.render.IWindowRenderer;

/** Contains all properties needed to integrate a window into the engine.
 * <p>
 * A context must be initialized with {@link #create(IEngineContext)} before any of it's properties are accessed.
 */
public interface IWindowContext {


	/** Initializes this window context using the given engine context.
	 *
	 * @param engineContext
	 */
	public void create(IEngineContext engineContext);

	/** Destroys this window context.
	 */
	public void destroy();


	/**
	 * @return The window factory of this context.
	 */
	public IWindowFactory getWindowFactory();

	/**
	 * @return The window toolkit of this context.
	 */
	public IWindowToolkit getWindowToolkit();


	/**
	 * @return The window of this context.
	 */
	public IWindow getWindow();

	/**
	 * @return The renderer of this context.
	 */
	public IWindowRenderer getRenderer();

	/**
	 * @return The input driver of this context.
	 */
	public IDesktopInputDriver getInputDriver();

}
