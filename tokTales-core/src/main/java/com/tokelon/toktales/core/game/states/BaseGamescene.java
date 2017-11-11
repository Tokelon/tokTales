package com.tokelon.toktales.core.game.states;

import com.tokelon.toktales.core.engine.TokTales;
import com.tokelon.toktales.core.game.controller.CameraController;
import com.tokelon.toktales.core.game.controller.ControllerManager;
import com.tokelon.toktales.core.game.controller.ICameraController;
import com.tokelon.toktales.core.game.controller.IControllerManager;
import com.tokelon.toktales.core.game.controller.IPlayerController;
import com.tokelon.toktales.core.game.controller.PlayerController;
import com.tokelon.toktales.core.game.controller.map.IMapController;
import com.tokelon.toktales.core.game.model.map.IBlockMap;
import com.tokelon.toktales.core.game.model.map.IMapLayer;
import com.tokelon.toktales.core.game.screen.IStateRender;
import com.tokelon.toktales.core.game.screen.order.IRenderLayerStack;
import com.tokelon.toktales.core.game.screen.order.IRenderOrder;
import com.tokelon.toktales.core.game.world.IWorldspace;
import com.tokelon.toktales.core.game.world.Worldspace;

public class BaseGamescene implements IGameScene {

	public static final String TAG = "BaseGamescene";

	private static final String RENDER_LAYER_TAG = "BaseGamescene_Map";

	
	
	private boolean logUpdateTime = false;

	
	private IControllerManager sceneControllerManager;
	private IPlayerController sceneControllerPlayer;
	private ICameraController sceneControllerCamera;
	private IMapController sceneControllerMap;
	
	
	private IWorldspace sceneWorldspace;

	private IControlHandler sceneControlHandler;
	

	private IGameState gamestate;
	

	
	public BaseGamescene() {
		this.sceneControlHandler = new IControlHandler.EmptyControlHandler();
		this.sceneControllerManager = new ControllerManager();
	}
	
	
	/**
	 * 
	 * @return The scene's gamestate, or null if the scene has not been assigned yet.
	 */
	protected IGameState getGamestate() {
		return gamestate;
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
	public void onAssign() {
		sceneWorldspace = gamesceneCreateWorldspace();
		
		setPlayerController(gamesceneCreatePlayerController());
		
		setCameraController(gamesceneCreateCameraController());
		
		// TODO: Provide default
		//setMapController(gamesceneCreateMapController());
	}
	

	/** Will be called in {@link #onAssign()}.
	 * @return The worldspace for this gamescene.
	 */
	protected IWorldspace gamesceneCreateWorldspace() {
		Worldspace worldspace = new Worldspace(this, getGamestate().getGame().getWorld());
		
		return worldspace;
	}
	
	/** Will be called in {@link #onAssign()}.
	 * @return The player controller for this gamescene.
	 */
	protected IPlayerController gamesceneCreatePlayerController() {
		PlayerController playerController = new PlayerController();
		playerController.receiveScriptManager(getGamestate().getGame().getScriptManager());
		return playerController;
	}
	
	/** Will be called in {@link #onAssign()}.
	 * @return The camera controller for this gamescene.
	 */
	protected ICameraController gamesceneCreateCameraController() {
		CameraController cameraController = new CameraController();
		return cameraController;
	}

	/** Will be called in {@link #onAssign()}.
	 * 
	 * @return The map controller for this gamescene.
	 */
	protected IMapController gamesceneCreateMapController() {
		// TODO: Implement empty map controller
		return null;
	}
	
	
	
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

	
	
	/** Sets the scene control handler.
	 * 
	 * @param controlHandler
	 * @throws NullPointerException If controlHandler is null.
	 */
	public void setSceneControlHandler(IControlHandler controlHandler) {
		if(controlHandler == null) {
			throw new NullPointerException();
		}
		
		this.sceneControlHandler = controlHandler;
	}
	
	
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

	
	// Should these really be public?
	public void setCameraController(ICameraController cameraController) {
		this.sceneControllerCamera = cameraController;
		sceneControllerManager.setController(CONTROLLER_CAMERA, cameraController);
		
		onCameraChange(cameraController);
	}
	
	public void setPlayerController(IPlayerController playerController) {
		this.sceneControllerPlayer = playerController;
		sceneControllerManager.setController(CONTROLLER_PLAYER, playerController);
		
		onPlayerChange(playerController);
	}
	
	public void setMapController(IMapController mapController) {
		this.sceneControllerMap = mapController;
		sceneControllerManager.setController(CONTROLLER_MAP, mapController);
		
		onMapChange(mapController);
	}
	

}
