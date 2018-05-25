package com.tokelon.toktales.desktop.engine;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.system.MemoryUtil;

import com.tokelon.toktales.core.engine.EngineException;
import com.tokelon.toktales.core.engine.IEngineContext;
import com.tokelon.toktales.core.engine.IEngineLauncher;
import com.tokelon.toktales.core.engine.TokTales;
import com.tokelon.toktales.core.engine.inject.IHierarchicalInjectConfig;
import com.tokelon.toktales.core.engine.input.IInputService;
import com.tokelon.toktales.core.engine.render.ISurface;
import com.tokelon.toktales.core.engine.setup.BaseInjectSetup;
import com.tokelon.toktales.core.engine.setup.IEngineSetup;
import com.tokelon.toktales.core.game.IGameAdapter;
import com.tokelon.toktales.desktop.engine.inject.DesktopInjectConfig;
import com.tokelon.toktales.desktop.engine.inject.DesktopSetupInjectModule;
import com.tokelon.toktales.desktop.input.IDesktopInputService;
import com.tokelon.toktales.desktop.lwjgl.LWJGLException;
import com.tokelon.toktales.desktop.lwjgl.LWJGLProgram;
import com.tokelon.toktales.desktop.lwjgl.LWJGLRenderer;
import com.tokelon.toktales.desktop.lwjgl.LWJGLWindow;
import com.tokelon.toktales.desktop.lwjgl.LWJGLWindow.WindowFactory;
import com.tokelon.toktales.desktop.lwjgl.input.GLFWInputDriver;

public class DesktopEngineLauncher implements IEngineLauncher {

	private int windowWidth = 1280;
	private int windowHeight = 720;
	private String windowTitle = "";
	
	private final IHierarchicalInjectConfig injectConfig;
	
	
	/** Ctor with an inject config.
	 * 
	 * @param injectConfig
	 * 
	 * @see DesktopInjectConfig
	 */
	public DesktopEngineLauncher(IHierarchicalInjectConfig injectConfig) {
	    this.injectConfig = injectConfig;
	}

	
	@Override
	public void launch(Class<? extends IGameAdapter> adapter) throws EngineException {
		BaseInjectSetup setup = new BaseInjectSetup();
		launchWithSetup(adapter, setup);
	}
	
	
	@Override
	public void launchWithSetup(Class<? extends IGameAdapter> adapter, IEngineSetup setup) throws EngineException {
		// Inject game adapter
		injectConfig.override(new DesktopSetupInjectModule(adapter));
		
	    // Create engine context
		IEngineContext engineContext = setup.create(injectConfig);
		
		// Load into TokTales
		TokTales.load(engineContext);

		
		// Run after setting up TokTales
		setup.run(engineContext);
		

		try {
			additionalSetup(engineContext);
		} catch (LWJGLException e) {
			throw new EngineException(e);
		}
		
	}

	
	private void additionalSetup(IEngineContext engineContext) throws EngineException, LWJGLException {
		// Put this in setup or not?
		
		LWJGLProgram lwProgram = new LWJGLProgram();
		
		lwProgram.setup();
		try {
			LWJGLWindow lwMainWindow = createWindow();
			
			
			lwMainWindow.create();
			try {
				lwMainWindow.setSwapInterval(1);	// Enable Vsync
				lwMainWindow.show();
				
				GLFWInputDriver inputControl = new GLFWInputDriver(lwMainWindow);
				
				// TODO: Refactor if possible
				IInputService inputService = engineContext.getEngine().getInputService();
				if(inputService instanceof IDesktopInputService) {
					((IDesktopInputService) inputService).setInputDriver(inputControl);
				}
				else {
					throw new EngineException("Input service must be of type " + IDesktopInputService.class);
				}
				
				
				LWJGLRenderer renderer = new LWJGLRenderer(engineContext.getGame());
				ISurface surface = renderer.onWindowCreated(lwMainWindow);
				
				engineContext.getEngine().getRenderService().getSurfaceHandler().publishSurface(surface);
				engineContext.getEngine().getRenderService().getSurfaceHandler().updateSurface(surface);
				
				
				engineContext.getGame().getGameControl().createGame(); // calls onCreate on adapter

				engineContext.getGame().getGameControl().startGame();
				engineContext.getGame().getGameControl().resumeGame();
				try {
					renderer.runLoop();
				}
				finally {
					engineContext.getGame().getGameControl().pauseGame();
					engineContext.getGame().getGameControl().stopGame();
					
					engineContext.getGame().getGameControl().destroyGame();
				}
			}
			finally {
				lwMainWindow.destroy();
			}
			
		}
		finally {
			lwProgram.tearDown();
		}
		
	}
	
	
	// TODO: Additional window properties, like visible resisable etc.
	// Better: Make window object available through a solid interface
	
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
		
		return mainWindow;
	}
	
}
