package com.tokelon.toktales.core.game.states;

public interface ITypedGameState<T extends IGameScene> extends IGameState {

	
	/** Assigns a typed scene to this state with the given name.
	 * 
	 * @param name
	 * @param scene
	 * @return True if the given scene was assigned successfully, false if not.
	 * @throws NullPointerException If name or scene is null.
	 */
	public boolean assignSceneTyped(String name, T scene);

	
	@Override
	public T getActiveScene();
	
	@Override
	public IGameSceneControl<T> getSceneControl();
	
}
