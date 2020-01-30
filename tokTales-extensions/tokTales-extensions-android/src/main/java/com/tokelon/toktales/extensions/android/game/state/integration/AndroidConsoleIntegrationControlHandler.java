package com.tokelon.toktales.extensions.android.game.state.integration;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import com.tokelon.toktales.core.engine.EngineException;
import com.tokelon.toktales.core.engine.log.ILogger;
import com.tokelon.toktales.core.engine.ui.IConsoleUIExtension;
import com.tokelon.toktales.core.engine.ui.IUIService;
import com.tokelon.toktales.extensions.core.game.state.integration.ConsoleIntegrationControlHandler;
import com.tokelon.toktales.extensions.core.game.state.integration.IConsoleIntegration;

public class AndroidConsoleIntegrationControlHandler extends ConsoleIntegrationControlHandler {


	private final ILogger logger;
	
	@Inject
	public AndroidConsoleIntegrationControlHandler(@Assisted IConsoleIntegration consoleIntegration) {
		super(consoleIntegration);
		
		logger = consoleIntegration.getGamestate().getLogging().getLogger(getClass());
	}
	
	
	@Override
	public boolean handleConsoleToggle() {
		super.handleConsoleToggle();

		if(isConsoleOpen()) {
			IUIService uiService = getConsoleIntegration().getGamestate().getEngine().getUIService();
			
			try {
				uiService.getExtensionByTypeOrFail(IConsoleUIExtension.class).openConsoleInput(getConsoleController());
			} catch (EngineException e) {
				logger.error("Error opening console input:", e);
			}
		}
		
		return true;
	}
	
}
