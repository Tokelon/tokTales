package com.tokelon.toktales.core.game.states;

import javax.inject.Inject;

import com.tokelon.toktales.core.engine.IEngineContext;
import com.tokelon.toktales.core.engine.TokTales;
import com.tokelon.toktales.core.engine.inject.RequiresInjection;
import com.tokelon.toktales.core.game.controller.ICameraController;
import com.tokelon.toktales.core.game.controller.IControllerManager;
import com.tokelon.toktales.core.game.controller.IPlayerController;
import com.tokelon.toktales.core.game.controller.map.IMapController;
import com.tokelon.toktales.core.game.model.map.IBlockMap;
import com.tokelon.toktales.core.game.model.map.IMapLayer;
import com.tokelon.toktales.core.game.screen.IStateRender;
import com.tokelon.toktales.core.game.screen.order.IRenderLayerStack;
import com.tokelon.toktales.core.game.screen.order.IRenderOrder;
import com.tokelon.toktales.core.game.world.IWorldspace;
import com.tokelon.toktales.tools.inject.IParameterInjector;
import com.tokelon.toktales.tools.inject.IParameterInjector.IParameterInjectorFactory;

/** Use as the base for game scenes. 
 */
@RequiresInjection
public class BaseGamescene implements IGameScene {

	public static final String TAG = "BaseGamescene";

	private static final String RENDER_LAYER_TAG = "BaseGamescene_Map";


	/* Base objects */
	
	private IParameterInjector gamesceneInjector;

	private boolean logUpdateTime = false;

	
	/* Injected base objects */
	
	private IEngineContext engineContext;

	
	/* Scene objects */

	private IWorldspace sceneWorldspace;

	// TODO: Handle controllers differently? More like data maybe?
	private IControllerManager sceneControllerManager;
	private IPlayerController sceneControllerPlayer;
	private ICameraController sceneControllerCamera;
	private IMapController sceneControllerMap;
	
	private IControlHandler sceneControlHandler;
	

	private IGameState gamestate;
	
	@Inject
	public BaseGamescene() {
		// Used for injection
	}
	
	public BaseGamescene(
			IWorldspace defaultWorldspace,
			IControlHandler defaultControlHandler,
			IControllerManager defaultControllerManager,
			IPlayerController defaultPlayerController,
			ICameraController defaultCameraController,
			IMapController defaultMapController
	) {
		// Any of these can be null
		this.sceneWorldspace = defaultWorldspace;
		this.sceneControlHandler = defaultControlHandler;
		this.sceneControllerManager = defaultControllerManager;
		this.sceneControllerPlayer = defaultPlayerController;
		this.sceneControllerCamera = defaultCameraController;
		this.sceneControllerMap = defaultMapController;
	}
	
	
	/** Injects all dependencies for a scene and object initialization.
	 * <p>
	 * Note that this method will be called before any <i>method</i> injections of subtypes.
	 * 
	 * @see #initBaseDependencies
	 * @see #initSceneDependencies
	 */
	@Inject
	protected void injectDependencies(
			IParameterInjectorFactory parameterInjectorFactory,
			IEngineContext context,
			IWorldspace worldspace,
			IControlHandler controlHandler,
			IControllerManager controllerManager,
			IPlayerController playerController,
			ICameraController cameraController,
			IMapController mapController
	) {
		
		this.gamesceneInjector = parameterInjectorFactory.create(InjectGameScene.class, this);
		
		
		initBaseDependencies(context);
		afterInitBaseDependencies();
		
		
		IWorldspace defaultWorldspace = sceneWorldspace == null ? worldspace : sceneWorldspace;
		IControlHandler defaultControlHandler = sceneControlHandler == null ? controlHandler : sceneControlHandler;
		IControllerManager defaultControllerManager = sceneControllerManager == null ? controllerManager : sceneControllerManager;
		IPlayerController defaultPlayerController = sceneControllerPlayer == null ? playerController : sceneControllerPlayer;
		ICameraController defaultCameraController = sceneControllerCamera == null ? cameraController : sceneControllerCamera;
		IMapController defaultMapController = sceneControllerMap == null ? mapController : sceneControllerMap;
		
		initSceneDependencies(defaultWorldspace, defaultControlHandler, defaultControllerManager, defaultPlayerController, defaultCameraController, defaultMapController);
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
	}
	
	/** Called after base dependencies have been initialized.
	 * 
	 * @see #initBaseDependencies
	 */
	protected void afterInitBaseDependencies() { }
	

	/** Initializes the state dependencies.
	 * 
	 * @see #afterInitSceneDependencies()
	 */
	protected void initSceneDependencies(
			IWorldspace defaultWorldspace,
			IControlHandler defaultControlHandler,
			IControllerManager defaultControllerManager,
			IPlayerController defaultPlayerController,
			ICameraController defaultCameraController,
			IMapController defaultMapController
	) {
		setSceneWorldspace(defaultWorldspace);
		setSceneControlHandler(defaultControlHandler);
		setSceneControllerManager(defaultControllerManager);
		setScenePlayerController(defaultPlayerController);
		setSceneCameraController(defaultCameraController);
		setSceneMapController(defaultMapController);
	}
	
