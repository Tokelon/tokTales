package com.tokelon.toktales.extens.def.core.game.logic;

import com.tokelon.toktales.core.game.model.IConsole;

public interface IConsoleInterpreter {
	
	// TODO: Return boolean for extension purposes or even string?
	public boolean interpret(IConsole console, String input);
	
}
