package com.tokelon.toktales.test.core.game.state.enginestate;

import com.tokelon.toktales.core.game.state.IGameStateInputHandler;

public interface IEngineGamestateInputHandler extends IGameStateInputHandler {
	
	
	public interface IEngineGamestateInputHandlerFactory {
		
		public IEngineGamestateInputHandler create(IEngineGamestate gamestate);
	}

}
