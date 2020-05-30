package com.tokelon.toktales.desktop.engine;

import com.tokelon.toktales.desktop.ui.window.IWindowContext;

/** Version of {@link IDesktopEngineLauncher} that supports windows.
 */
public interface IWindowEngineLauncher extends IDesktopEngineLauncher {


	/**
	 * @return The window context for this launcher.
	 */
	public IWindowContext getWindowContext();

}
