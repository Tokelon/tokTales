package com.tokelon.toktales.core.game.states;

import javax.inject.Inject;

import com.tokelon.toktales.core.config.IConfigManager;
import com.tokelon.toktales.core.config.IFileConfig;
import com.tokelon.toktales.core.config.IMainConfig;
import com.tokelon.toktales.core.engine.IEngine;
import com.tokelon.toktales.core.engine.IEngineContext;
import com.tokelon.toktales.core.engine.inject.RequiresInjection;
import com.tokelon.toktales.core.engine.log.ILogger;
import com.tokelon.toktales.core.game.IGame;
import com.tokelon.toktales.core.game.controller.IControllerManager;
import com.tokelon.toktales.core.game.model.ICamera;
import com.tokelon.toktales.tools.inject.IParameterInjector;
import com.tokelon.toktales.tools.inject.IParameterInjector.IParameterInjectorFactory;

/** Use as a base for game scenes.
 */
@RequiresInjection
public class BaseGamescene implements IGameScene {

	public static final String BASE_TAG = "BaseGamescene";


	private static final float DEFAULT_CAMERA_WIDTH = 640;
	private static final float DEFAULT_CAMERA_HEIGHT = 360;


	/* Base objects */
	
	private IParameterInjector gamesceneInjector;

	private boolean logUpdateTime = false;

	
	/* Injected base objects */
	
	private IEngineContext engineContext;
	private IEngine engine;
	private ILogger log;
	private IGame game;
	
	/* Scene objects */

	private IControllerManager sceneControllerManager;
	private ICamera sceneCamera;
	private IControlHandler sceneControlHandler;
	

	private IGameState gamestate;
	
	@Inject
	public BaseGamescene() {
		// Used for injection
	}
	
	protected BaseGamescene(
			IControllerManager defaultControllerManager,
			ICamera defaultCamera,
			IControlHandler defaultControlHandler
	) {
		// Any of these can be null
		this.sceneControllerManager = defaultControllerManager;
		this.sceneCamera = defaultCamera;
		this.sceneControlHandler = defaultControlHandler;
	}
	
	
	/** Injects all dependencies for the scene.
	 * <p>
	 * Note that this method will be called before any <i>method</i> injections of subclasses.
	 * 
	 * @see #initBaseDependencies
	 * @see #initSceneDependencies
	 */
	@Inject
	protected void injectDependencies(
			IParameterInjectorFactory parameterInjectorFactory,
			IEngineContext context,
			IControllerManager controllerManager,
			ICamera camera,
			IControlHandler controlHandler
	) {
		
		this.gamesceneInjector = parameterInjectorFactory.create(InjectGameScene.class, this);
		
		
		initBaseDependencies(context);
		afterInitBaseDependencies();
		
		
		IControllerManager defaultControllerManager = sceneControllerManager == null ? controllerManager : sceneControllerManager;
		ICamera defaultCamera = sceneCamera == null ? camera : sceneCamera;
		IControlHandler defaultControlHandler = sceneControlHandler == null ? controlHandler : sceneControlHandler;
		
		initSceneDependencies(defaultControllerManager, defaultCamera, defaultControlHandler);
		afterInitSceneDependencies();
		
	}
	
	
	/** Initializes the base dependencies.
	 * 
	 * @see #afterInitBaseDependencies()
	 */
	protected void initBaseDependencies(
			IEngineContext context
	) {
		
		this.engineContext = context;
		this.engine = context.getEngine();
		this.log = context.getLog();
		this.game = context.getGame();
	}
	
	/** Called after base dependencies have been initialized.
	 * 
	 * @see #initBaseDependencies
	 */
	protected void afterInitBaseDependencies() { }
	

	/** Initializes the scene dependencies.
	 * 
	 * @see #afterInitSceneDependencies()
	 */
	protected void initSceneDependencies(
			IControllerManager defaultControllerManager,
			ICamera defaultCamera,
			IControlHandler defaultControlHandler
			
	) {
		setSceneControllerManager(defaultControllerManager);
		setSceneCamera(defaultCamera);
		setSceneControlHandler(defaultControlHandler);
	}
	
	/** Called after scene dependencies have been initialized.
	 * 
	 * @see #initSceneDependencies
	 */
	protected void afterInitSceneDependencies() { }
	

