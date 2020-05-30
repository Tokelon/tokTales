package com.tokelon.toktales.desktop.engine;

import com.tokelon.toktales.core.engine.IEngineLooper;
import com.tokelon.toktales.core.engine.log.ILoggerFactory;
import com.tokelon.toktales.desktop.ui.window.IWindowBuilder;
import com.tokelon.toktales.desktop.ui.window.IWindowConfigurator;
import com.tokelon.toktales.desktop.ui.window.IWindowContext;
import com.tokelon.toktales.desktop.ui.window.IWindowContextBuilder;
import com.tokelon.toktales.desktop.ui.window.IWindowHandler;
import com.tokelon.toktales.tools.core.sub.inject.config.IHierarchicalInjectConfig;

/** Creates engine launcher instances and engine launcher builders for the desktop platform.
 */
public interface IDesktopLauncherFactory {


	/** Creates an engine launcher with the default properties of this factory.
	 *
	 * @return A new default engine launcher.
	 */
	public IWindowEngineLauncher createDefaultLauncher();

	/** Creates an engine launcher builder with the default properties of this factory.
	 *
	 * @return A new default engine launcher builder.
	 */
	public IWindowEngineLauncherBuilder createDefaultLauncherBuilder();


	/** Creates a window engine launcher.
	 *
	 * @return A new window engine launcher.
	 */
	public IWindowEngineLauncher createWindowLauncher();

	/** Creates a window engine launcher builder.
	 *
	 * @return A new window engine launcher builder.
	 */
	public IWindowEngineLauncherBuilder createWindowLauncherBuilder();


	/** Creates a desktop engine launcher.
	 *
	 * @return A new desktop engine launcher.
	 */
	public IDesktopEngineLauncher createDesktopLauncher();

	/** Creates a desktop engine launcher builder.
	 *
	 * @return A new desktop engine launcher builder.
	 */
	public IDesktopEngineLauncherBuilder createDesktopLauncherBuilder();



	/** Builds a desktop engine launcher with given or default properties.
	 */
	public interface IDesktopEngineLauncherBuilder {


		/** Creates a desktop engine launcher.
		 *
		 * @return A new launcher.
		 */
		public IDesktopEngineLauncher build();


		/**
		 * @param injectConfig
		 * @return This builder.
		 */
		public IDesktopEngineLauncherBuilder withInjectConfig(IHierarchicalInjectConfig injectConfig);

		/**
		 * @param defaultLooper
		 * @return This builder.
		 */
		public IDesktopEngineLauncherBuilder withEngineLooper(IEngineLooper defaultLooper);

		/**
		 * @param loggerFactory
		 * @return This builder.
		 */
		public IDesktopEngineLauncherBuilder withLoggerFactory(ILoggerFactory loggerFactory);
	}


	/** Builds a window engine launcher with given or default properties.
	 */
	public interface IWindowEngineLauncherBuilder {


		/** Creates a window engine launcher.
		 *
		 * @return A new launcher.
		 */
		public IWindowEngineLauncher build();


		/**
		 * @param windowHandler
		 * @return This builder.
		 */
		public IWindowEngineLauncherBuilder withWindow(IWindowHandler windowHandler);

		/**
		 * @param windowContext
		 * @return This builder.
		 */
		public IWindowEngineLauncherBuilder withWindow(IWindowContext windowContext);

		/**
		 * @param windowContextBuilder
		 * @return This builder.
		 */
		public IWindowEngineLauncherBuilder withWindow(IWindowContextBuilder windowContextBuilder);


		/**
		 * @param windowBuilder
		 * @return This builder.
		 */
		public IWindowEngineLauncherBuilder withWindow(IWindowBuilder windowBuilder);

		/**
		 * @param windowConfigurator
		 * @return This builder.
		 */
		public IWindowEngineLauncherBuilder withWindow(IWindowConfigurator windowConfigurator);

		/**
		 * @param windowBuilder
		 * @param windowConfigurator
		 * @return This builder.
		 */
		public IWindowEngineLauncherBuilder withWindow(IWindowBuilder windowBuilder, IWindowConfigurator windowConfigurator);


		/**
		 * @param injectConfig
		 * @return This builder.
		 */
		public IWindowEngineLauncherBuilder withInjectConfig(IHierarchicalInjectConfig injectConfig);

		/**
		 * @param defaultLooper
		 * @return This builder.
		 */
		public IWindowEngineLauncherBuilder withEngineLooper(IEngineLooper defaultLooper);

		/**
		 * @param loggerFactory
		 * @return This builder.
		 */
		public IWindowEngineLauncherBuilder withLoggerFactory(ILoggerFactory loggerFactory);
	}

}
