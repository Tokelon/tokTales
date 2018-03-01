package com.tokelon.toktales.extens.def.core.game.states;

import com.tokelon.toktales.core.game.controller.IConsoleController;
import com.tokelon.toktales.core.game.states.IGameState;
import com.tokelon.toktales.extens.def.core.game.logic.IConsoleInterpreter;

public interface IConsoleGamestate extends IGameState {
	// TODO: Refactor with control handler - also make injection simpler!
	
	
	public IConsoleController getConsoleController();
	
	
	public interface IConsoleGamestateInterpreterFactory {
		public IConsoleInterpreter create(IConsoleGamestate gamestate);
	}
	
}
