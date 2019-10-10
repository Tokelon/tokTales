package com.tokelon.toktales.extens.def.android.states.integration;

import com.tokelon.toktales.android.input.TokelonTypeAInputs;
import com.tokelon.toktales.android.input.dispatch.IAndroidInputRegistration.IScreenButtonCallback;
import com.tokelon.toktales.android.input.events.IScreenButtonInputEvent;
import com.tokelon.toktales.core.engine.inject.ISupplier;
import com.tokelon.toktales.core.game.states.IControlScheme;
import com.tokelon.toktales.extens.def.core.game.states.integration.IConsoleIntegration;
import com.tokelon.toktales.extens.def.core.game.states.integration.IConsoleIntegrationControlHandler;
import com.tokelon.toktales.extens.def.core.game.states.localmap.ILocalMapInputHandler;

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
