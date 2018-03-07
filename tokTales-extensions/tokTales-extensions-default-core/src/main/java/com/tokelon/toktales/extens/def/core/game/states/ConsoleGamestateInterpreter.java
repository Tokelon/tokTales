package com.tokelon.toktales.extens.def.core.game.states;

import com.tokelon.toktales.core.game.model.IConsole;
import com.tokelon.toktales.core.game.states.InjectGameState;
import com.tokelon.toktales.extens.def.core.game.logic.IConsoleInterpreter;

@InjectGameState
public class ConsoleGamestateInterpreter implements IConsoleInterpreter {
	
	
	private IConsoleGamestate gamestate;
	
	@InjectGameState
	protected void injectGamestate(IConsoleGamestate gamestate) {
		this.gamestate = gamestate;
	}
	
	
	@Override
	public boolean interpret(IConsole console, String input) {
		String response;
		
		if(input.contains("Hello")) {
			response = "Hello!";
		}
		else if(input.toLowerCase().contains("load module map")) {
			response = "Not found";
			//response = "Loading...";
			
			//getGame().getStateControl().changeStateTo("main_menu_state");
		}
		else if(input.toLowerCase().contains("load chunk_test")) {
			response = "Loading...";
			
			gamestate.getGame().getStateControl().changeState("chunk_test_state");
		}
		else {
			response = "I did not understand that.";
		}
		
		console.print(response);
		return true;
	}

}
