package com.tokelon.toktales.desktop.ui.window;

/** Creates windows and window builders.
 */
public interface IWindowFactory {


	/** Creates a window with the default properties of this factory.
	 * <p>
	 * The default window hints will be used.
	 *
	 * @return A new window.
	 * @see #withDefaultHints()
	 */
	public IWindow createDefault();

	/** Creates a window builder with the default properties of this factory.
	 * <p>
	 * The default window hints will be used.
	 *
	 * @return A window builder.
	 * @see #withDefaultHints()
	 */
	public IWindowBuilder createDefaultBuilder();


	/** Creates a window with the given size and title.
	 *
	 * @param width
	 * @param height
	 * @param title
	 * @return A new window.
	 */
	public IWindow create(int width, int height, String title);

	/** Creates a window with the given size, title and monitor.
	 *
	 * @param width
	 * @param height
	 * @param title
	 * @param monitor
	 * @return A new window.
	 */
	public IWindow create(int width, int height, String title, long monitor);

	/** Creates a window with the given size, title, monitor and share.
	 *
	 * @param width
	 * @param height
	 * @param title
	 * @param monitor
	 * @param share
	 * @return A new window.
	 */
	public IWindow create(int width, int height, String title, long monitor, long share);

	/** Creates a window builder with the given size and title.
	 *
	 * @param width
	 * @param height
	 * @param title
	 * @return A window builder.
	 */
	public IWindowBuilder createBuilder(int width, int height, String title);

	/** Creates a window builder with the given size, title and monitor.
	 *
	 * @param width
	 * @param height
	 * @param title
	 * @param monitor
	 * @return A window builder.
	 */
	public IWindowBuilder createBuilder(int width, int height, String title, long monitor);

	/** Creates a window builder with the given size, title, monitor and share.
	 *
	 * @param width
	 * @param height
	 * @param title
	 * @param monitor
	 * @param share
	 * @return A window builder.
	 */
	public IWindowBuilder createBuilder(int width, int height, String title, long monitor, long share);


	/** Creates a window in fullscreen mode with the given title.
	 *
	 * @param title
	 * @return A new window.
	 */
	public IWindow createFullscreen(String title);

	/** Creates a window builder for fullscreen mode with the given title.
	 *
	 * @param title
	 * @return A window builder.
	 */
	public IWindowBuilder createFullscreenBuilder(String title);

	/** Creates a window in borderless fullscreen mode with the given title.
	 *
	 * @param title
	 * @return A new window.
	 */
	public IWindow createBorderless(String title);

	/** Creates a window builder for borderless fullscreen mode with the given title.
	 *
	 * @param title
	 * @return A window builder.
	 */
	public IWindowBuilder createBorderlessBuilder(String title);


	/** Returns a configurator that does nothing.
	 *
	 * @return A no-op configurator.
	 */
	public IWindowConfigurator getEmptyConfigurator();

	/** Returns a configurator that will set the default hints for a window.
	 *
	 * @return A default hints configurator.
	 */
	public IWindowConfigurator getDefaultHintsConfigurator();

	/** Returns a configurator that will set a window to windowed mode.
	 *
	 * @return A windowed mode configurator.
	 */
	public IWindowConfigurator getWindowedConfigurator();

	/** Returns a configurator that will set a window to fullscreen mode.
	 *
	 * @return A fullscreen mode configurator.
	 */
	public IWindowConfigurator getFullscreenConfigurator();

	/** Returns a configurator that will set a window to borderless fullscreen mode.
	 *
	 * @return A borderless fullscreen mode configurator.
	 */
	public IWindowConfigurator getBorderlessConfigurator();

}