	/** This implementation will assign the given gamestate to the current gamestate, obtainable by {@link #getGamestate()}.
	 * 
	 * @param gamestate The gamestate this scene is being assigned to.
	 * @return True.
	 * @throws IllegalArgumentException If gamestate is null.
	 */
	@Override
	public boolean assign(IGameState gamestate) {
		if(gamestate == null) {
			throw new IllegalArgumentException("gamestate must not be null");
		}
		
		this.gamestate = gamestate;
		return true;
	}
	
	@Override
	public boolean deassign() {
		this.gamestate = null;
		return true;
	}

	@Override
	public boolean isAssigned() {
		return gamestate != null;
	}

	@Override
	public IGameState getGamestate() {
		return gamestate;
	}

	
	/** The default implementation for this will:<br>
	 * 1. Set a default camera size.<br>
	 * 
	 */
	@Override
	public void onAssign() {
		/* 1. Set default camera size */
		float cameraWidth = DEFAULT_CAMERA_WIDTH;
		float cameraHeight = DEFAULT_CAMERA_HEIGHT;
		
		IConfigManager configManager = getGamestate().getGame().getConfigManager();
		IFileConfig config = configManager.getConfig(IConfigManager.MAIN_CONFIG);
		if(config instanceof IMainConfig) {
			IMainConfig mainConfig = (IMainConfig) configManager.getConfig(IConfigManager.MAIN_CONFIG);
			
			cameraWidth = mainConfig.getConfigCameraSizeUnitsX();
			cameraHeight = mainConfig.getConfigCameraSizeUnitsY();
		}

		ICamera camera = getSceneCamera();
		camera.setSize(cameraWidth, cameraHeight);
		getLog().i(getTag(), String.format("Camera size was set to default %.2fx%.2f", cameraWidth, cameraHeight));

	}
	
	
	@Override
	public void onUpdate(long timeMillis) {
		long startTime = System.currentTimeMillis();
		
		
		getSceneCamera().adjustState(timeMillis);

		
		long dt = System.currentTimeMillis() - startTime;
		if(logUpdateTime && startTime % 1000 > 0 && startTime % 1000 < 200) getLog().d(getTag(), "Gamestate Update Time: " +dt);		
	}
	

	
	@Override
	public void onStart() {	}
	
	@Override
	public void onPause() {	}
	
	@Override
	public void onResume() { }
	
	@Override
	public void onStop() { }
	
	@Override
	public void onDeassign() { }
	

	
	@Override
	public IControllerManager getControllerManager() {
		return sceneControllerManager;
	}

	@Override
	public IControlHandler getSceneControlHandler() {
		return sceneControlHandler;
	}

	@Override
	public ICamera getSceneCamera() {
		return sceneCamera;
	}


	// Make these public/interface methods?
	protected IEngineContext getEngineContext() {
		return engineContext;
	}
	
	protected IEngine getEngine() {
		return engine;
	}

	protected ILogger getLog() {
		return log;
	}

	protected IGame getGame() {
		return game;
	}
	
	
	/** Returns an injector that can be used to inject this gamescene into dependencies via {@link InjectGameScene}.
	 * 
	 * @return An injector for this gamescene.
	 */
	protected IParameterInjector getGamesceneInjector() {
		return gamesceneInjector;
	}
	
	
	/** Override to return you custom tag.
	 * 
	 * @return The tag for this state.
	 */
	protected String getTag() {
		return BASE_TAG;
	}

	
	/** Sets the scene controller manager.
	 * 
	 * @param controllerManager
	 * @throws NullPointerException If controllerManager is null.
	 */
	protected void setSceneControllerManager(IControllerManager controllerManager) {
		if(controllerManager == null) {
			throw new NullPointerException();
		}
		
		getGamesceneInjector().injectInto(controllerManager);
		this.sceneControllerManager = controllerManager;
	}
	
	/** Sets the scene camera.
	 * 
	 * @param camera
	 * @throws NullPointerException If camera is null.
	 */
	protected void setSceneCamera(ICamera camera) {
		if(camera == null) {
			throw new NullPointerException();
		}
		
		getGamesceneInjector().injectInto(camera);
		this.sceneCamera = camera;
	}
	
	/** Sets the scene control handler.
	 * 
	 * @param controlHandler
	 * @throws NullPointerException If controlHandler is null.
	 */
	protected void setSceneControlHandler(IControlHandler controlHandler) {
		if(controlHandler == null) {
			throw new NullPointerException();
		}
		
		getGamesceneInjector().injectInto(controlHandler);
		this.sceneControlHandler = controlHandler;
	}
	
}
