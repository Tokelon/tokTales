package com.tokelon.toktales.extens.def.desktop.game.states.consover;

import com.tokelon.toktales.core.game.states.IControlScheme;
import com.tokelon.toktales.desktop.input.TInput;
import com.tokelon.toktales.extens.def.core.game.states.consover.IConsoleOverlayControlHandler;

public class DesktopConsoleOverlayControlScheme implements IControlScheme {

	
	@Override
	public String map(int vk) {
		switch (vk) {
		case TInput.VK_ENTER:
			return IConsoleOverlayControlHandler.CONSOLE_ENTER;
		case TInput.VK_BACKSPACE:
			return IConsoleOverlayControlHandler.CONSOLE_DELETE;
		case TInput.VK_ESCAPE:
			return IConsoleOverlayControlHandler.CONSOLE_CLEAR;
		case TInput.VK_GRAVE_ACCENT:
			return IConsoleOverlayControlHandler.CONSOLE_TOGGLE;
		}
		
		return UNMAPPED;
	}
	
	@Override
	public String interpret(int vk, int action) {
		return UNRESOLVED;
	}

}
