package com.tokelon.toktales.extensions.core.game.states.integration;

import com.tokelon.toktales.core.game.controller.IConsoleController;
import com.tokelon.toktales.core.game.states.IGameState;
import com.tokelon.toktales.core.game.states.integration.IGameStateIntegration;

public interface IConsoleIntegration extends IGameStateIntegration {
	
	
	public IGameState getGamestate();
	
	public IConsoleController getConsoleController();
	
	public IConsoleIntegrationControlHandler getConsoleControlHandler();
	
}
