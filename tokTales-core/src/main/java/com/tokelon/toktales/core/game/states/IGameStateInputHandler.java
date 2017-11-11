package com.tokelon.toktales.core.game.states;

/** Receives input and processes so it gets used by a gamestate.
 * <br><br>
 * Normally uses {@link IControlScheme} and {@link IControlHandler} of the gamestate.
 */
public interface IGameStateInputHandler {
	
	// would have to pass platform dependent input (or cast)
	//public void register(IGameStateInput gamestateInput);
	
	// could add generic version here -> let's not because then all implementation would have to support it
	//public boolean invoke(String target, String action, Object... params);
		
}
