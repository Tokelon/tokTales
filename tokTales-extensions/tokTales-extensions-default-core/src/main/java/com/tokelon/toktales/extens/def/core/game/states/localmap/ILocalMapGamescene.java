package com.tokelon.toktales.extens.def.core.game.states.localmap;

import com.tokelon.toktales.core.game.states.IGameScene;
import com.tokelon.toktales.extens.def.core.game.logic.IConsoleInterpreter;

public interface ILocalMapGamescene extends IGameScene {
	
	/* HANDLING CONTROL FOR SCENES
	 * 2 Ways to do this
	 * 
	 * 1. Like here implemented, the state has it's own control handler which is called before state control handler.
	 * 
	 * 2. Have the control handler be settable in the state, the scene could then implement a wrapper around it,
	 * and then set the wrapper as the main handler.
	 * -> LOL, nevermind - only one scene could set it's handler then!!
	 * 
	 */

	@Override
	public ILocalMapControlHandler getSceneControlHandler();

	
	/**
	 * @return The scene console interpreter, or null if there is none.
	 */
	public IConsoleInterpreter getSceneConsoleInterpreter();
	
	
	// TESTING
	//public void interact();

	
}
