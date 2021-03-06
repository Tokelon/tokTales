package com.tokelon.toktales.extensions.android.game.state.integration;

import com.tokelon.toktales.android.input.TokelonTypeAInputs;
import com.tokelon.toktales.core.game.state.IControlScheme;
import com.tokelon.toktales.extensions.core.game.state.integration.IConsoleIntegrationControlHandler;

public class AndroidConsoleIntegrationControlScheme implements IControlScheme {

	
	@Override
	public String map(int vk) {
		switch(vk) {
		case TokelonTypeAInputs.VB_SP1:
			return IConsoleIntegrationControlHandler.CONSOLE_TOGGLE;
		}
		
		return UNMAPPED;
	}

	@Override
	public String interpret(int vk, int action) {
		return UNRESOLVED;
	}

}
