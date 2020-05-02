package com.tokelon.toktales.desktop.ui.window;

import com.tokelon.toktales.core.engine.IEngineContext;
import com.tokelon.toktales.desktop.input.IDesktopInputDriver;
import com.tokelon.toktales.desktop.render.IWindowRenderer;

/** Contains all properties needed to integrate a window into the engine.
 * <p>
 * A context must be initialized with {@link #create(IEngineContext)} before any of it's properties are accessed.
 */
public interface IWindowContext {


	/** Initializes this window context using the given engine context.
	 *
	 * @param engineContext
	 */
	public void create(IEngineContext engineContext);

	/** Destroys this window context.
	 */
	public void destroy();


	/**
	 * @return The window factory of this context.
	 */
	public IWindowFactory getWindowFactory();

	/**
	 * @return The window toolkit of this context.
	 */
	public IWindowToolkit getWindowToolkit();


	/**
	 * @return The window of this context.
	 */
	public IWindow getWindow();

	/**
	 * @return The renderer of this context.
	 */
	public IWindowRenderer getRenderer();

	/**
	 * @return The input driver of this context.
	 */
	public IDesktopInputDriver getInputDriver();


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
