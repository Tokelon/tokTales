package com.tokelon.toktales.extens.def.desktop.game.states.integration;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import com.tokelon.toktales.extens.def.core.game.states.integration.ConsoleIntegrationControlHandler;
import com.tokelon.toktales.extens.def.core.game.states.integration.IConsoleIntegration;

public class DesktopConsoleIntegrationControlHandler extends ConsoleIntegrationControlHandler {

	
	@Inject
	public DesktopConsoleIntegrationControlHandler(@Assisted IConsoleIntegration consoleIntegration) {
		super(consoleIntegration);
	}
	

	@Override
	public boolean handleConsoleToggle() {
		if(getConsoleController().isConsoleOpen()) {
			getConsoleController().backspace();		// TODO: Fix that we have to remove the last pressed char because there is not stopping key events
		}
		
		super.handleConsoleToggle();
		return true;
	}
	
}
