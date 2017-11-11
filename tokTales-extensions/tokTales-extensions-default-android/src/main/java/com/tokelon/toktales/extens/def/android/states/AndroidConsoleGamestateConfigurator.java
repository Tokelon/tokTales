package com.tokelon.toktales.extens.def.android.states;

import com.tokelon.toktales.android.states.AndroidGameStateInput;
import com.tokelon.toktales.android.states.IAndroidGameStateInput;
import com.tokelon.toktales.core.game.states.IGameStateConfigurator.AbstractGamestateConfigurator;
import com.tokelon.toktales.extens.def.core.game.states.ConsoleGamestate;

public class AndroidConsoleGamestateConfigurator extends AbstractGamestateConfigurator<ConsoleGamestate> {

	
	@Override
	public void configureState(ConsoleGamestate gamestate) {
		IAndroidGameStateInput stateInput = createStateInput(gamestate);
		gamestate.setStateInput(stateInput);

		AndroidConsoleInputHandler stateInputHandler = createStateInputHandler(gamestate);
		gamestate.setStateInputHandler(stateInputHandler);
		
		stateInput.registerScreenButtonCallback(stateInputHandler);

		//input.setInputHandlerTypeC(new AndroidLocalMapInputHandler(getControllerManager()));
		
		// Make custom input for text through keyboard?
		//stateInput.
	}

	
	@Override
	public IAndroidGameStateInput createStateInput(ConsoleGamestate gamestate) {
		return new AndroidGameStateInput();
		
	}
	
	@Override
	public AndroidConsoleInputHandler createStateInputHandler(ConsoleGamestate gamestate) {
		return new AndroidConsoleInputHandler(gamestate);
	}

}
