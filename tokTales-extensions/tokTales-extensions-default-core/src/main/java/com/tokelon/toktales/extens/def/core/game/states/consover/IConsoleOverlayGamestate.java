package com.tokelon.toktales.extens.def.core.game.states.consover;

import com.tokelon.toktales.core.game.controller.IConsoleController;
import com.tokelon.toktales.core.game.states.IGameState;

public interface IConsoleOverlayGamestate extends IGameState {

	/** The console controller will be available after {@link #onEngage()} has been called.
	 * 
	 * @return The console controller.
	 */
	public IConsoleController getConsoleController();
	
	@Override
	public IConsoleOverlayControlHandler getStateControlHandler();
	
}
