package com.tokelon.toktales.core.game.states;

import com.tokelon.toktales.core.game.controller.ICameraController;
import com.tokelon.toktales.core.game.controller.IControllerManager;
import com.tokelon.toktales.core.game.controller.IPlayerController;
import com.tokelon.toktales.core.game.controller.map.IMapController;
import com.tokelon.toktales.core.game.world.IWorldspace;

public interface IGameScene {

	public static final String CONTROLLER_PLAYER = "gamescene-controller_player";
	public static final String CONTROLLER_MAP = "gamescene-controller_map";
	public static final String CONTROLLER_CAMERA = "gamescene-controller_camera";
	
	
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
	
	
	
	// Maybe replace the word assign with associate or allocate
	
	/** Called to confirm that the given game state can be assigned to this scene.
	 * 
	 * @param gamestate The state this scene is being assigned to.
	 * @return True if assignment was successful, false if not.
	 */
	public boolean assign(IGameState gamestate);

	
	/** Called when the scene is added to the scene control.
	 * 
	 */
	public void onAssign();

	
	public void onStart();

	public void onPause();
	
	public void onResume();
	
	public void onStop();

	
	/** Called when the scene is removed from the scene control.
	 */
	public void onDeassign();


	public void onUpdate(long timeMillis);
	//public void onRender();
	
	
	/* Same problem as with gamestate
	public void onCreate();
	public void onDestroy();
	*/
	
	// Add the moment we need it
	//public IGameState getAssignedState();
	
	
	/** The control handler for scene specific action handling.
	 * 
	 * @return The scene control handler.
	 */
	public IControlHandler getSceneControlHandler();
	

	
	
	public IWorldspace getWorldspace();



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
