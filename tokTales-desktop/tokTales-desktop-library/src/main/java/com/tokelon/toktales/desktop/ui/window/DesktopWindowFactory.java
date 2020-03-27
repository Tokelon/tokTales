package com.tokelon.toktales.desktop.ui.window;

import com.tokelon.toktales.desktop.lwjgl.ui.LWJGLWindowFactory;

/** Default implementation of {@link IWindowFactory}.
 */
public class DesktopWindowFactory extends LWJGLWindowFactory {


	/** Default constructor.
	 */
	public DesktopWindowFactory() {
		super();
	}

	/** Constructor with a window toolkit.
	 *
	 * @param windowToolkit
	 */
	public DesktopWindowFactory(IWindowToolkit windowToolkit) {
		super(windowToolkit);
	}

}
