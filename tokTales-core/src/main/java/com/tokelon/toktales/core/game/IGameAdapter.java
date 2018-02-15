package com.tokelon.toktales.core.game;

import com.tokelon.toktales.core.engine.IEngineContext;

/** All methods will be called after the main event logic has run.
 * 
 */
public interface IGameAdapter {

	//TODO: Check and document which methods might not be called
	

	//onGameCreate() ?
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
