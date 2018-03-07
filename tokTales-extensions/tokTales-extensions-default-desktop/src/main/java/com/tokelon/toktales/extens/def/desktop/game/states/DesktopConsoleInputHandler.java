package com.tokelon.toktales.extens.def.desktop.game.states;

import com.tokelon.toktales.core.game.states.IGameStateInputHandler;
import com.tokelon.toktales.core.game.states.InjectGameState;
import com.tokelon.toktales.desktop.input.IDesktopInputRegistration.ICharInputCallback;
import com.tokelon.toktales.desktop.input.IDesktopInputRegistration.IKeyInputCallback;
import com.tokelon.toktales.desktop.input.TInput;
import com.tokelon.toktales.extens.def.core.game.states.IConsoleGamestate;

@InjectGameState
public class DesktopConsoleInputHandler implements IGameStateInputHandler, ICharInputCallback, IKeyInputCallback {
	
	
	private IConsoleGamestate consoleGamestate;
	
	@InjectGameState
	protected void passGamestate(IConsoleGamestate gamestate) {
		this.consoleGamestate = gamestate;
	}
	
	
	@Override
	public boolean invokeCharInput(int codepoint) {
		// if(consoleController.isConsoleOpen())	// Implement?
		
		consoleGamestate.getConsoleController().inputCodepoint(codepoint);
		return true;
	}


	@Override
	public boolean invokeKeyInput(int vk, int action) {
		boolean handled = true;
		
		if(action == TInput.KEY_PRESS) {
			
			switch (vk) {
			case TInput.VK_ENTER:
				consoleGamestate.getConsoleController().enter();
				break;
			case TInput.VK_BACKSPACE:
				consoleGamestate.getConsoleController().backspace();
				break;
			default:
				handled = false;
				break;
			}
		}
		
		return handled;
	}
	
}
