package com.tokelon.toktales.extens.def.core.game.states.console;

import com.tokelon.toktales.core.game.controller.IConsoleController;
import com.tokelon.toktales.core.game.states.IGameState;
import com.tokelon.toktales.extens.def.core.game.logic.IConsoleInterpreter;
import com.tokelon.toktales.extens.def.core.game.logic.IConsoleInterpreterManager;

public interface IConsoleGamestate extends IGameState {
	
	
	public IConsoleInterpreterManager getInterpreterManager();
	
	public void addInterpreterAndInjectState(IConsoleInterpreter interpreter);
	public void addInterpreterAndInjectState(IConsoleInterpreter interpreter, int index);

	
	public IConsoleController getConsoleController();
	
}
