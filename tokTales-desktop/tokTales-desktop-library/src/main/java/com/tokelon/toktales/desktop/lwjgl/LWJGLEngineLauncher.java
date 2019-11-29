package com.tokelon.toktales.desktop.lwjgl;

import org.lwjgl.glfw.GLFW;

import com.google.inject.ConfigurationException;
import com.google.inject.ProvisionException;
import com.tokelon.toktales.core.engine.EngineException;
import com.tokelon.toktales.core.engine.IEngineContext;
import com.tokelon.toktales.core.engine.IEngineLooper;
import com.tokelon.toktales.core.engine.log.ILoggerFactory;
import com.tokelon.toktales.core.engine.render.ISurface;
import com.tokelon.toktales.desktop.engine.DesktopEngineLauncher;
import com.tokelon.toktales.desktop.input.IDesktopInputDriver;
import com.tokelon.toktales.desktop.input.IDesktopInputService;
import com.tokelon.toktales.desktop.input.dispatch.IDesktopInputProducer;
import com.tokelon.toktales.desktop.lwjgl.input.GLFWInputDriver;
import com.tokelon.toktales.desktop.lwjgl.render.GLSurfaceController;
import com.tokelon.toktales.desktop.lwjgl.ui.LWJGLWindowFactory;
import com.tokelon.toktales.desktop.lwjgl.ui.LWJGLWindowRenderer;
import com.tokelon.toktales.desktop.render.IWindowRenderer;
import com.tokelon.toktales.desktop.ui.window.IWindow;
import com.tokelon.toktales.tools.core.objects.pools.IObjectPool.IObjectPoolFactory;
import com.tokelon.toktales.tools.core.sub.inject.config.IHierarchicalInjectConfig;

public class LWJGLEngineLauncher extends DesktopEngineLauncher {
	// TODO: Additional window properties, like visible resisable etc.
	// Better: Make window object available through a solid interface
	// TODO: Pass window in ctor!


	private int windowWidth = 1280;
	private int windowHeight = 720;
	private String windowTitle = "";

	private IDesktopInputDriver mainWindowInputDriver;
	private IWindowRenderer mainWindowRenderer;
	private IEngineLooper mainWindowLooper;
	
	
	public LWJGLEngineLauncher(IHierarchicalInjectConfig injectConfig) {
		super(injectConfig);
	}
	
	public LWJGLEngineLauncher(IHierarchicalInjectConfig injectConfig, ILoggerFactory loggerFactory) {
		super(injectConfig, loggerFactory);
	}


	@Override
	protected void startEngine(IEngineContext engineContext) throws EngineException {
		LWJGLProgram lwProgram = new LWJGLProgram();
		try {
			
			lwProgram.setup();
			try {
				IWindow mainWindow = createWindow();

				mainWindow.create();
				try {
					mainWindow.show();
					
					mainWindowInputDriver = setupInputDriver(engineContext, mainWindow);
					mainWindowRenderer = setupRenderer(engineContext, mainWindow);
					mainWindowLooper = setupLooper(mainWindowRenderer);

					
					super.startEngine(engineContext);
				}
				finally {
					mainWindow.destroy();
				}
			}
			finally {
				lwProgram.tearDown();
			}
		}
		catch (LWJGLException e) {
			throw new EngineException(e);
		}
	}
	
	@Override
	protected void loop() throws EngineException {
		// Do not invoke super, for it would call the looper
		
		mainWindowLooper.loop();
	}
	
	
	protected IDesktopInputDriver setupInputDriver(IEngineContext engineContext, IWindow window) throws EngineException {
		IDesktopInputService desktopInputService;
		try {
			desktopInputService = engineContext.getInjector().getInstance(IDesktopInputService.class);
		}
		catch (ConfigurationException | ProvisionException e) {
			throw new EngineException(e);
		}

		IDesktopInputProducer mainInputProducer = desktopInputService.getMainInputDispatch().getInputProducer();
		IDesktopInputDriver inputDriver = new GLFWInputDriver(window, mainInputProducer, engineContext.getInjector().getInstance(IObjectPoolFactory.class));
		return inputDriver;
	}
	
	protected IWindowRenderer setupRenderer(IEngineContext engineContext, IWindow window) {
		IWindowRenderer renderer = new LWJGLWindowRenderer(engineContext.getGame());
		ISurface surface = renderer.create(window);
		
		engineContext.getEngine().getRenderService().getSurfaceHandler().publishSurface(surface, new GLSurfaceController());
		engineContext.getEngine().getRenderService().getSurfaceHandler().updateSurface(surface);
		
		return renderer;
	}

	protected IEngineLooper setupLooper(IWindowRenderer renderer) {
		return new DefaultLWJGLLooper(renderer);
	}
	
	
	public void setWindowSize(int width, int height) {
		this.windowWidth = width;
		this.windowHeight = height;
	}
	
	public void setWindowTitle(String title) {
		this.windowTitle = title;
	}
	

	private IWindow createWindow() throws LWJGLException {
		LWJGLWindowFactory windowFactory = new LWJGLWindowFactory();
		windowFactory.setWindowHint(GLFW.GLFW_VISIBLE, GLFW.GLFW_TRUE);
		windowFactory.setWindowHint(GLFW.GLFW_RESIZABLE, GLFW.GLFW_FALSE);
		
		//IWindow window = windowFactory.create(windowWidth, windowHeight, windowTitle, windowFactory.getPrimaryMonitor());
		IWindow window = windowFactory.create(windowWidth, windowHeight, windowTitle);
		window.create();
		
		return window;
	}
	
}
