package com.tokelon.toktales.extens.def.desktop.game.states;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import com.tokelon.toktales.desktop.input.IDesktopInputRegistration.ICharInputCallback;
import com.tokelon.toktales.desktop.input.IDesktopInputRegistration.IKeyInputCallback;
import com.tokelon.toktales.desktop.input.TInput;
import com.tokelon.toktales.extens.def.core.game.states.IConsoleGamestate;
import com.tokelon.toktales.extens.def.core.game.states.IConsoleGamestateInputHandler;

public class DesktopConsoleInputHandler implements IConsoleGamestateInputHandler, ICharInputCallback, IKeyInputCallback {
	
	
	private final IConsoleGamestate consoleGamestate;

	@Inject
	public DesktopConsoleInputHandler(@Assisted IConsoleGamestate consoleGamestate) {
		this.consoleGamestate = consoleGamestate;
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
