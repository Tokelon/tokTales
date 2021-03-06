package com.tokelon.toktales.extensions.desktop.game.state.integration;

import com.tokelon.toktales.core.game.state.IControlScheme;
import com.tokelon.toktales.core.game.state.IGameStateInputHandler;
import com.tokelon.toktales.desktop.input.TInput;
import com.tokelon.toktales.desktop.input.dispatch.IDesktopInputRegistration.ICharInputCallback;
import com.tokelon.toktales.desktop.input.dispatch.IDesktopInputRegistration.IKeyInputCallback;
import com.tokelon.toktales.desktop.input.events.ICharInputEvent;
import com.tokelon.toktales.desktop.input.events.IKeyInputEvent;
import com.tokelon.toktales.extensions.core.game.state.integration.IConsoleIntegration;
import com.tokelon.toktales.extensions.core.game.state.integration.IConsoleIntegrationControlHandler;
import com.tokelon.toktales.tools.core.inject.ISupplier;

public class DesktopConsoleIntegrationInputHandler implements IGameStateInputHandler, ICharInputCallback, IKeyInputCallback {

	
	private final ISupplier<IConsoleIntegration> consoleIntegrationSupplier;

	public DesktopConsoleIntegrationInputHandler(ISupplier<IConsoleIntegration> consoleIntegrationSupplier) {
		this.consoleIntegrationSupplier = consoleIntegrationSupplier;
	}


	@Override
	public boolean handleKeyInput(IKeyInputEvent event) {
		IControlScheme controlScheme = consoleIntegrationSupplier.get().getGamestate().getStateControlScheme();
		IConsoleIntegrationControlHandler controlHandler = consoleIntegrationSupplier.get().getConsoleControlHandler();
		
		String ca = controlScheme.map(event.getKey());
		
		boolean handled = true;
		if(event.getAction() == TInput.KEY_PRESS) {
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
		else if(event.getAction() == TInput.KEY_RELEASE) {
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
	public boolean handleCharInput(ICharInputEvent event) {
		return consoleIntegrationSupplier.get().getConsoleControlHandler().handleConsoleInput(event.getCodepoint());
	}
	
}
