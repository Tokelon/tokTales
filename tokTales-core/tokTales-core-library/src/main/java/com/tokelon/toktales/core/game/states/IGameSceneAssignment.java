package com.tokelon.toktales.core.game.states;

import java.lang.reflect.Type;

/** Used for assigning a scene to a state in a type safe way. 
 */
public interface IGameSceneAssignment {
	// Could add generic type for scene if the need arises
	
	
	/**
	 * @return The scene for this assignment.
	 */
	public IGameScene getScene();

	/**
	 * @return The scene type for this assignment.
	 */
	public Type getSceneType();
	
	
	/** Returns whether the scene can be safely casted into the given type.
	 * 
	 * @param type
	 * @return True if the scene is of the given type, false if not.
	 */
	public boolean isSceneOfType(Type type);
	
}
