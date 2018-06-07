package com.tokelon.toktales.core.game.states;

import com.tokelon.toktales.core.game.controller.IControllerManager;
import com.tokelon.toktales.core.game.model.ICamera;

public interface IGameScene {
	// Add getters for context access?
	
	/* The gamestate defines how things should work,
	 * the scene defines with what data (and logic)
	 * 
	 */
	// Map / Worldspace - Entities / Events? / Logic / Player?
	
	

	/**
	 * @return The gamestate this scene is assigned to, or null if this scene has not been assigned.
	 */
	public IGameState getGamestate();
	
	
	public boolean isAssigned();
	
	
	/** Called to confirm that the given game state can be assigned to this scene.
	 * 
	 * @param gamestate The state this scene is being assigned to.
	 * @return True if assignment was successful, false if not.
	 */
	public boolean assign(IGameState gamestate);
	
	public boolean deassign(); // rename to unassign?

	
	/** Called when the scene is added to the state scene control.
	 */
	public void onAssign();

	/** Called when the scene is set as the active scene.
	 */
	public void onStart();

	/** Called when the scene is paused.
	 */
	public void onPause();

	/** Called when the scene is resumed.
	 */
	public void onResume();

	/** Called when the scene is no longer the active scene.
	 */
	public void onStop();

	
	/** Called when the scene is removed from the state scene control.
	 */
	public void onDeassign();


	/** Called when the scene should be updated.
	 * 
	 * @param timeMillis
	 */
	public void onUpdate(long timeMillis);

	// no onRender() -> for custom rendering the scene should use render order

	

	/** The controller manager for this scene.
	 * 
	 * @return The scene controller manager.
	 */
	public IControllerManager getControllerManager();

	
	/** The camera used for this scene.
	 * 
	 * @return The scene camera.
	 */
	public ICamera getSceneCamera();
	
	
	/** The control handler for scene specific action handling.
	 * 
	 * @return The scene control handler.
	 */
	public IControlHandler getSceneControlHandler();

}
