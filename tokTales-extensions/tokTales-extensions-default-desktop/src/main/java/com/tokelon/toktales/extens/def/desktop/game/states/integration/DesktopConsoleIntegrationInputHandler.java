package com.tokelon.toktales.extens.def.desktop.game.states.integration;

import com.tokelon.toktales.core.game.states.IControlScheme;
import com.tokelon.toktales.core.game.states.IGameStateInputHandler;
import com.tokelon.toktales.core.util.function.Supplier;
import com.tokelon.toktales.desktop.input.IDesktopInputRegistration.ICharInputCallback;
import com.tokelon.toktales.desktop.input.IDesktopInputRegistration.IKeyInputCallback;
import com.tokelon.toktales.desktop.input.TInput;
import com.tokelon.toktales.extens.def.core.game.states.integration.IConsoleIntegration;
import com.tokelon.toktales.extens.def.core.game.states.integration.IConsoleIntegrationControlHandler;

public class DesktopConsoleIntegrationInputHandler implements IGameStateInputHandler, ICharInputCallback, IKeyInputCallback {

	
	private final Supplier<IConsoleIntegration> consoleIntegrationSupplier;

	public DesktopConsoleIntegrationInputHandler(Supplier<IConsoleIntegration> consoleIntegrationSupplier) {
		this.consoleIntegrationSupplier = consoleIntegrationSupplier;
	}
	
	
	@Override
	public boolean invokeKeyInput(int vk, int action) {
		IControlScheme controlScheme = consoleIntegrationSupplier.get().getGamestate().getStateControlScheme();
		IConsoleIntegrationControlHandler controlHandler = consoleIntegrationSupplier.get().getConsoleControlHandler();
		
		String ca = controlScheme.map(vk);
		
		boolean handled = true;
		if(action == TInput.KEY_PRESS) {
			switch (ca) {
			case IConsoleIntegrationControlHandler.CONSOLE_ENTER:
				controlHandler.handleConsoleEnter();
				break;
			case IConsoleIntegrationControlHandler.CONSOLE_DELETE:
				controlHandler.handleConsoleDelete();
				break;
			case IConsoleIntegrationControlHandler.CONSOLE_CLEAR:
				controlHandler.handleConsoleClear();
				break;
			default:
				handled = false;
				break;
			}
		}
		else if(action == TInput.KEY_RELEASE) {
			switch (ca) {
			case IConsoleIntegrationControlHandler.CONSOLE_TOGGLE:
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
		return consoleIntegrationSupplier.get().getConsoleControlHandler().handleConsoleInput(codepoint);
	}
	
}
