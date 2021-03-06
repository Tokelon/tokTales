package com.tokelon.toktales.desktop.engine;

import com.tokelon.toktales.core.engine.IEngineLooper;
import com.tokelon.toktales.core.engine.NoopEngineLooper;
import com.tokelon.toktales.core.engine.log.ILoggerFactory;
import com.tokelon.toktales.core.engine.log.LoggingManager;
import com.tokelon.toktales.desktop.engine.inject.MasterDesktopInjectConfig;
import com.tokelon.toktales.desktop.lwjgl.LWJGLInputProcessor;
import com.tokelon.toktales.desktop.lwjgl.ui.LWJGLWindowContextFactory;
import com.tokelon.toktales.desktop.ui.window.IWindowBuilder;
import com.tokelon.toktales.desktop.ui.window.IWindowConfigurator;
import com.tokelon.toktales.desktop.ui.window.IWindowContext;
import com.tokelon.toktales.desktop.ui.window.IWindowContextBuilder;
import com.tokelon.toktales.desktop.ui.window.IWindowHandler;
import com.tokelon.toktales.desktop.ui.window.WindowHandler;
import com.tokelon.toktales.tools.core.sub.inject.config.IHierarchicalInjectConfig;

/** Implementation of {@link IDesktopLauncherFactory}.
 */
public class DesktopLauncherFactory implements IDesktopLauncherFactory {


	@Override
	public IWindowEngineLauncher createDefaultLauncher() {
		return createDefaultLauncherBuilder().build();
	}

	@Override
	public IWindowEngineLauncherBuilder createDefaultLauncherBuilder() {
		return new WindowEngineLauncherBuilder();
	}

	@Override
	public IWindowEngineLauncher createWindowLauncher() {
		return createWindowLauncherBuilder().build();
	}

	@Override
	public IWindowEngineLauncherBuilder createWindowLauncherBuilder() {
		return new WindowEngineLauncherBuilder();
	}

	@Override
	public IDesktopEngineLauncher createDesktopLauncher() {
		return createDesktopLauncherBuilder().build();
	}

	@Override
	public IDesktopEngineLauncherBuilder createDesktopLauncherBuilder() {
		return new DesktopEngineLauncherBuilder();
	}


	/** Implementation of {@link IDesktopEngineLauncherBuilder}.
	 */
	public static class DesktopEngineLauncherBuilder implements IDesktopEngineLauncherBuilder {
		private IHierarchicalInjectConfig injectConfig;
		private IEngineLooper defaultLooper;
		private ILoggerFactory loggerFactory;

		/** Default constructor with default properties.
		 */
		public DesktopEngineLauncherBuilder() {
			this(
				new MasterDesktopInjectConfig(),
				new NoopEngineLooper(),
				LoggingManager.getLoggerFactory()
			);
		}

		/** Constructor with desktop engine launcher properties.
		 *
		 * @param injectConfig
		 * @param defaultLooper
		 * @param loggerFactory
		 */
		public DesktopEngineLauncherBuilder(
				IHierarchicalInjectConfig injectConfig,
				IEngineLooper defaultLooper,
				ILoggerFactory loggerFactory
		) {
			this.injectConfig = injectConfig;
			this.defaultLooper = defaultLooper;
			this.loggerFactory = loggerFactory;
		}


		@Override
		public IDesktopEngineLauncher build() {
			return new DesktopEngineLauncher(injectConfig, defaultLooper, loggerFactory);
		}

		@Override
		public IDesktopEngineLauncherBuilder withInjectConfig(IHierarchicalInjectConfig injectConfig) {
			this.injectConfig = injectConfig;
			return this;
		}

		@Override
		public IDesktopEngineLauncherBuilder withEngineLooper(IEngineLooper defaultLooper) {
			this.defaultLooper = defaultLooper;
			return this;
		}

		@Override
		public IDesktopEngineLauncherBuilder withLoggerFactory(ILoggerFactory loggerFactory) {
			this.loggerFactory = loggerFactory;
			return this;
		}
	}


