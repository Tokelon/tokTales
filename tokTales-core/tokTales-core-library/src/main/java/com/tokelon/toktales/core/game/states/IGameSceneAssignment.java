package com.tokelon.toktales.core.game.states;

import java.lang.reflect.Type;

/** Used for assigning a scene to a state with custom logic.
 */
public interface IGameSceneAssignment {
	
	
	/**
	 * @return The scene for this assignment.
	 */
	public IGameScene getScene();

	/**
	 * @return The scene type for this assignment.
	 */
	public Type getSceneType();
	
	
	/** Returns whether the scene can be safely cast into the given type.
	 * 
	 * @param type
	 * @return True if the scene is of the given type, false if not.
	 */
	public boolean isSceneOfType(Type type);
	
}
