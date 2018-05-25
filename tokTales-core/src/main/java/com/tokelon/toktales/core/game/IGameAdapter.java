package com.tokelon.toktales.core.game;

import com.tokelon.toktales.core.engine.IEngineContext;

/** The game adapter is the main entry point for your custom logic.
 * <p>
 * Here you can initialize your game logic, create game states and react to game events.
 * <p>
 * Note: All methods will be called after the main event logic has run.
 */
public interface IGameAdapter {
	

	public default void onCreate(IEngineContext engineContext) { }
	
	public default void onStart() { }
	
	public default void onResume() { }
	
	public default void onPause() { }
	
	public default void onStop() { }
	
	/** This method is not guaranteed to be called.
	 */
	public default void onDestroy() { }
	
	
	public default void onUpdate() { }
	
	public default void onRender() { }
	
}
