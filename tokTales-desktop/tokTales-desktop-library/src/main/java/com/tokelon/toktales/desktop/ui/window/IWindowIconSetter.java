package com.tokelon.toktales.desktop.ui.window;

import com.tokelon.toktales.tools.core.dispose.IDisposable;

public interface IWindowIconSetter extends IDisposable {


	/** Sets the icon for the given window, using the given window toolkit if needed.
	 *
	 * @param window
	 * @param windowToolkit
	 */
	public void set(IWindow window, IWindowToolkit windowToolkit);


	/** The default implementation does nothing.
	 */
	@Override
	public default void dispose() {
		// Nothing
	}

}
