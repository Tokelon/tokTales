package com.tokelon.toktales.desktop.lwjgl;

import com.google.inject.ConfigurationException;
import com.google.inject.ProvisionException;
import com.tokelon.toktales.core.engine.EngineException;
import com.tokelon.toktales.core.engine.IEngineContext;
import com.tokelon.toktales.desktop.input.IDesktopInputDriver;
import com.tokelon.toktales.desktop.input.IDesktopInputService;
import com.tokelon.toktales.desktop.input.dispatch.IDesktopInputProducer;
import com.tokelon.toktales.desktop.lwjgl.input.GLFWInputDriver;
import com.tokelon.toktales.desktop.lwjgl.ui.DefaultGameWindowRenderer;
import com.tokelon.toktales.desktop.lwjgl.ui.LWJGLWindowFactory;
import com.tokelon.toktales.desktop.lwjgl.ui.LWJGLWindowToolkit;
import com.tokelon.toktales.desktop.render.IWindowRenderer;
import com.tokelon.toktales.desktop.ui.window.IWindow;
import com.tokelon.toktales.desktop.ui.window.IWindowBuilder;
import com.tokelon.toktales.desktop.ui.window.IWindowConfigurator;
import com.tokelon.toktales.desktop.ui.window.IWindowContext;
import com.tokelon.toktales.desktop.ui.window.IWindowFactory;
import com.tokelon.toktales.desktop.ui.window.IWindowToolkit;
import com.tokelon.toktales.tools.core.objects.pools.IObjectPool.IObjectPoolFactory;

public class LWJGLWindowContext implements IWindowContext {


	private IWindow window;
	private IWindowRenderer renderer;
	private IDesktopInputDriver inputDriver;

	private IWindowBuilder windowBuilder;
	private IWindowConfigurator windowConfigurator;
	private IDesktopInputDriverFactory inputDriverFactory;
	private IWindowRendererFactory windowRendererFactory;

	private final IWindowFactory windowFactory;
	private final IWindowToolkit windowToolkit;

	public LWJGLWindowContext(
			IWindowFactory windowFactory,
			IWindowToolkit windowToolkit,
			IWindowBuilder windowBuilder,
			IWindowConfigurator windowConfigurator,
			IWindowRendererFactory windowRendererFactory,
			IDesktopInputDriverFactory inputDriverFactory
	) {
		this.windowFactory = windowFactory;
		this.windowToolkit = windowToolkit;
		this.windowBuilder = windowBuilder;
		this.windowConfigurator = windowConfigurator;
		this.inputDriverFactory = inputDriverFactory;
		this.windowRendererFactory = windowRendererFactory;
	}


	@Override
	public void create(IEngineContext engineContext) throws EngineException {
		this.renderer = windowRendererFactory.create(engineContext);
		this.inputDriver = inputDriverFactory.create(engineContext);


		// Create window
		this.window = getWindowBuilder().build(getWindowFactory(), getWindowToolkit());
		getWindow().create();

		// Register input driver
		getInputDriver().register(getWindow());

		// Create renderer and context
		getRenderer().create(getWindow());
		getRenderer().createContext();

		// Configure window
		getWindowConfigurator().configure(getWindow(), getWindowToolkit());
		getWindow().show();
	}

	@Override
	public void destroy() throws EngineException {
		if(getInputDriver() != null) {
			getInputDriver().unregister();
		}

		getRenderer().destroyContext();
		getRenderer().destroy();
		getWindow().destroy();
	}


	@Override
	public IWindowFactory getWindowFactory() {
		return windowFactory;
	}

	@Override
	public IWindowToolkit getWindowToolkit() {
		return windowToolkit;
	}

	@Override
	public IWindow getWindow() {
		return window;
	}

	@Override
	public IWindowRenderer getRenderer() {
		return renderer;
	}

	@Override
	public IDesktopInputDriver getInputDriver() {
		return inputDriver;
	}


	protected IWindowBuilder getWindowBuilder() {
		return windowBuilder;
	}

	protected IWindowConfigurator getWindowConfigurator() {
		return windowConfigurator;
	}


	public static class LWJGLWindowContextBuilder implements IWindowContextBuilder {
		private IWindowFactory windowFactory;
		private IWindowToolkit windowToolkit;
		private IWindowBuilder windowBuilder;
		private IWindowConfigurator windowConfigurator;
		private IWindowRendererFactory windowRendererFactory;
		private IDesktopInputDriverFactory inputDriverFactory;

		public LWJGLWindowContextBuilder() {
			this(
				new LWJGLWindowFactory(),
				new LWJGLWindowToolkit(),
				new LWJGLWindowFactory().createDefaultBuilder(),
				(window, windowToolkit) -> {},
				new LWJGLWindowRendererFactory(),
				new LWJGLInputDriverFactory()
			);
		}

		public LWJGLWindowContextBuilder(
				IWindowFactory windowFactory,
				IWindowToolkit windowToolkit,
				IWindowBuilder windowBuilder,
				IWindowConfigurator windowConfigurator,
				IWindowRendererFactory windowRendererFactory,
				IDesktopInputDriverFactory inputDriverFactory
		) {
			this.windowFactory = windowFactory;
			this.windowToolkit = windowToolkit;
			this.windowBuilder = windowBuilder;
			this.windowConfigurator = windowConfigurator;
			this.windowRendererFactory = windowRendererFactory;
			this.inputDriverFactory = inputDriverFactory;
		}


		@Override
		public IWindowContext build() {
			return new LWJGLWindowContext(
					windowFactory,
					windowToolkit,
					windowBuilder,
					windowConfigurator,
					windowRendererFactory,
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
		public IWindowContextBuilder withRenderer(IWindowRenderer windowRenderer) {
			this.windowRendererFactory = (engineContext) -> windowRenderer;
			return this;
		}

		@Override
		public IWindowContextBuilder withRenderer(IWindowRendererFactory windowRendererFactory) {
			this.windowRendererFactory = windowRendererFactory;
			return this;
		}

		@Override
		public IWindowContextBuilder withInputDriver(IDesktopInputDriver inputDriver) {
			this.inputDriverFactory = (engineContext) -> inputDriver;
			return this;
		}

		@Override
		public IWindowContextBuilder withInputDriver(IDesktopInputDriverFactory inputDriverFactory) {
			this.inputDriverFactory = inputDriverFactory;
			return this;
		}

		@Override
		public IWindowContextBuilder withInputDriverNone() {
			this.inputDriverFactory = (engineContext) -> null;
			return this;
		}


		public static class LWJGLInputDriverFactory implements IDesktopInputDriverFactory {

			@Override
			public IDesktopInputDriver create(IEngineContext engineContext) {
				IDesktopInputDriver inputDriver = null;
				try {
					IDesktopInputService desktopInputService = engineContext.getInjector().getInstance(IDesktopInputService.class);

					IDesktopInputProducer mainInputProducer = desktopInputService.getMainInputDispatch().getInputProducer();
					inputDriver = new GLFWInputDriver(mainInputProducer, engineContext.getInjector().getInstance(IObjectPoolFactory.class));
				}
				catch (ConfigurationException | ProvisionException e) {
					// TODO: What to do here? Pass the exception? Log and continue?
					throw e;
				}

				return inputDriver;
			}
		}

		public static class LWJGLWindowRendererFactory implements IWindowRendererFactory {

			@Override
			public IWindowRenderer create(IEngineContext engineContext) {
				return new DefaultGameWindowRenderer(engineContext.getEngine().getEngineDriver(), engineContext.getEngine().getRenderService().getSurfaceManager());
			}
		}
	}

}
