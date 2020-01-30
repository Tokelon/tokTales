package com.tokelon.toktales.core.game.state;

import javax.inject.Inject;

import com.tokelon.toktales.core.game.controller.ICameraController;
import com.tokelon.toktales.core.game.controller.ICameraController.ICameraControllerFactory;
import com.tokelon.toktales.core.game.controller.IControllerManager;
import com.tokelon.toktales.core.game.controller.IPlayerController;
import com.tokelon.toktales.core.game.controller.map.IMapController;
import com.tokelon.toktales.core.game.model.ICamera;
import com.tokelon.toktales.core.game.model.map.IBlockMap;
import com.tokelon.toktales.core.game.model.map.IMapLayer;
import com.tokelon.toktales.core.game.state.render.IStateRender;
import com.tokelon.toktales.core.game.world.IWorldspace;
import com.tokelon.toktales.core.render.order.IRenderLayerStack;
import com.tokelon.toktales.core.render.order.IRenderOrder;
import com.tokelon.toktales.core.values.ControllerValues;
import com.tokelon.toktales.tools.core.sub.inject.annotations.RequiresInjection;
import com.tokelon.toktales.tools.script.annotation.ScriptAccess;

/** Use as an extended base for game scenes.
 */
@RequiresInjection
public class ExtendedGamescene extends BaseGamescene implements IExtendedGameScene {
	// Register DefaultControllersSetterInterceptor on controller manager as default?
	
	private static final String RENDER_LAYER_TAG = "BaseGamescene_Map";

	
	private boolean initialMapRenderRegistrationPending = false;
	

	private IWorldspace sceneWorldspace;

	// Handle controllers differently? More like data maybe?
	private ICameraController sceneCameraController;
	private IPlayerController scenePlayerController;
	private IMapController sceneMapController;
	
	private ICameraControllerFactory sceneCameraControllerFactory;
	
	
	@Inject
	public ExtendedGamescene() {
		super();
	}

	
	protected ExtendedGamescene(
			IControllerManager defaultControllerManager,
			ICamera defaultCamera,
			IControlHandler defaultControlHandler
	) {
		super(defaultControllerManager, defaultCamera, defaultControlHandler);
	}

	protected ExtendedGamescene(
			IWorldspace defaultWorldspace,
			ICameraControllerFactory defaultCameraControllerFactory,
			IPlayerController defaultPlayerController,
			IMapController defaultMapController
	) {
		this(null, null, null, defaultWorldspace, defaultCameraControllerFactory, defaultPlayerController, defaultMapController);
	}

	protected ExtendedGamescene(
			IControllerManager defaultControllerManager,
			ICamera defaultCamera,
			IControlHandler defaultControlHandler,
			IWorldspace defaultWorldspace,
			ICameraControllerFactory defaultCameraControllerFactory,
			IPlayerController defaultPlayerController,
			IMapController defaultMapController
	) {
		super(defaultControllerManager, defaultCamera, defaultControlHandler);
		
		// Any of these can be null
		this.sceneWorldspace = defaultWorldspace;
		this.scenePlayerController = defaultPlayerController;
		this.sceneCameraControllerFactory = defaultCameraControllerFactory;
		this.sceneMapController = defaultMapController;
	}
	
	
	/** Injects all extended dependencies for the scene.
	 * <p>
	 * This method will be called after injection of {@link BaseGamescene}.
	 * <p>
	 * Note that this method will be called before any <i>method</i> injections of subclasses.
	 * 
	 * @see #initExtendedDependencies()
	 * @see #initExtendedSceneDependencies
	 */
	@Inject
	protected void injectExtendedDependencies(
			IWorldspace worldspace,
			ICameraControllerFactory cameraControllerFactory,
			IPlayerController playerController,
			IMapController mapController
	) {
		
		initExtendedDependencies();
		afterInitExtendedDependencies();

		
		IWorldspace defaultWorldspace = sceneWorldspace == null ? worldspace : sceneWorldspace;
		ICameraControllerFactory defaultCameraControllerFactory = sceneCameraControllerFactory == null ? cameraControllerFactory : sceneCameraControllerFactory;
		ICameraController cameraController = defaultCameraControllerFactory.create(getSceneCamera());
		IPlayerController defaultPlayerController = scenePlayerController == null ? playerController : scenePlayerController;
		IMapController defaultMapController = sceneMapController == null ? mapController : sceneMapController;
		
		initExtendedSceneDependencies(defaultWorldspace, cameraController, defaultPlayerController, defaultMapController);
		afterInitSceneDependencies();
		
	}
	
	/** Initializes the extended dependencies.
	 * 
	 * @see #afterInitExtendedDependencies()
	 */
	protected void initExtendedDependencies() {
		// Nothing yet
	}
	
	/** Called after extended dependencies have been initialized.
	 * 
	 * @see #initExtendedDependencies()
	 */
	protected void afterInitExtendedDependencies() { }

	
	/** Initializes the extended scene dependencies.
	 * 
	 * @see #afterInitExtendedSceneDependencies()
	 */
	protected void initExtendedSceneDependencies(
			IWorldspace defaultWorldspace,
			ICameraController defaultCameraController,
			IPlayerController defaultPlayerController,
			IMapController defaultMapController
	) {

		setSceneWorldspace(defaultWorldspace);
		setScenePlayerController(defaultPlayerController);
		setSceneCameraController(defaultCameraController);
		setSceneMapController(defaultMapController);
	}
	
