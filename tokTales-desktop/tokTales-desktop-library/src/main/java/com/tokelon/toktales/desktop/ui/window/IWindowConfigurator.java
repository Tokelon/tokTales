package com.tokelon.toktales.desktop.ui.window;

/** Configures windows, essentially acting as a delegate for window configuration.
 */
public interface IWindowConfigurator {


	/** Configures the given window, using the given window toolkit if needed.
	 *
	 * @param window
	 * @param windowToolkit
	 */
	public void configure(IWindow window, IWindowToolkit windowToolkit);

}
