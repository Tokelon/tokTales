package com.tokelon.toktales.core.game;

import com.tokelon.toktales.core.engine.IEngineContext;

/** The game adapter is the main entry point for your logic.
 * <p>
 * Here you can initialize your game logic, create game states and react to game events.
 * <p>
 * Note: All methods will be called after the main event logic has run.
 */
public interface IGameAdapter {


	/** Called when the game is created.
	 * 
	 * @param engineContext
	 */
	public void onCreate(IEngineContext engineContext);
	
	/** Called when the game is started.
	 */
	public void onStart();

	/** Called when the game is resumed.
	 */
	public void onResume();

	/** Called when the game is paused.
	 */
	public void onPause();

	/** Called when the game is stopped.
	 */
	public void onStop();
	
	/** Called when the game is destroyed.
	 * <p>
	 * Note: This method is not guaranteed to be called on all platforms.
	 */
	public void onDestroy();
	
	
	/** Called when the game is updated.
	 */
	public void onUpdate();

	/** Called when the game is rendered.
	 */
	public void onRender();
	
}
