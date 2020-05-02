package com.tokelon.toktales.desktop.lwjgl;

import com.google.inject.ConfigurationException;
import com.google.inject.ProvisionException;
import com.tokelon.toktales.core.content.manage.bitmap.IBitmapAssetManager;
import com.tokelon.toktales.core.engine.IEngineContext;
import com.tokelon.toktales.desktop.input.IDesktopInputDriver;
import com.tokelon.toktales.desktop.lwjgl.input.GLFWInputDriver;
import com.tokelon.toktales.desktop.lwjgl.ui.DefaultGameWindowRenderer;
import com.tokelon.toktales.desktop.lwjgl.ui.LWJGLWindowFactory;
import com.tokelon.toktales.desktop.lwjgl.ui.LWJGLWindowToolkit;
import com.tokelon.toktales.desktop.render.IWindowRenderer;
import com.tokelon.toktales.desktop.ui.window.FileWindowIconSetter;
import com.tokelon.toktales.desktop.ui.window.IWindow;
import com.tokelon.toktales.desktop.ui.window.IWindowBuilder;
import com.tokelon.toktales.desktop.ui.window.IWindowConfigurator;
import com.tokelon.toktales.desktop.ui.window.IWindowContext;
import com.tokelon.toktales.desktop.ui.window.IWindowContextBuilder;
import com.tokelon.toktales.desktop.ui.window.IWindowFactory;
import com.tokelon.toktales.desktop.ui.window.IWindowIconSetter;
import com.tokelon.toktales.desktop.ui.window.IWindowToolkit;
import com.tokelon.toktales.tools.core.objects.pools.IObjectPool.IObjectPoolFactory;

public class LWJGLWindowContextBuilder implements IWindowContextBuilder {


	private IWindowFactory windowFactory;
	private IWindowToolkit windowToolkit;
	private IWindowBuilder windowBuilder;
	private IWindowConfigurator windowConfigurator;
	private IWindowContextIconSetterFactory iconSetterFactory;
	private IWindowContextRendererFactory rendererFactory;
	private IWindowContextInputDriverFactory inputDriverFactory;

	public LWJGLWindowContextBuilder() {
		this(
			new LWJGLWindowFactory(),
			new LWJGLWindowToolkit(),
			new LWJGLWindowFactory().createDefaultBuilder(),
			(window, windowToolkit) -> {},
			(engineContext) -> (window, windowToolkit) -> {},
			new LWJGLWindowContextRendererFactory(),
			new LWJGLWindowContextInputDriverFactory()
		);
	}

	public LWJGLWindowContextBuilder(
			IWindowFactory windowFactory,
			IWindowToolkit windowToolkit,
			IWindowBuilder windowBuilder,
			IWindowConfigurator windowConfigurator,
			IWindowContextIconSetterFactory iconSetterFactory,
			IWindowContextRendererFactory rendererFactory,
			IWindowContextInputDriverFactory inputDriverFactory
	) {
		this.windowFactory = windowFactory;
		this.windowToolkit = windowToolkit;
		this.windowBuilder = windowBuilder;
		this.windowConfigurator = windowConfigurator;
		this.iconSetterFactory = iconSetterFactory;
		this.rendererFactory = rendererFactory;
		this.inputDriverFactory = inputDriverFactory;
	}


	@Override
	public IWindowContext build() {
		return new LWJGLWindowContext(
				windowFactory,
				windowToolkit,
				windowBuilder,
				windowConfigurator,
				iconSetterFactory,
				rendererFactory,
				inputDriverFactory
		);
	}

	@Override
	public IWindowContextBuilder withWindowFactory(IWindowFactory windowFactory) {
		this.windowFactory = windowFactory;
		return this;
	}

	@Override
	public IWindowContextBuilder withWindowToolkit(IWindowToolkit windowToolkit) {
		this.windowToolkit = windowToolkit;
		return this;
	}

	@Override
	public IWindowContextBuilder withWindow(IWindow window) {
		this.windowBuilder = (windowFactory, windowToolkit) -> window;
		return this;
	}

