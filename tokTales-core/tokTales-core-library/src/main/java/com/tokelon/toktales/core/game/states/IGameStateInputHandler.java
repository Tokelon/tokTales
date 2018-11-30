package com.tokelon.toktales.core.game.states;

import com.tokelon.toktales.core.engine.input.IInputCallback;
import com.tokelon.toktales.core.engine.input.IInputEvent;

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
	
	
	public static class EmptyGameStateInputHandler implements IGameStateInputHandler {
		
		@Override
		public boolean handle(IInputEvent event) {
			return false;
		}
	}
	
}
