package com.tokelon.toktales.extens.def.core.game.states.localmap;

import com.tokelon.toktales.core.game.states.IGameScene;
import com.tokelon.toktales.extens.def.core.game.logic.IConsoleInterpreter;

public interface ILocalMapGamescene extends IGameScene {
	
	
	/* Handling control for scenes
	 * 
	 * Like here implemented, the state has it's own control handler which is called before state control handler.
	 */
	
	@Override
	public ILocalMapControlHandler getSceneControlHandler();

	
	/**
	 * @return The scene console interpreter, or null if there is none.
	 */
	public IConsoleInterpreter getSceneConsoleInterpreter();
	
}
