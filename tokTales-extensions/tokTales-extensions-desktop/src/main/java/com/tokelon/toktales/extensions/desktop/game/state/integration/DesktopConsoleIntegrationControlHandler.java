package com.tokelon.toktales.extensions.desktop.game.state.integration;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import com.tokelon.toktales.extensions.core.game.state.integration.ConsoleIntegrationControlHandler;
import com.tokelon.toktales.extensions.core.game.state.integration.IConsoleIntegration;

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
