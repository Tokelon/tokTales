package com.tokelon.toktales.desktop.ui.window;

import com.tokelon.toktales.desktop.lwjgl.ui.IWindowContextFactory;
import com.tokelon.toktales.desktop.lwjgl.ui.LWJGLWindowContextFactory;

/** Default implementation of {@link IWindowContextFactory}.
 */
public class DesktopWindowContextFactory extends LWJGLWindowContextFactory {


	/** Default constructor.
	 */
	public DesktopWindowContextFactory() {
		super();
	}

	/** Constructor with a default icon path.
	 *
	 * @param defaultIconPath
	 */
	public DesktopWindowContextFactory(String defaultIconPath) {
		super(defaultIconPath);
	}

}
