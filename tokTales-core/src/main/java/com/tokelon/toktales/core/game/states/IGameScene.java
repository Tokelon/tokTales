package com.tokelon.toktales.core.game.states;

import com.tokelon.toktales.core.game.controller.ICameraController;
import com.tokelon.toktales.core.game.controller.IControllerManager;
import com.tokelon.toktales.core.game.controller.IPlayerController;
import com.tokelon.toktales.core.game.controller.map.IMapController;
import com.tokelon.toktales.core.game.world.IWorldspace;

public interface IGameScene {
	// Extend from IEngineContext?
	
	/* The gamestate defines how things should work,
	 * the scene defines with what data (and logic)
	 * 
	 */
	
	// Map
	// Worldspace - Entities
	// Events?
	// Logic
	// Player?
	
	/* One map for one scene.
	 * Similar to one player controller and one camera controller, there should be one map controller.
	 * These should not change during the scene's lifetime?
	 * 
	 */

	/* Same problem as with gamestate
	public void onCreate();
	public void onDestroy();
	*/

	// TODO: Add surface change events?
	
	
	public static final String CONTROLLER_PLAYER = "gamescene-controller_player";
	public static final String CONTROLLER_MAP = "gamescene-controller_map";
	public static final String CONTROLLER_CAMERA = "gamescene-controller_camera";
	
	

	/**
	 * @return The gamestate this scene is assigned to, or null if this scene has not been assigned yet.
	 */
	public IGameState getGamestate();
	
	
	public boolean isAssigned();
	
	
	/** Called to confirm that the given game state can be assigned to this scene.
	 * 
	 * @param gamestate The state this scene is being assigned to.
	 * @return True if assignment was successful, false if not.
	 */
	public boolean assign(IGameState gamestate); // rename to assignToState?
	
	public boolean deassign(); // rename to unassign?

	
	/** Called when the scene is added to the state scene control.
	 */
	public void onAssign();

	
	public void onStart();

	public void onPause();
	
	public void onResume();
	
	public void onStop();

	
	/** Called when the scene is removed from the state scene control.
	 */
	public void onDeassign();


	public void onUpdate(long timeMillis);
	//public void onRender();
	
	
	/** This scene's worldspace.
	 * 
	 * @return The worldspace for this scene.
	 */
	public IWorldspace getWorldspace();

	/** The control handler for scene specific action handling.
	 * 
	 * @return The scene control handler.
	 */
	public IControlHandler getSceneControlHandler();


	public IControllerManager getControllerManager();

	public IPlayerController getPlayerController();
	public ICameraController getCameraController();
	public IMapController getMapController();
	
	// NO setters!
	//public void setMapController(IMapController mapController);

	// Controllers should never be null!
	//public boolean hasMap();
	
	
	/* Add?
	public interface IExternalControllerGameScene extends IGameScene {
		
		public void setPlayerController(IPlayerController playerController);
		public void setCameraController(ICameraController cameraController);
		public void setMapController(IMapController mapController);
	}
	*/
	
}