	/** Implementation of {@link IWindowEngineLauncherBuilder}.
	 */
	public static class WindowEngineLauncherBuilder implements IWindowEngineLauncherBuilder {
		private IHierarchicalInjectConfig injectConfig;
		private IEngineLooper defaultLooper;
		private ILoggerFactory loggerFactory;
		private IWindowHandler windowHandler;

		/** Default constructor with default properties.
		 */
		public WindowEngineLauncherBuilder() {
			this(
				new MasterDesktopInjectConfig(),
				null,
				LoggingManager.getLoggerFactory(),
				new WindowHandler(new LWJGLWindowContextFactory().createDefaultBuilder().build())
			);
		}

		/** Constructor with window engine launcher properties.
		 *
		 * @param injectConfig
		 * @param defaultLooper
		 * @param loggerFactory
		 * @param windowHandler
		 */
		public WindowEngineLauncherBuilder(
				IHierarchicalInjectConfig injectConfig,
				IEngineLooper defaultLooper,
				ILoggerFactory loggerFactory,
				IWindowHandler windowHandler
		) {
			this.injectConfig = injectConfig;
			this.defaultLooper = defaultLooper;
			this.loggerFactory = loggerFactory;
			this.windowHandler = windowHandler;
		}


		@Override
		public IWindowEngineLauncher build() {
			// Create window handler lazily for some reason?
			IEngineLooper looper = defaultLooper == null ? new WindowEngineLooper(windowHandler, new LWJGLInputProcessor()) : defaultLooper;

			return new WindowEngineLauncher(injectConfig, windowHandler, looper, loggerFactory);
		}

		@Override
		public IWindowEngineLauncherBuilder withWindow(IWindowHandler windowHandler) {
			this.windowHandler = windowHandler;
			return this;
		}

		@Override
		public IWindowEngineLauncherBuilder withWindow(IWindowContext windowContext) {
			return withWindow(new WindowHandler(windowContext));
		}

		@Override
		public IWindowEngineLauncherBuilder withWindow(IWindowContextBuilder windowContextBuilder) {
			return withWindow(new WindowHandler(windowContextBuilder.build()));
		}


		@Override
		public IWindowEngineLauncherBuilder withWindow(IWindowBuilder windowBuilder) {
			IWindowContextBuilder windowContextBuilder = new LWJGLWindowContextFactory().createDefaultBuilder();
			windowContextBuilder.withWindow(windowBuilder);

			return withWindow(new WindowHandler(windowContextBuilder.build()));
		}

		@Override
		public IWindowEngineLauncherBuilder withWindow(IWindowConfigurator windowConfigurator) {
			IWindowContextBuilder windowContextBuilder = new LWJGLWindowContextFactory().createDefaultBuilder();
			windowContextBuilder.withWindowConfigurator(windowConfigurator);

			return withWindow(new WindowHandler(windowContextBuilder.build()));
		}

		@Override
		public IWindowEngineLauncherBuilder withWindow(IWindowBuilder windowBuilder, IWindowConfigurator windowConfigurator) {
			IWindowContextBuilder windowContextBuilder = new LWJGLWindowContextFactory().createDefaultBuilder();
			windowContextBuilder.withWindow(windowBuilder);
			windowContextBuilder.withWindowConfigurator(windowConfigurator);

			return withWindow(new WindowHandler(windowContextBuilder.build()));
		}

		@Override
		public IWindowEngineLauncherBuilder withInjectConfig(IHierarchicalInjectConfig injectConfig) {
			this.injectConfig = injectConfig;
			return this;
		}

		@Override
		public IWindowEngineLauncherBuilder withEngineLooper(IEngineLooper defaultLooper) {
			this.defaultLooper = defaultLooper;
			return this;
		}

		@Override
		public IWindowEngineLauncherBuilder withLoggerFactory(ILoggerFactory loggerFactory) {
			this.loggerFactory = loggerFactory;
			return this;
		}
	}

}
