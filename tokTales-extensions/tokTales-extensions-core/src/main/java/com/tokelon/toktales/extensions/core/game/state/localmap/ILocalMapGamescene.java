package com.tokelon.toktales.extensions.core.game.state.localmap;

import com.tokelon.toktales.core.game.state.scene.IExtendedGameScene;
import com.tokelon.toktales.extensions.core.game.logic.IConsoleInterpreter;

public interface ILocalMapGamescene extends IExtendedGameScene {
	
	
	/* Handling control for scenes
	 * 
	 * Like here implemented, the state has it's own control handler which is called before state control handler.
	 */
	
	@Override
	public ILocalMapControlHandler getSceneControlHandler();

	
	/**
	 * @return The scene console interpreter, or null if there is none.
	 */
	public IConsoleInterpreter getSceneConsoleInterpreter(); // Change to ILocalMapConsoleInterpreter?
	
}
