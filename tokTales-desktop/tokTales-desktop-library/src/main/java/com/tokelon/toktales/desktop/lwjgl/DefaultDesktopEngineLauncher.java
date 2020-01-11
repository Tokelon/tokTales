package com.tokelon.toktales.desktop.lwjgl;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.system.MemoryUtil;

import com.google.inject.ConfigurationException;
import com.google.inject.ProvisionException;
import com.tokelon.toktales.core.engine.EngineException;
import com.tokelon.toktales.core.engine.IEngineContext;
import com.tokelon.toktales.core.engine.IEngineLooper;
import com.tokelon.toktales.core.engine.log.ILoggerFactory;
import com.tokelon.toktales.desktop.engine.DefaultEngineLooper;
import com.tokelon.toktales.desktop.input.IDesktopInputDriver;
import com.tokelon.toktales.desktop.input.IDesktopInputService;
import com.tokelon.toktales.desktop.input.dispatch.IDesktopInputProducer;
import com.tokelon.toktales.desktop.lwjgl.input.GLFWInputDriver;
import com.tokelon.toktales.desktop.lwjgl.ui.DefaultGameWindowRenderer;
import com.tokelon.toktales.desktop.lwjgl.ui.LWJGLWindowFactory;
import com.tokelon.toktales.desktop.render.IWindowRenderer;
import com.tokelon.toktales.desktop.ui.window.IWindow;
import com.tokelon.toktales.desktop.ui.window.IWindowBuilder;
import com.tokelon.toktales.desktop.ui.window.IWindowConfigurator;
import com.tokelon.toktales.tools.core.objects.pools.IObjectPool.IObjectPoolFactory;
import com.tokelon.toktales.tools.core.sub.inject.config.IHierarchicalInjectConfig;

public class DefaultDesktopEngineLauncher extends LWJGLEngineLauncher {


	public static final int DEFAULT_WINDOW_WIDTH = 800;
	public static final int DEFAULT_WINDOW_HEIGHT = 600;
	public static final String DEFAULT_WINDOW_TITLE = "tokTales Engine";


	private IWindowBuilder effectiveWindowBuilder;
	private IWindowConfigurator effectiveWindowConfigurator;
	private IWindow effectiveWindow;
	private IDesktopInputDriver effectiveInputDriver;
	private IWindowRenderer effectiveRenderer;
	private IEngineLooper effectiveLooper;

	private boolean useDefaultInputDriver = true;
	private IWindowBuilder defaultWindowBuilder;
	private IWindowConfigurator defaultWindowConfigurator;

	private IWindowBuilder setWindowBuilder;
	private IWindowConfigurator setWindowConfigurator;
	private IDesktopInputDriver setInputDriver;
	private IWindowRenderer setRenderer;
	private IEngineLooper setLooper;
	
	
	public DefaultDesktopEngineLauncher(IHierarchicalInjectConfig injectConfig) {
		super(injectConfig);
	}
	
	public DefaultDesktopEngineLauncher(IHierarchicalInjectConfig injectConfig, ILoggerFactory loggerFactory) {
		super(injectConfig, loggerFactory);
	}
	
	
	@Override
	protected void startupProgram(IEngineContext engineContext) throws EngineException {
		super.startupProgram(engineContext);
		
		
		effectiveWindowBuilder = defaultWindowBuilder == null && setWindowBuilder != null
				? setWindowBuilder : (defaultWindowBuilder == null ? createDefaultWindowBuilder() : defaultWindowBuilder);
		effectiveWindowConfigurator = defaultWindowConfigurator == null && setWindowConfigurator != null
				? setWindowConfigurator : (defaultWindowConfigurator == null ? createDefaultWindowConfigurator() : defaultWindowConfigurator);
		
		effectiveInputDriver = useDefaultInputDriver ? createDefaultInputDriver(engineContext) : setInputDriver;
		effectiveRenderer = setRenderer == null ? createDefaultRenderer(engineContext) : setRenderer;
		effectiveLooper = setLooper == null ? createDefaultLooper(engineContext, effectiveRenderer) : setLooper;

		// Create window
		effectiveWindow = getEffectiveWindowBuilder().build(new LWJGLWindowFactory());
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
	protected void shutdownProgram(IEngineContext engineContext) throws EngineException {
		try {
			if(getEffectiveInputDriver() != null) {
				getEffectiveInputDriver().unregister();
			}

			getEffectiveRenderer().destroyContext();
			getEffectiveRenderer().destroy();
			getEffectiveWindow().destroy();
		}
		finally {
			super.shutdownProgram(engineContext);
		}
	}
	
	
	@Override
	protected void loop(IEngineContext engineContext, IEngineLooper defaultLooper) throws EngineException {
		// The default looper is a no-op here
		
		getEffectiveLooper().loop(engineContext);
	}
	
	@Override
	public void terminate() {
		getEffectiveLooper().stop();
	}
	
	
	public void useDefaultWindowBuilder() {
		this.defaultWindowBuilder = createDefaultWindowBuilder();
	}
	
	public void useDefaultWindowBuilderWith(int width, int height, String title) {
		this.defaultWindowBuilder = createDefaultWindowBuilderWith(width, height, title, MemoryUtil.NULL, MemoryUtil.NULL);
	}
	
	public void useDefaultWindowBuilderWith(int width, int height, String title, long monitor) {
		this.defaultWindowBuilder = createDefaultWindowBuilderWith(width, height, title, monitor, MemoryUtil.NULL);
	}

	public void useDefaultWindowBuilderWith(int width, int height, String title, long monitor, long share) {
		this.defaultWindowBuilder = createDefaultWindowBuilderWith(width, height, title, monitor, share);
	}
	
	public void useDefaultWindowConfigurator() {
		this.defaultWindowConfigurator = createDefaultWindowConfigurator();
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
	
	public void setLooper(IEngineLooper looper) {
		this.setLooper = looper;
	}


	// Keep create methods public or change to protected?
	
	public IWindowBuilder createDefaultWindowBuilder() {
		return createDefaultWindowBuilderWith(DEFAULT_WINDOW_WIDTH, DEFAULT_WINDOW_HEIGHT, DEFAULT_WINDOW_TITLE, MemoryUtil.NULL, MemoryUtil.NULL);
	}
	
	public IWindowBuilder createDefaultWindowBuilderWith(int width, int height, String title, long monitor, long share) {
		return (windowFactory) -> {
			windowFactory.setWindowHint(GLFW.GLFW_VISIBLE, GLFW.GLFW_TRUE);
			windowFactory.setWindowHint(GLFW.GLFW_RESIZABLE, GLFW.GLFW_FALSE);
			
			return windowFactory.create(width, height, title, monitor, share);
		};
	}
	
	public IWindowConfigurator createDefaultWindowConfigurator() {
		return (window) -> {};
	}
	
	public IDesktopInputDriver createDefaultInputDriver(IEngineContext engineContext) throws EngineException {
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
	
	public IWindowRenderer createDefaultRenderer(IEngineContext engineContext) {
		return new DefaultGameWindowRenderer(engineContext.getEngine().getEngineDriver(), engineContext.getEngine().getRenderService().getSurfaceManager());
	}

	public IEngineLooper createDefaultLooper(IEngineContext engineContext, IWindowRenderer renderer) {
		return new DefaultEngineLooper(renderer, new LWJGLInputProcessor());
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
	
	protected IEngineLooper getEffectiveLooper() {
		return effectiveLooper;
	}

}
