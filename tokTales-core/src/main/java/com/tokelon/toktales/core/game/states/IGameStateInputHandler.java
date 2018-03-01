package com.tokelon.toktales.core.game.states;

import com.tokelon.toktales.core.engine.input.IInputCallback;

/** Receives input and processes so it gets used by a gamestate.
 * <br><br>
 * Normally uses {@link IControlScheme} and {@link IControlHandler} of the gamestate.
 */
public interface IGameStateInputHandler extends IInputCallback {
	
	
	/** Registers this input handler to the given state input.
	 * <p>
	 * The default implementation for this simply calls {@link IGameStateInput#registerInputCallback(IInputCallback)},
	 * and returns the result.
	 * 
	 * @param stateInput
	 * @return True if registration was successful, false if not.
	 */
	public default boolean register(IGameStateInput stateInput) {
		return stateInput.registerInputCallback(this);
	}
	
	
	// could add generic version here -> let's not because then all implementation would have to support it
	//public boolean invoke(String target, String action, Object... params);
	
	
	public static class EmptyGameStateInputHandler implements IGameStateInputHandler { }
	
}
