package com.tokelon.toktales.test.core.game.states.enginestate;

import com.tokelon.toktales.core.game.states.IGameStateInputHandler;

public interface IEngineGamestateInputHandler extends IGameStateInputHandler {
	
	
	public interface IEngineGamestateInputHandlerFactory {
		
		public IEngineGamestateInputHandler create(IEngineGamestate gamestate);
	}

}