	@Override
	public IWindowContextBuilder withWindow(IWindowBuilder windowBuilder) {
		this.windowBuilder = windowBuilder;
		return this;
	}

	@Override
	public IWindowContextBuilder withWindowConfigurator(IWindowConfigurator windowConfigurator) {
		this.windowConfigurator = windowConfigurator;
		return this;
	}

	@Override
	public IWindowContextBuilder withWindowIconSetter(IWindowIconSetter iconSetter) {
		this.iconSetterFactory = (engineContext) -> iconSetter;
		return this;
	}

	@Override
	public IWindowContextBuilder withWindowIconSetter(IWindowContextIconSetterFactory iconSetterFactory) {
		this.iconSetterFactory = iconSetterFactory;
		return this;
	}

	@Override
	public IWindowContextBuilder withRenderer(IWindowRenderer windowRenderer) {
		this.rendererFactory = (engineContext) -> windowRenderer;
		return this;
	}

	@Override
	public IWindowContextBuilder withRenderer(IWindowContextRendererFactory rendererFactory) {
		this.rendererFactory = rendererFactory;
		return this;
	}

	@Override
	public IWindowContextBuilder withInputDriver(IDesktopInputDriver inputDriver) {
		this.inputDriverFactory = (engineContext) -> inputDriver;
		return this;
	}

	@Override
	public IWindowContextBuilder withInputDriver(IWindowContextInputDriverFactory inputDriverFactory) {
		this.inputDriverFactory = inputDriverFactory;
		return this;
	}

	@Override
	public IWindowContextBuilder withInputDriverNone() {
		this.inputDriverFactory = (engineContext) -> null;
		return this;
	}


	public static class LWJGLWindowContextIconSetterFactory implements IWindowContextIconSetterFactory {
		private final String iconPath;

		public LWJGLWindowContextIconSetterFactory(String iconPath) {
			this.iconPath = iconPath;
		}

		@Override
		public IWindowIconSetter create(IEngineContext engineContext) {
			try {
				// We assume the instance will be resolved, otherwise we cannot use this icon setter
				IBitmapAssetManager bitmapAssetManager = engineContext.getInjector().getInstance(IBitmapAssetManager.class);

				return new FileWindowIconSetter(bitmapAssetManager, iconPath);
			}
			catch(ConfigurationException | ProvisionException e) {
				// TODO: Log an informative error message. Then re-throw exception?
				throw e;
			}
		}
	}

	public static class LWJGLWindowContextInputDriverFactory implements IWindowContextInputDriverFactory {

		@Override
		public IDesktopInputDriver create(IEngineContext engineContext) {
			try {
				// We assume the instance will be resolved, otherwise we cannot use this input driver
				ILWJGLInputService inputService = engineContext.getInjector().getInstance(ILWJGLInputService.class);

				return new GLFWInputDriver(
						inputService.getMainInputDispatch().getGLFWInputConsumer(),
						inputService.getMainInputDispatch().getInputProducer(),
						engineContext.getInjector().getInstance(IObjectPoolFactory.class)
				);
			}
			catch (ConfigurationException | ProvisionException e) {
				// TODO: Log an informative error message. Then re-throw exception?
				throw e;
			}
		}
	}

	public static class LWJGLWindowContextRendererFactory implements IWindowContextRendererFactory {

		@Override
		public IWindowRenderer create(IEngineContext engineContext) {
			try {
				// We assume the instance will be resolved, otherwise we cannot use this renderer
				ILWJGLInputService inputService = engineContext.getInjector().getInstance(ILWJGLInputService.class);

				return new DefaultGameWindowRenderer(
						engineContext.getEngine().getRenderService().getSurfaceManager(),
						inputService.getMainInputDispatch().getGLFWInputConsumer(),
						engineContext.getEngine().getEngineDriver());
			}
			catch(ConfigurationException | ProvisionException e) {
				// TODO: Log an informative error message. Then re-throw exception?
				throw e;
			}
		}
	}

}