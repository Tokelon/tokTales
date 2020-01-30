package com.tokelon.toktales.extensions.core.game.state.integration;

import com.tokelon.toktales.core.game.controller.IConsoleController;
import com.tokelon.toktales.core.game.state.IGameState;
import com.tokelon.toktales.core.game.state.integration.IGameStateIntegration;

public interface IConsoleIntegration extends IGameStateIntegration {
	
	
	public IGameState getGamestate();
	
	public IConsoleController getConsoleController();
	
	public IConsoleIntegrationControlHandler getConsoleControlHandler();
	
}