	/** Called after scene dependencies have been initialized.
	 * 
	 * @see #initSceneDependencies
	 */
	protected void afterInitSceneDependencies() {
		// Nothing yet
	}
	

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

	
	@Override
	public void onAssign() {
		// Nothing yet
	}
	
	
	@Override
	public void onUpdate(long timeMillis) {
		long startTime = System.currentTimeMillis();
		

		// Important - Adjust the camera first, then the player (and everything else)

		getCameraController().getCamera().adjustState(timeMillis);

		getPlayerController().getPlayer().adjustState(timeMillis);	// The player is an entity as well so it does not really need explicit updating ?

		getWorldspace().adjustState(timeMillis);
		

		
		//if(hasMap())

		
		long dt = System.currentTimeMillis() - startTime;
		if(logUpdateTime && startTime % 1000 > 0 && startTime % 1000 < 200) TokTales.getLog().d(TAG, "Gamestate Update Time: " +dt);		
	}
	

	// TODO: Remove or refactor
	public void setMapController(IMapController mapController) {
		this.sceneControllerMap = mapController;
		sceneControllerManager.setController(CONTROLLER_MAP, mapController);
		
		onMapChange(mapController);
	}
	
	// TODO: Refactor these
	protected void onMapChange(IMapController mapController) {
		registerMapRenderOrder(mapController);
	}
	
	protected void onPlayerChange(IPlayerController playerController) {
		// Is this ok?
		getWorldspace().putEntity("player", getPlayerController().getPlayer());
	}
	
	protected void onCameraChange(ICameraController cameraController) {
		// Nothing yet
	}
	
	
	
	// TODO: Make separate MapGamescene and move this into there
	private void registerMapRenderOrder(IMapController mapController) {
		IBlockMap map = mapController.getMap();
		IRenderOrder renderOrder = getGamestate().getRenderOrder();
		IStateRender renderer = getGamestate().getStateRender();
		synchronized (renderOrder) {
		
			// Remove old callbacks
			renderOrder.removeAllTaggedLayers(RENDER_LAYER_TAG);
			
			
			int index = 1;
			for(int i = map.getLevelReference().getLowestLevel(); i <= map.getLevelReference().getHighestLevel(); i++) {
				IMapLayer mapLayer = mapController.getMap().getLayerOnLevel(i);
				String layerName = mapLayer.getName();
				
				renderOrder.insertTaggedLayerAt(index, layerName, RENDER_LAYER_TAG);
				IRenderLayerStack stack = renderOrder.getStackForIndex(index);
				stack.addCallbackAt(0d, renderer);
				
				index++;
			}
		}
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
	public IControlHandler getSceneControlHandler() {
		return sceneControlHandler;
	}

	@Override
	public IWorldspace getWorldspace() {
		return sceneWorldspace;
	}


	@Override
	public IControllerManager getControllerManager() {
		return sceneControllerManager;
	}

	@Override
	public IPlayerController getPlayerController() {
		return sceneControllerPlayer;
	}
	
	@Override
	public ICameraController getCameraController() {
		return sceneControllerCamera;
	}

	@Override
	public IMapController getMapController() {
		return sceneControllerMap;
	}

	
	/** Returns an injector that can be used to inject this gamescene into dependencies via {@link InjectGameScene}.
	 * 
	 * @return An injector for this gamescene.
	 */
	protected IParameterInjector getGamesceneInjector() {
		return gamesceneInjector;
	}

	protected IEngineContext getEngineContext() {
		return engineContext;
	}

	

	/** Sets the scene worldspace.
	 * 
	 * @param worldspace
	 * @throws NullPointerException If worldspace is null.
	 */
	protected void setSceneWorldspace(IWorldspace worldspace) {
		if(worldspace == null) {
			throw new NullPointerException();
		}
		
		gamesceneInjector.injectInto(worldspace);
		this.sceneWorldspace = worldspace;
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
		
		gamesceneInjector.injectInto(controlHandler);
		this.sceneControlHandler = controlHandler;
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
		
		gamesceneInjector.injectInto(controllerManager);
		this.sceneControllerManager = controllerManager;
	}
	
	/** Sets the scene player controller.
	 * 
	 * @param playerController
	 * @throws NullPointerException If playerController is null.
	 */
	protected void setScenePlayerController(IPlayerController playerController) {
		if(playerController == null) {
			throw new NullPointerException();
		}
		
		gamesceneInjector.injectInto(playerController);
		this.sceneControllerPlayer = playerController;
		sceneControllerManager.setController(CONTROLLER_PLAYER, playerController);
	}
	
	/** Sets the scene camera controller.
	 * 
	 * @param cameraController
	 * @throws NullPointerException If cameraController is null.
	 */
	protected void setSceneCameraController(ICameraController cameraController) {
		if(cameraController == null) {
			throw new NullPointerException();
		}
		
		gamesceneInjector.injectInto(cameraController);
		this.sceneControllerCamera = cameraController;
		sceneControllerManager.setController(CONTROLLER_CAMERA, cameraController);
	}
	
	/** Sets the scene map controller.
	 * 
	 * @param mapController
	 * @throws NullPointerException If mapController is null.
	 */
	protected void setSceneMapController(IMapController mapController) {
		if(mapController == null) {
			throw new NullPointerException();
		}
		
		gamesceneInjector.injectInto(mapController);
		this.sceneControllerMap = mapController;
		sceneControllerManager.setController(CONTROLLER_MAP, mapController);
	}
	
}
