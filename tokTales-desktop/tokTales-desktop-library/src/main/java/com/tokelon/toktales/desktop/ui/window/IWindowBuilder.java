package com.tokelon.toktales.desktop.ui.window;

/** Prepares and produces windows, essentially acting as a delegate for window creation.
 */
public interface IWindowBuilder {


	/** Produces a window, using the given window factory and toolkit if needed.
	 *
	 * @param windowFactory
	 * @param windowToolkit
	 * @return A window.
	 */
	public IWindow build(IWindowFactory windowFactory, IWindowToolkit windowToolkit);

}
