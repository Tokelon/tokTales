package com.tokelon.toktales.core.game.state;

import java.lang.reflect.Type;

import com.google.common.reflect.TypeToken;

/** Implementation of {@link IGameSceneAssignment} using TypeToken.<br>
 * Bundles a scene and it's type together in a type safe way that respects generic types.
 * <p>
 * This class is intended to be instantiated as an anonymous subclass so it keeps it's type information at runtime.
 * <p>
 * An example for that (note the curly braces at the end):<br>
 * <code>GameSceneAssignment{@literal<}IGenericGamescene{@literal<}String{@literal>}{@literal>} assignment = new GameSceneAssignment{@literal<}IGenericGamescene{@literal<}String{@literal>}{@literal>}() { };</code>
 * 
 * @param <T> The scene type.
 */
public class GenericGamesceneAssignment<T extends IGameScene> implements IGameSceneAssignment {
	
	
	private final TypeToken<T> typeToken;
	private final T gamescene;

	protected GenericGamesceneAssignment(T gamescene) {
		this.typeToken = createTypeToken();
		this.gamescene = gamescene;
	}
	
	private GenericGamesceneAssignment(T gamescene, TypeToken<T> sceneTypeToken) {
		this.typeToken = sceneTypeToken;
		this.gamescene = gamescene;
	}
	
	@SuppressWarnings("serial")
	protected TypeToken<T> createTypeToken() {
		return new TypeToken<T>(getClass()) {};
	}
	
	
	@Override
	public T getScene() {
		return gamescene;
	}
	
	@Override
	public Type getSceneType() {
		return typeToken.getType();
	}
	
	@Override
	public boolean isSceneOfType(Type type) {
		return typeToken.isSubtypeOf(type);
	}
	
	
	public TypeToken<T> getSceneTypeToken() {
		return typeToken;
	}
	
	
	/** Creates a new scene assignment with the given scene and scene type.
	 * 
	 * @param gamescene
	 * @param sceneTypeToken
	 * @return A new scene assignment of the given parameters.
	 */
	public static <T extends IGameScene> GenericGamesceneAssignment<T> of(T gamescene, TypeToken<T> sceneTypeToken) {
		return new GenericGamesceneAssignment<T>(gamescene, sceneTypeToken);
	}

}
