package com.tokelon.toktales.extens.def.core.game.logic;

import com.tokelon.toktales.core.game.model.IConsole;

public interface IConsoleInterpreter {
	
	
	/** Attempt to interpret the given input.
	 * 
	 * @param console
	 * @param input
	 * @return True if the input was consumed, false if not.
	 */
	public boolean interpret(IConsole console, String input);
	
}
