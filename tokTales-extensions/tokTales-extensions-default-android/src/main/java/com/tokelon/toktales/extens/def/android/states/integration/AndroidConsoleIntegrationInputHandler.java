package com.tokelon.toktales.extens.def.android.states.integration;

import com.tokelon.toktales.android.input.IAndroidInputRegistration.IScreenButtonCallback;
import com.tokelon.toktales.android.input.TokelonTypeAInputs;
import com.tokelon.toktales.core.game.states.IControlScheme;
import com.tokelon.toktales.core.util.function.Supplier;
import com.tokelon.toktales.extens.def.core.game.states.integration.IConsoleIntegration;
import com.tokelon.toktales.extens.def.core.game.states.integration.IConsoleIntegrationControlHandler;
import com.tokelon.toktales.extens.def.core.game.states.localmap.ILocalMapInputHandler;

public class AndroidConsoleIntegrationInputHandler implements ILocalMapInputHandler, IScreenButtonCallback {

	
	private final Supplier<IConsoleIntegration> consoleIntegrationSupplier;

	public AndroidConsoleIntegrationInputHandler(Supplier<IConsoleIntegration> consoleIntegrationSupplier) {
		this.consoleIntegrationSupplier = consoleIntegrationSupplier;
	}

	
	@Override
	public boolean invokeScreenButton(int vb, int action) {
		IControlScheme controlScheme = consoleIntegrationSupplier.get().getGamestate().getStateControlScheme();
		IConsoleIntegrationControlHandler controlHandler = consoleIntegrationSupplier.get().getConsoleControlHandler();
		
		String ca = controlScheme.map(vb);
		
		boolean handled = true;
		if(IConsoleIntegrationControlHandler.CONSOLE_TOGGLE.equals(ca) && action == TokelonTypeAInputs.BUTTON_PRESS) {
			controlHandler.handleConsoleToggle(); // TODO: Does not work for some reason -> does it still not?
		}
		else {
			handled = false;
		}
		
		return handled;
	}
	
}
