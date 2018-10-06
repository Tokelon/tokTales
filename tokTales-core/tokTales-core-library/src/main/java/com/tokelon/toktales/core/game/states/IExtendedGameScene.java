package com.tokelon.toktales.core.game.states;

import com.tokelon.toktales.core.game.controller.ICameraController;
import com.tokelon.toktales.core.game.controller.IPlayerController;
import com.tokelon.toktales.core.game.controller.map.IMapController;
import com.tokelon.toktales.core.game.world.IWorldspace;

public interface IExtendedGameScene extends IGameScene {

	
	/** This scene's worldspace.
	 * 
	 * @return The worldspace for this scene.
	 */
	public IWorldspace getWorldspace();


	/* Notes on controllers:
	 * 1. No setters.
	 * 2. Controllers should never be null.
	 */
	
	/** This scene's player controller.
	 * 
	 * @return The player controller for this scene.
	 */
	public IPlayerController getPlayerController();
	
	/** This scene's camera controller.
	 * <p>
	 * Note that the controller should use the camera returned by {@link #getSceneCamera()}.
	 * 
	 * @return The camera controller for this scene.
	 */
	public ICameraController getCameraController();
	
	/** This scene's map controller.
	 * 
	 * @return The map controller for this scene.
	 */
	public IMapController getMapController();
	
}
