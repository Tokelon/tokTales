package com.tokelon.toktales.desktop.ui.window;

import com.tokelon.toktales.core.engine.IEngineContext;
import com.tokelon.toktales.desktop.input.IDesktopInputDriver;
import com.tokelon.toktales.desktop.render.IWindowRenderer;

/** Builds a window context with given or default properties.
 */
public interface IWindowContextBuilder {


	/** Creates a window context.
	 *
	 * @return A new window context.
	 */
	public IWindowContext build();

	/**
	 * @param windowFactory
	 * @return This builder.
	 */
	public IWindowContextBuilder withWindowFactory(IWindowFactory windowFactory);

	/**
	 * @param windowToolkit
	 * @return This builder.
	 */
	public IWindowContextBuilder withWindowToolkit(IWindowToolkit windowToolkit);

	/**
	 * @param window
	 * @return This builder.
	 */
	public IWindowContextBuilder withWindow(IWindow window);

	/**
	 * @param windowBuilder
	 * @return This builder.
	 */
	public IWindowContextBuilder withWindow(IWindowBuilder windowBuilder);

	/**
	 * @param windowConfigurator
	 * @return This builder.
	 */
	public IWindowContextBuilder withWindowConfigurator(IWindowConfigurator windowConfigurator);

	/**
	 * @param iconSetter
	 * @return This builder.
	 */
	public IWindowContextBuilder withWindowIconSetter(IWindowIconSetter iconSetter);

	/**
	 * @param iconSetterFactory
	 * @return This builder.
	 */
	public IWindowContextBuilder withWindowIconSetter(IWindowContextIconSetterFactory iconSetterFactory);

	/**
	 * @param windowRenderer
	 * @return This builder.
	 */
	public IWindowContextBuilder withRenderer(IWindowRenderer windowRenderer);

	/**
	 * @param rendererFactory
	 * @return This builder.
	 */
	public IWindowContextBuilder withRenderer(IWindowContextRendererFactory rendererFactory);

	/**
	 * @param inputDriver
	 * @return This builder.
	 */
	public IWindowContextBuilder withInputDriver(IDesktopInputDriver inputDriver);

	/**
	 * @param inputDriverFactory
	 * @return This builder.
	 */
	public IWindowContextBuilder withInputDriver(IWindowContextInputDriverFactory inputDriverFactory);

	/** Disables creation of the default input driver.
	 *
	 * @return This builder.
	 */
	public IWindowContextBuilder withInputDriverNone();


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