package com.tokelon.toktales.extens.def.android.states.integration;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import com.tokelon.toktales.core.engine.EngineException;
import com.tokelon.toktales.core.engine.ui.IConsoleUIExtension;
import com.tokelon.toktales.core.engine.ui.IUIService;
import com.tokelon.toktales.extens.def.core.game.states.integration.ConsoleIntegrationControlHandler;
import com.tokelon.toktales.extens.def.core.game.states.integration.IConsoleIntegration;

public class AndroidConsoleIntegrationControlHandler extends ConsoleIntegrationControlHandler {

	public static final String TAG = "AndroidConsoleIntegrationControlHandler";
	
	
	@Inject
	public AndroidConsoleIntegrationControlHandler(@Assisted IConsoleIntegration consoleIntegration) {
		super(consoleIntegration);
	}
	
	
	@Override
	public boolean handleConsoleToggle() {
		super.handleConsoleToggle();

		if(isConsoleOpen()) {
			IUIService uiService = getConsoleIntegration().getGamestate().getEngine().getUIService();
			
			try {
				uiService.getExtensionByTypeOrFail(IConsoleUIExtension.class).openConsoleInput(getConsoleController());
			} catch (EngineException e) {
				getConsoleIntegration().getGamestate().getLog().e(TAG, "Error opening console input: " + e.getMessage());
			}
		}
		
		return true;
	}
	
}