	/** Called after extended scene dependencies have been initialized.
	 * 
	 * @see #initExtendedSceneDependencies
	 */
	protected void afterInitExtendedSceneDependencies() { }
	
	
	/** The default implementation for this will:<br>
	 * 1. Invoke the default implementation {@link BaseGamescene#onAssign()}.<br>
	 * 
	 */
	@Override
	public void onAssign() {
		super.onAssign();

		
		/* Register map render order if needed */
		if(initialMapRenderRegistrationPending) {
			initialMapRenderRegistrationPending = false;
			getLogger().debug("Registering map render callbacks to previously unavailable gamestate");

			unregisterMapRenderCallbacks(getGamestate());
			registerMapRenderCallbacks(getGamestate(), getMapController());
		}
	}
	
	
	@Override
	public void onDeassign() {
		super.onDeassign();
		
		unregisterMapRenderCallbacks(getGamestate());
	}
	
	
	@Override
	public void onUpdate(long timeMillis) {
		super.onUpdate(timeMillis);

		// Important - Adjust the camera first (in base), then the player (and everything else)
		getPlayerController().getPlayer().getActor().adjustState(timeMillis);	// The player is an entity as well so it does not really need explicit updating ?

		getWorldspace().adjustState(timeMillis);
		
		
		// Adjust map?
	}
	

	// TODO: Refactor these?
	protected void onCameraControllerChange(ICameraController cameraController) {
		// Nothing yet
	}
	
	protected void onPlayerControllerChange(IPlayerController playerController) {
		// Is this ok?
		getWorldspace().putEntity("player", getPlayerController().getPlayer().getActor()); // TODO: How the f should this work?
	}
	
	protected void onMapControllerChange(IMapController mapController) {
		IGameState gamestate = getGamestate();
		if(gamestate == null) {
			getLogger().debug("Cannot register map render callbacks: no gamestate assigned yet");
			initialMapRenderRegistrationPending = true;
			return;
		}
		
		unregisterMapRenderCallbacks(gamestate);
		registerMapRenderCallbacks(gamestate, mapController);
	}
	
	
	// TODO: Refactor by making separate render order for map and combining it with the state render order
	protected void registerMapRenderCallbacks(IGameState gamestate, IMapController mapController) {
		IBlockMap map = mapController.getMap();
		
		IRenderOrder renderOrder = gamestate.getRenderOrder();
		IStateRender renderer = gamestate.getStateRender();
		synchronized (renderOrder) {
			
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
	
	protected void unregisterMapRenderCallbacks(IGameState gamestate) {
		IRenderOrder renderOrder = gamestate.getRenderOrder();

		synchronized (renderOrder) {
			// Remove previous map callbacks
			renderOrder.removeAllTaggedLayers(RENDER_LAYER_TAG);
		}
	}
	
	
	@Override
	public IWorldspace getWorldspace() {
		return sceneWorldspace;
	}

	
	@Override
	public IPlayerController getPlayerController() {
		return scenePlayerController;
	}
	
	@Override
	public ICameraController getCameraController() {
		return sceneCameraController;
	}

	@Override
	public IMapController getMapController() {
		return sceneMapController;
	}
	

	/** Sets the scene worldspace.
	 * 
	 * @param worldspace
	 * @throws NullPointerException If worldspace is null.
	 */
	@ScriptAccess
	public void setSceneWorldspace(IWorldspace worldspace) {
		if(worldspace == null) {
			throw new NullPointerException();
		}
		
		getGamesceneInjector().injectInto(worldspace);
		this.sceneWorldspace = worldspace;
	}
	
	
	/** Sets the scene player controller.
	 * 
	 * @param playerController
	 * @throws NullPointerException If playerController is null.
	 */
	@ScriptAccess
	public void setScenePlayerController(IPlayerController playerController) {
		if(playerController == null) {
			throw new NullPointerException();
		}
		
		getGamesceneInjector().injectInto(playerController);
		this.scenePlayerController = playerController;
		getControllerManager().setController(ControllerValues.CONTROLLER_PLAYER, playerController);
		
		onPlayerControllerChange(scenePlayerController);
	}
	
	/** Sets the scene camera controller.
	 * 
	 * @param cameraController
	 * @throws NullPointerException If cameraController is null.
	 */
	@ScriptAccess
	public void setSceneCameraController(ICameraController cameraController) {
		if(cameraController == null) {
			throw new NullPointerException();
		}
		
		getGamesceneInjector().injectInto(cameraController);
		this.sceneCameraController = cameraController;
		getControllerManager().setController(ControllerValues.CONTROLLER_CAMERA, cameraController);
		
		onCameraControllerChange(sceneCameraController);
	}
	
	/** Sets the scene map controller.
	 * 
	 * @param mapController
	 * @throws NullPointerException If mapController is null.
	 */
	@ScriptAccess
	public void setSceneMapController(IMapController mapController) {
		if(mapController == null) {
			throw new NullPointerException();
		}
		
		getGamesceneInjector().injectInto(mapController);
		this.sceneMapController = mapController;
		getControllerManager().setController(ControllerValues.CONTROLLER_MAP, mapController);
		
		onMapControllerChange(sceneMapController);
	}
	
}
