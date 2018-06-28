package com.tokelon.toktales.extens.def.core.game.states.integration;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import com.tokelon.toktales.core.game.controller.IConsoleController;

public class ConsoleIntegrationControlHandler implements IConsoleIntegrationControlHandler {


	private IConsoleIntegration consoleIntegration;

	@Inject
	public ConsoleIntegrationControlHandler(@Assisted IConsoleIntegration consoleIntegration) {
		this.consoleIntegration = consoleIntegration;
	}

	
	protected IConsoleIntegration getConsoleIntegration() {
		return consoleIntegration;
	}

	protected IConsoleController getConsoleController() {
		return consoleIntegration.getConsoleController();
	}
	

	@Override
	public boolean handleAction(String target, String action, Object... params) {
		return false;
	}

	
	@Override
	public boolean isConsoleOpen() {
		return getConsoleController().isConsoleOpen();
	}
	
	
	@Override
	public boolean handleConsoleEnter() {
		if(getConsoleController().isConsoleOpen()) {
			getConsoleController().enter();
			return true;
		}
		
		return false;
	}

	@Override
	public boolean handleConsoleDelete() {
		if(getConsoleController().isConsoleOpen()) {
			getConsoleController().backspace();
			return true;
		}
		
		return false;
	}

	@Override
	public boolean handleConsoleClear() {
		if(getConsoleController().isConsoleOpen()) {
			getConsoleController().clear();
			return true;
		}
		
		return false;
	}

	@Override
	public boolean handleConsoleToggle() {
		getConsoleController().toggleConsole();
		return true;
	}
	
	@Override
	public boolean handleConsoleInput(int codepoint) {
		if(getConsoleController().isConsoleOpen()) {
			getConsoleController().inputCodepoint(codepoint);
			return true;
		}

		return false;
	}
	
}
