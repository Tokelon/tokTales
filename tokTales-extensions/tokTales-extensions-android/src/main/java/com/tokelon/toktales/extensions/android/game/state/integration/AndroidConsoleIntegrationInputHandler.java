package com.tokelon.toktales.extensions.android.game.state.integration;

import com.tokelon.toktales.android.input.TokelonTypeAInputs;
import com.tokelon.toktales.android.input.dispatch.IAndroidInputRegistration.IScreenButtonCallback;
import com.tokelon.toktales.android.input.events.IScreenButtonInputEvent;
import com.tokelon.toktales.core.game.state.IControlScheme;
import com.tokelon.toktales.extensions.core.game.state.integration.IConsoleIntegration;
import com.tokelon.toktales.extensions.core.game.state.integration.IConsoleIntegrationControlHandler;
import com.tokelon.toktales.extensions.core.game.state.localmap.ILocalMapInputHandler;
import com.tokelon.toktales.tools.core.inject.ISupplier;

public class AndroidConsoleIntegrationInputHandler implements ILocalMapInputHandler, IScreenButtonCallback {

	
	private final ISupplier<IConsoleIntegration> consoleIntegrationSupplier;

	public AndroidConsoleIntegrationInputHandler(ISupplier<IConsoleIntegration> consoleIntegrationSupplier) {
		this.consoleIntegrationSupplier = consoleIntegrationSupplier;
	}

	
	@Override
	public boolean handleScreenButtonInput(IScreenButtonInputEvent event) {
		IControlScheme controlScheme = consoleIntegrationSupplier.get().getGamestate().getStateControlScheme();
		IConsoleIntegrationControlHandler controlHandler = consoleIntegrationSupplier.get().getConsoleControlHandler();
		
		String ca = controlScheme.map(event.getButton());
		
		boolean handled = true;
		if(IConsoleIntegrationControlHandler.CONSOLE_TOGGLE.equals(ca) && event.getAction() == TokelonTypeAInputs.BUTTON_PRESS) {
			controlHandler.handleConsoleToggle(); // TODO: Does not work for some reason -> does it still not?
		}
		else {
			handled = false;
		}
		
		return handled;
	}
	
}
