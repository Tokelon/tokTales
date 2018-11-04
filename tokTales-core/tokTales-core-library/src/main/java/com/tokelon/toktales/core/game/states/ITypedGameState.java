package com.tokelon.toktales.core.game.states;

public interface ITypedGameState<T extends IGameScene> extends IGameState {

	
	/** Assigns a scene using a scene assignment parameter which respects generic types.
	 * <p>
	 * You only really need to use this if your scene type is generic.
	 * Otherwise you can use {@link #assignScene(String, IGameScene)}.
	 * <p>
	 * Returns whether the scene was actually assigned or not.
	 * 
	 * @param name
	 * @param sceneAssignment
	 * @return True if the given scene was assigned successfully, false if not.
	 * @throws NullPointerException If name or sceneAssignment is null.
	 */
	public boolean assignSceneWithGenericType(String name, IGameSceneAssignment sceneAssignment);
	
	/** Assigns a scene in a type safe matter, since the scene's type must match this state's scene type.
	 * <p>
	 * Returns whether the scene was actually assigned or not.
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
