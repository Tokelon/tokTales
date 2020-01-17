package com.tokelon.toktales.extensions.core.game.states.console;

import com.tokelon.toktales.core.game.model.IConsole;
import com.tokelon.toktales.extensions.core.game.logic.IConsoleInterpreter;

public class ConsoleGamestateInterpreter implements IConsoleInterpreter {
	
	
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
		else {
			response = "I did not understand that.";
		}
		
		console.print(response);
		return false;
	}

}
