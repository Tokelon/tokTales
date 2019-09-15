package com.tokelon.toktales.desktop.lwjgl;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.system.MemoryUtil;

import com.tokelon.toktales.core.engine.EngineException;
import com.tokelon.toktales.core.engine.IEngineContext;
import com.tokelon.toktales.core.engine.inject.IHierarchicalInjectConfig;
import com.tokelon.toktales.core.engine.input.IInputService;
import com.tokelon.toktales.core.engine.render.ISurface;
import com.tokelon.toktales.core.util.IObjectPool.IObjectPoolFactory;
import com.tokelon.toktales.desktop.engine.DesktopEngineLauncher;
import com.tokelon.toktales.desktop.input.IDesktopInputService;
import com.tokelon.toktales.desktop.input.dispatch.IDesktopInputProducer;
import com.tokelon.toktales.desktop.lwjgl.LWJGLWindow.WindowFactory;
import com.tokelon.toktales.desktop.lwjgl.input.GLFWInputDriver;

public class LWJGLEngineLauncher extends DesktopEngineLauncher {
	// TODO: Additional window properties, like visible resisable etc.
	// Better: Make window object available through a solid interface

	
	private int windowWidth = 1280;
	private int windowHeight = 720;
	private String windowTitle = "";

	private LWJGLRenderer renderer;
	private GLFWInputDriver inputDriver;
	
	
	public LWJGLEngineLauncher(IHierarchicalInjectConfig injectConfig) {
		super(injectConfig);
	}
	
	@Override
	protected void startEngine(IEngineContext engineContext) throws EngineException {

		LWJGLProgram lwProgram = new LWJGLProgram();
		try {
			
			lwProgram.setup();
			try {
				LWJGLWindow lwMainWindow = createWindow();


				lwMainWindow.create();
				try {
					lwMainWindow.setSwapInterval(1);	// Enable Vsync
					lwMainWindow.show();

					
					IInputService inputService = engineContext.getEngine().getInputService();
					if(inputService instanceof IDesktopInputService) {
						IDesktopInputService desktopInputService = (IDesktopInputService) inputService;
						IDesktopInputProducer mainInputProducer = desktopInputService.getMainInputDispatch().getInputProducer();
						inputDriver = new GLFWInputDriver(lwMainWindow, mainInputProducer, engineContext.getInjector().getInstance(IObjectPoolFactory.class));
					}
					else {
						getLogger().error("Input driver could not be created: Input service is not compatible with input driver. Input service must be of type {}", IDesktopInputService.class);
						//throw new EngineException("Input service must be of type " + IDesktopInputService.class);
					}
					
					renderer = new LWJGLRenderer(engineContext.getGame());
					ISurface surface = renderer.onWindowCreated(lwMainWindow);

					engineContext.getEngine().getRenderService().getSurfaceHandler().publishSurface(surface);
					engineContext.getEngine().getRenderService().getSurfaceHandler().updateSurface(surface);

					
					super.startEngine(engineContext);
				}
				finally {
					lwMainWindow.destroy();
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
		// Do not invoke super, as it would call the looper
		
		renderer.runLoop();
	}
	
	
	
	public void setWindowSize(int width, int height) {
		this.windowWidth = width;
		this.windowHeight = height;
	}
	
	public void setWindowTitle(String title) {
		this.windowTitle = title;
	}
	

	private LWJGLWindow createWindow() throws LWJGLException {
		
		LWJGLWindow.WindowFactory mainWindowFactory = new WindowFactory();
		
		mainWindowFactory.setWindowHint(GLFW.GLFW_VISIBLE, GLFW.GLFW_TRUE);
		mainWindowFactory.setWindowHint(GLFW.GLFW_RESIZABLE, GLFW.GLFW_FALSE);
		
		//GLFW.glfwGetPrimaryMonitor()
		LWJGLWindow mainWindow = mainWindowFactory.create(windowWidth, windowHeight, windowTitle, MemoryUtil.NULL, MemoryUtil.NULL);
		//LWJGLWindow mainWindow = mainWindowFactory.create(windowWidth, windowHeight, windowTitle, GLFW.glfwGetPrimaryMonitor(), MemoryUtil.NULL);

		
		return mainWindow;
	}
	
}
