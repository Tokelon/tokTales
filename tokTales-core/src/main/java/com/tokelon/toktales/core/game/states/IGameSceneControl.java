package com.tokelon.toktales.core.game.states;

import java.util.Map;

public interface IGameSceneControl<T extends IGameScene> {
	// Listener support?

	
	public T getActiveScene();
	
	public String getActiveSceneName();

	
	public boolean hasScene(String name);
	
	public T removeScene(String name);
	
	public T getScene(String name);
	
	/**
	 * @return An unmodifiable map containing all name to scene mappings.
	 */
	public Map<String, T> getSceneMap();


	// Might want these, or do directly in gamestate
	//public void pauseActiveScene();
	//public void resumeActiveScene();

	
	public interface IModifiableGameSceneControl<T extends IGameScene> extends IGameSceneControl<T> {
		
		public void changeScene(String name);
		public void addScene(String name, T scene);
			
	}
	
}
