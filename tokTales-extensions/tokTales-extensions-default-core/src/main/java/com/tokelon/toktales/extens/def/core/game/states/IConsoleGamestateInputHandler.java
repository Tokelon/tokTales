package com.tokelon.toktales.extens.def.core.game.states;

import com.tokelon.toktales.core.game.states.IGameStateInputHandler;

public interface IConsoleGamestateInputHandler extends IGameStateInputHandler {

	
	public interface IConsoleGamestateInputHandlerFactory {
		
		public IConsoleGamestateInputHandler create(IConsoleGamestate gamestate);
	}
	
}
