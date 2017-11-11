package com.tokelon.toktales.extens.def.desktop.desktop.game.states.consover;

import com.tokelon.toktales.desktop.input.IDesktopInputRegistration.ICharInputCallback;
import com.tokelon.toktales.desktop.input.IDesktopInputRegistration.IKeyInputCallback;
import com.tokelon.toktales.extens.def.core.game.states.consover.IConsoleOverlayControlHandler;
import com.tokelon.toktales.extens.def.core.game.states.consover.IConsoleOverlayGamestate;
import com.tokelon.toktales.core.game.states.IGameStateInputHandler;
import com.tokelon.toktales.desktop.input.TInput;

public class DesktopConsoleOverlayInputHandler implements IGameStateInputHandler, ICharInputCallback, IKeyInputCallback {

	
	private final IConsoleOverlayGamestate gamestate;
	
	public DesktopConsoleOverlayInputHandler(IConsoleOverlayGamestate gamestate) {
		this.gamestate = gamestate;
	}
	
	
	@Override
	public boolean invokeKeyInput(int vk, int action) {
		IConsoleOverlayControlHandler controlHandler = gamestate.getStateControlHandler();
		
		String ca = gamestate.getStateControlScheme().map(vk);
		
		boolean handled = true;
		if(action == TInput.KEY_PRESS) {
			switch (ca) {
			case IConsoleOverlayControlHandler.CONSOLE_ENTER:
				controlHandler.handleConsoleEnter();
				break;
			case IConsoleOverlayControlHandler.CONSOLE_DELETE:
				controlHandler.handleConsoleDelete();
				break;
			case IConsoleOverlayControlHandler.CONSOLE_CLEAR:
				controlHandler.handleConsoleClear();
				break;
			default:
				handled = false;
				break;
			}
		}
		else if(action == TInput.KEY_RELEASE) {
			switch (ca) {
			case IConsoleOverlayControlHandler.CONSOLE_TOGGLE:
				controlHandler.handleConsoleToggle();
				break;
			default:
				handled = false;
				break;
			}
		}
		else {
			handled = false;
		}
		
		
		if(!handled && controlHandler.isConsoleOpen()) {
			// Consume key events (by the console)
			return true;
		}
		
		return handled;
	}

	@Override
	public boolean invokeCharInput(int codepoint) {
		return gamestate.getStateControlHandler().handleConsoleInput(codepoint);
	}
	
}
