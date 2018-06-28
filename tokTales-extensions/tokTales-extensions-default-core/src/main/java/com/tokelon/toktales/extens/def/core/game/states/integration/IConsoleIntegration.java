package com.tokelon.toktales.extens.def.core.game.states.integration;

import com.tokelon.toktales.core.game.controller.IConsoleController;
import com.tokelon.toktales.core.game.states.IGameState;
import com.tokelon.toktales.core.game.states.integration.IGameStateIntegration;
import com.tokelon.toktales.extens.def.core.game.states.consover.IConsoleOverlayControlHandler;

public interface IConsoleIntegration extends IGameStateIntegration {
	
	
	public IGameState getGamestate();
	
	public IConsoleController getConsoleController();
	
	public IConsoleOverlayControlHandler getConsoleOverlayControlHandler();
	
}
