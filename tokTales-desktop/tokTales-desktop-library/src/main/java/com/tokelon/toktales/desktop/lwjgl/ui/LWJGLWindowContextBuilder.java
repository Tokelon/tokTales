package com.tokelon.toktales.desktop.lwjgl.ui;

import com.tokelon.toktales.desktop.input.IDesktopInputDriver;
import com.tokelon.toktales.desktop.lwjgl.ui.IWindowContextFactory.IWindowContextIconSetterFactory;
import com.tokelon.toktales.desktop.lwjgl.ui.IWindowContextFactory.IWindowContextInputDriverFactory;
import com.tokelon.toktales.desktop.lwjgl.ui.IWindowContextFactory.IWindowContextRendererFactory;
import com.tokelon.toktales.desktop.render.IWindowRenderer;
import com.tokelon.toktales.desktop.ui.window.IWindow;
import com.tokelon.toktales.desktop.ui.window.IWindowBuilder;
import com.tokelon.toktales.desktop.ui.window.IWindowConfigurator;
import com.tokelon.toktales.desktop.ui.window.IWindowContext;
import com.tokelon.toktales.desktop.ui.window.IWindowContextBuilder;
import com.tokelon.toktales.desktop.ui.window.IWindowFactory;
import com.tokelon.toktales.desktop.ui.window.IWindowIconSetter;
import com.tokelon.toktales.desktop.ui.window.IWindowToolkit;

/** LWJGL implementation of {@link IWindowContextBuilder}.
 */
public class LWJGLWindowContextBuilder implements IWindowContextBuilder {


	private IWindowFactory windowFactory;
	private IWindowToolkit windowToolkit;
	private IWindowBuilder windowBuilder;
	private IWindowConfigurator windowConfigurator;
	private IWindowContextIconSetterFactory iconSetterFactory;
	private IWindowContextRendererFactory rendererFactory;
	private IWindowContextInputDriverFactory inputDriverFactory;

	/** Constructor with window context properties.
	 *
	 * @param windowFactory
	 * @param windowToolkit
	 * @param windowBuilder
	 * @param windowConfigurator
	 * @param iconSetterFactory
	 * @param rendererFactory
	 * @param inputDriverFactory
	 */
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

}