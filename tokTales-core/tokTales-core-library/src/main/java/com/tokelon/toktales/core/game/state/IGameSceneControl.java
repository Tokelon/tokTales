package com.tokelon.toktales.core.game.state;

import java.util.Map;

public interface IGameSceneControl<T extends IGameScene> {
	// Implement listener support?

	
	public void pauseScene();
	public void resumeScene();
	
	public void updateScene(long timeMillis);
	
	
	
	public T getActiveScene();
	public String getActiveSceneName();

	public boolean hasScene(String name);
	
	public T getScene(String name);

	
	/**
	 * @return An unmodifiable map containing all name to scene mappings.
	 */
	public Map<String, T> getSceneMap();


	
	public interface IModifiableGameSceneControl<T extends IGameScene> extends IGameSceneControl<T> {
		
		public void addScene(String name, T scene);
		public void changeScene(String name);
		public T removeScene(String name);
	}

	
	public interface IGameSceneControlFactory {
		
		public <T extends IGameScene> IGameSceneControl<T> create();
		public <T extends IGameScene> IModifiableGameSceneControl<T> createModifiable();
	}
	
}
