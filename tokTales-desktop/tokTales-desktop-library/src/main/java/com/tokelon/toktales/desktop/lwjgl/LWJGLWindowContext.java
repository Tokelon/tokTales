package com.tokelon.toktales.desktop.lwjgl;

import com.tokelon.toktales.core.engine.IEngineContext;
import com.tokelon.toktales.desktop.input.IDesktopInputDriver;
import com.tokelon.toktales.desktop.render.IWindowRenderer;
import com.tokelon.toktales.desktop.ui.window.IWindow;
import com.tokelon.toktales.desktop.ui.window.IWindowBuilder;
import com.tokelon.toktales.desktop.ui.window.IWindowConfigurator;
import com.tokelon.toktales.desktop.ui.window.IWindowContext;
import com.tokelon.toktales.desktop.ui.window.IWindowContextBuilder.IWindowContextInputDriverFactory;
import com.tokelon.toktales.desktop.ui.window.IWindowContextBuilder.IWindowContextRendererFactory;
import com.tokelon.toktales.desktop.ui.window.IWindowFactory;
import com.tokelon.toktales.desktop.ui.window.IWindowToolkit;

public class LWJGLWindowContext implements IWindowContext {


	private IWindow window;
	private IWindowRenderer renderer;
	private IDesktopInputDriver inputDriver;

	private IWindowBuilder windowBuilder;
	private IWindowConfigurator windowConfigurator;
	private IWindowContextInputDriverFactory inputDriverFactory;
	private IWindowContextRendererFactory rendererFactory;

	private final IWindowFactory windowFactory;
	private final IWindowToolkit windowToolkit;

	public LWJGLWindowContext(
			IWindowFactory windowFactory,
			IWindowToolkit windowToolkit,
			IWindowBuilder windowBuilder,
			IWindowConfigurator windowConfigurator,
			IWindowContextRendererFactory rendererFactory,
			IWindowContextInputDriverFactory inputDriverFactory
	) {
		this.windowFactory = windowFactory;
		this.windowToolkit = windowToolkit;
		this.windowBuilder = windowBuilder;
		this.windowConfigurator = windowConfigurator;
		this.rendererFactory = rendererFactory;
		this.inputDriverFactory = inputDriverFactory;
	}


	@Override
	public void create(IEngineContext engineContext) {
		this.renderer = rendererFactory.create(engineContext);
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
	public void destroy() {
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

}
