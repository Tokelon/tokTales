package com.tokelon.toktales.core.game;

import com.tokelon.toktales.core.engine.IEngineContext;

/** All methods will be called after the main event logic has run.
 * 
 */
public interface IGameAdapter {

	//TODO: Check and document which methods might not be called
	

	//onGameCreate() ?
	public void onCreate(IEngineContext engineContext);

	/** This method is not guaranteed to be called.
	 * 
	 */
	public void onDestroy();

	
	public void onStart();
	
	public void onResume();
	
	public void onPause();
	
	public void onStop();
	
	
	public void onUpdate();
	
	public void onRender();
	

	
	
	public class EmptyGameAdapter implements IGameAdapter {
		@Override
		public void onCreate(IEngineContext engineContext) { }

		@Override
		public void onDestroy() { }

		@Override
		public void onStart() {	}
		
		@Override
		public void onResume() { }

		@Override
		public void onPause() { }

		@Override
		public void onStop() { }
		
		@Override
		public void onUpdate() { }
		
		@Override
		public void onRender() { }
	}
	
	
}
