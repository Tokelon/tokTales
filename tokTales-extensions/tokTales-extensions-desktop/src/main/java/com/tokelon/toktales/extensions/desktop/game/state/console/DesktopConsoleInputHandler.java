package com.tokelon.toktales.extensions.desktop.game.state.console;

import com.tokelon.toktales.core.game.state.IGameStateInputHandler;
import com.tokelon.toktales.core.game.state.InjectGameState;
import com.tokelon.toktales.desktop.input.TInput;
import com.tokelon.toktales.desktop.input.dispatch.IDesktopInputRegistration.ICharInputCallback;
import com.tokelon.toktales.desktop.input.dispatch.IDesktopInputRegistration.IKeyInputCallback;
import com.tokelon.toktales.desktop.input.events.ICharInputEvent;
import com.tokelon.toktales.desktop.input.events.IKeyInputEvent;
import com.tokelon.toktales.extensions.core.game.state.console.IConsoleGamestate;

@InjectGameState
public class DesktopConsoleInputHandler implements IGameStateInputHandler, ICharInputCallback, IKeyInputCallback {
	
	
	private IConsoleGamestate consoleGamestate;
	
	@InjectGameState
	protected void passGamestate(IConsoleGamestate gamestate) {
		this.consoleGamestate = gamestate;
	}
	
	
	@Override
	public boolean handleCharInput(ICharInputEvent event) {
		// if(consoleController.isConsoleOpen())	// Implement?
		
		consoleGamestate.getConsoleController().inputCodepoint(event.getCodepoint());
		return true;
	}
	
	@Override
	public boolean handleKeyInput(IKeyInputEvent event) {
		boolean handled = true;
		
		if(event.getAction() == TInput.KEY_PRESS) {
			
			switch (event.getKey()) {
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
