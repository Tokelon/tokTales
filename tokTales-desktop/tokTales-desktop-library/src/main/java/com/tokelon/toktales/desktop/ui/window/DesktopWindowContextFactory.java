package com.tokelon.toktales.desktop.ui.window;

import com.tokelon.toktales.desktop.lwjgl.ui.IWindowContextFactory;
import com.tokelon.toktales.desktop.lwjgl.ui.LWJGLWindowContextFactory;

/** Default implementation of {@link IWindowContextFactory}.
 */
public class DesktopWindowContextFactory extends LWJGLWindowContextFactory {


	/** Default constructor.
	 * <p>
	 * {@link #DEFAULT_ICON_PATH} will be used for the default icon path.
	 *
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
