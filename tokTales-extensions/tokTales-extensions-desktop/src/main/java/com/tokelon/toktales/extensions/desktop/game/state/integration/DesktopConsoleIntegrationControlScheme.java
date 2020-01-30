package com.tokelon.toktales.extensions.desktop.game.state.integration;

import com.tokelon.toktales.core.game.state.IControlScheme;
import com.tokelon.toktales.desktop.input.TInput;
import com.tokelon.toktales.extensions.core.game.state.integration.IConsoleIntegrationControlHandler;

public class DesktopConsoleIntegrationControlScheme implements IControlScheme {

	
	@Override
	public String map(int vk) {
		switch (vk) {
		case TInput.VK_ENTER:
			return IConsoleIntegrationControlHandler.CONSOLE_ENTER;
		case TInput.VK_BACKSPACE:
			return IConsoleIntegrationControlHandler.CONSOLE_DELETE;
		case TInput.VK_ESCAPE:
			return IConsoleIntegrationControlHandler.CONSOLE_CLEAR;
		case TInput.VK_GRAVE_ACCENT:
			return IConsoleIntegrationControlHandler.CONSOLE_TOGGLE;
		}
		
		return UNMAPPED;
	}
	
	@Override
	public String interpret(int vk, int action) {
		return UNRESOLVED;
	}

}
