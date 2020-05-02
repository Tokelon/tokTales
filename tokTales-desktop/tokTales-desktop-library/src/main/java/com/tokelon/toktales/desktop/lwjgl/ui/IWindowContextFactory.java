package com.tokelon.toktales.desktop.lwjgl.ui;

import com.tokelon.toktales.core.engine.IEngineContext;
import com.tokelon.toktales.desktop.input.IDesktopInputDriver;
import com.tokelon.toktales.desktop.render.IWindowRenderer;
import com.tokelon.toktales.desktop.ui.window.IWindowContext;
import com.tokelon.toktales.desktop.ui.window.IWindowContextBuilder;
import com.tokelon.toktales.desktop.ui.window.IWindowIconSetter;

/** Creates window context instances and window context builders, as well as factories for window context properties.
 */
public interface IWindowContextFactory {


	/** Creates a window context with the default properties of this factory.
	 *
	 * @return A new window context.
	 */
	public IWindowContext createDefault();

	/** Creates a window context builder with the default properties of this factory.
	 *
	 * @return A new window context builder.
	 */
	public IWindowContextBuilder createDefaultBuilder();


	/** Creates a default icon setter factory.
	 *
	 * @return A new window context icon setter factory.
	 */
	public IWindowContextIconSetterFactory createDefaultIconSetterFactory();

	/** Creates an icon setter factory with the given icon path.
	 *
	 * @param iconPath
	 * @return A new window context icon setter factory.
	 */
	public IWindowContextIconSetterFactory createIconSetterFactory(String iconPath);

	/** Creates a default renderer factory.
	 *
	 * @return A new window context renderer factory
	 */
	public IWindowContextRendererFactory createDefaultRendererFactory();

	/** Creates a default input driver factory.
	 *
	 * @return a new window context input driver factory.
	 */
	public IWindowContextInputDriverFactory createDefaultInputDriverFactory();


	/** Creates a window icon setter for a window context.
	 */
	public interface IWindowContextIconSetterFactory {


		/** Creates a window icon setter using the given engine context.
		 *
		 * @param engineContext
		 * @return A new window icon setter.
		 */
		public IWindowIconSetter create(IEngineContext engineContext);
	}

	/** Creates a window renderer for a window context.
	 */
	public interface IWindowContextRendererFactory {


		/** Creates a window renderer using the given engine context.
		 *
		 * @param engineContext
		 * @return A new window renderer.
		 */
		public IWindowRenderer create(IEngineContext engineContext);
	}

	/** Creates an input driver for a window context.
	 */
	public interface IWindowContextInputDriverFactory {


		/** Creates an input driver using the given engine context.
		 *
		 * @param engineContext
		 * @return A new input driver.
		 */
		public IDesktopInputDriver create(IEngineContext engineContext);
	}

}
