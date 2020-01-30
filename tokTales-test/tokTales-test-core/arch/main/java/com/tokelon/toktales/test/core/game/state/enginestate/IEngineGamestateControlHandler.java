package com.tokelon.toktales.test.core.game.state.enginestate;

import com.tokelon.toktales.core.game.state.IControlHandler;

public interface IEngineGamestateControlHandler extends IControlHandler {

	
	public interface IEngineGamestateControlHandlerFactory {
		
		public IEngineGamestateControlHandler create(IEngineGamestate gamestate);
	}
	
}
