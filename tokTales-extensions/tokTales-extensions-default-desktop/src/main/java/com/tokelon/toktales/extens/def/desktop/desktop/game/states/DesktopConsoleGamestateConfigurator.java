package com.tokelon.toktales.extens.def.desktop.desktop.game.states;

import com.tokelon.toktales.core.game.states.IGameStateConfigurator.AbstractGamestateConfigurator;
import com.tokelon.toktales.desktop.game.states.DesktopGameStateInput;
import com.tokelon.toktales.desktop.game.states.IDesktopGameStateInput;
import com.tokelon.toktales.extens.def.core.game.logic.IConsoleInterpreter;
import com.tokelon.toktales.extens.def.core.game.states.ConsoleGamestate;

public class DesktopConsoleGamestateConfigurator extends AbstractGamestateConfigurator<ConsoleGamestate> {

	@Override
	public void configureState(ConsoleGamestate gamestate) {
		IDesktopGameStateInput stateInput = createStateInput(gamestate); 
		gamestate.setStateInput(stateInput);
		
		DesktopConsoleInputHandler stateInputHandler = createStateInputHandler(gamestate);
		stateInput.registerCharInputCallback(stateInputHandler);
		stateInput.registerKeyInputCallback(stateInputHandler);
		
		gamestate.setCustomConsoleInterpreter(createConsoleInterpreter(gamestate));
	}

	
	public IConsoleInterpreter createConsoleInterpreter(ConsoleGamestate gamestate) {
		return new DesktopConsoleGamestateInterpreter(gamestate);
	}

	@Override
	public IDesktopGameStateInput createStateInput(ConsoleGamestate gamestate) {
		return new DesktopGameStateInput();
	}
	
	@Override
	public DesktopConsoleInputHandler createStateInputHandler(ConsoleGamestate gamestate) {
		return new DesktopConsoleInputHandler(gamestate);
	}

}
