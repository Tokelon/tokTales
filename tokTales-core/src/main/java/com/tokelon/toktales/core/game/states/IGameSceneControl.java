package com.tokelon.toktales.core.game.states;

public interface IGameSceneControl<T extends IGameScene> {

	// Listener support?
	
	public T getActiveScene();
	
	public String getActiveSceneName();

	
	public boolean hasScene(String name);
	
	public T removeScene(String name);
	
	public T getScene(String name);
	
	
	public interface IModifiableGameSceneControl<T extends IGameScene> extends IGameSceneControl<T> {
		
		public void changeScene(String name);
		public void addScene(String name, T scene);
			
	}
	
	// Might want these, or do directly in gamestate
	//public void pauseActiveScene();
	//public void resumeActiveScene();
	
	
}
