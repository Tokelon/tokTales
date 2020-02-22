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


	private IWindowBuilder effectiveWindowBuilder;
	private IWindowConfigurator effectiveWindowConfigurator;
	private IWindow effectiveWindow;
	private IDesktopInputDriver effectiveInputDriver;
	private IWindowRenderer effectiveRenderer;

	private boolean useDefaultInputDriver = true;

	private IWindowBuilder setWindowBuilder;
	private IWindowConfigurator setWindowConfigurator;
	private IDesktopInputDriver setInputDriver;
	private IWindowRenderer setRenderer;
	
	private final IWindowFactory windowFactory;
	private final IWindowToolkit windowToolkit;
	
	public LWJGLWindowContext() {
		this(
			new LWJGLWindowFactory(),
			new LWJGLWindowToolkit()
		);
	}
	
	public LWJGLWindowContext(IWindowFactory windowFactory, IWindowToolkit windowToolkit) {
		this.windowFactory = windowFactory;
		this.windowToolkit = windowToolkit;
	}
	
	
	@Override
	public void create(IEngineContext engineContext) throws EngineException {
		effectiveWindowBuilder = setWindowBuilder == null ? createDefaultWindowBuilder() : setWindowBuilder;
		effectiveWindowConfigurator = setWindowConfigurator == null ? createDefaultWindowConfigurator() : setWindowConfigurator;
		effectiveRenderer = setRenderer == null ? createDefaultRenderer(engineContext) : setRenderer;
		effectiveInputDriver = useDefaultInputDriver ? createDefaultInputDriver(engineContext) : setInputDriver;

		// Create window
		effectiveWindow = getEffectiveWindowBuilder().build(getWindowFactory(), getWindowToolkit());
		getEffectiveWindow().create();

		// Register input driver
		getEffectiveInputDriver().register(getEffectiveWindow());

		// Create renderer and context
		getEffectiveRenderer().create(getEffectiveWindow());
		getEffectiveRenderer().createContext();

		// Configure window
		getEffectiveWindowConfigurator().configure(getEffectiveWindow());
		getEffectiveWindow().show();
	}

	@Override
	public void destroy() throws EngineException {
		if(getEffectiveInputDriver() != null) {
			getEffectiveInputDriver().unregister();
		}

		getEffectiveRenderer().destroyContext();
		getEffectiveRenderer().destroy();
		getEffectiveWindow().destroy();
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
		return getEffectiveWindow();
	}
	
	@Override
	public IWindowRenderer getRenderer() {
		return getEffectiveRenderer();
	}
	
	@Override
	public IDesktopInputDriver getInputDriver() {
		return getEffectiveInputDriver();
	}
	
	
	public void useDefaultInputDriver(boolean enabled) {
		this.useDefaultInputDriver = enabled;
	}
	
	
	public void setWindowBuilder(IWindowBuilder builder) {
		this.setWindowBuilder = builder;
	}
	
	public void setWindowConfigurator(IWindowConfigurator configurator) {
		this.setWindowConfigurator = configurator;
	}
	
	public void setInputDriver(IDesktopInputDriver inputDriver) {
		this.setInputDriver = inputDriver;
	}
	
	public void setRenderer(IWindowRenderer renderer) {
		this.setRenderer = renderer;
	}


	protected IWindowBuilder createDefaultWindowBuilder() {
		return windowFactory.createDefaultBuilder();
	}
	
	protected IWindowConfigurator createDefaultWindowConfigurator() {
		return (window) -> {};
	}
	
	protected IDesktopInputDriver createDefaultInputDriver(IEngineContext engineContext) throws EngineException {
		IDesktopInputService desktopInputService;
		try {
			desktopInputService = engineContext.getInjector().getInstance(IDesktopInputService.class);
		}
		catch (ConfigurationException | ProvisionException e) {
			throw new EngineException(e);
		}

		IDesktopInputProducer mainInputProducer = desktopInputService.getMainInputDispatch().getInputProducer();
		IDesktopInputDriver inputDriver = new GLFWInputDriver(mainInputProducer, engineContext.getInjector().getInstance(IObjectPoolFactory.class));
		return inputDriver;
	}
	
	protected IWindowRenderer createDefaultRenderer(IEngineContext engineContext) {
		return new DefaultGameWindowRenderer(engineContext.getEngine().getEngineDriver(), engineContext.getEngine().getRenderService().getSurfaceManager());
	}
	
	
	protected IWindowBuilder getEffectiveWindowBuilder() {
		return effectiveWindowBuilder;
	}
	
	protected IWindowConfigurator getEffectiveWindowConfigurator() {
		return effectiveWindowConfigurator;
	}
	
	protected IWindow getEffectiveWindow() {
		return effectiveWindow;
	}
	
	protected IDesktopInputDriver getEffectiveInputDriver() {
		return effectiveInputDriver;
	}
	
	protected IWindowRenderer getEffectiveRenderer() {
		return effectiveRenderer;
	}
	
}
