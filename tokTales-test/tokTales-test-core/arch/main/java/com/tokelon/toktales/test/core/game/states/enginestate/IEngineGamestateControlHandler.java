package com.tokelon.toktales.test.core.game.states.enginestate;

import com.tokelon.toktales.core.game.states.IControlHandler;

public interface IEngineGamestateControlHandler extends IControlHandler {

	
	public interface IEngineGamestateControlHandlerFactory {
		
		public IEngineGamestateControlHandler create(IEngineGamestate gamestate);
	}
	
}
