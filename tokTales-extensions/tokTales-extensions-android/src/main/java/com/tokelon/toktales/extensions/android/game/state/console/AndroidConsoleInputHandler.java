package com.tokelon.toktales.extensions.android.game.state.console;

import com.tokelon.toktales.android.input.TokelonTypeAInputs;
import com.tokelon.toktales.android.input.dispatch.IAndroidInputRegistration.IScreenButtonCallback;
import com.tokelon.toktales.android.input.events.IScreenButtonInputEvent;
import com.tokelon.toktales.core.engine.EngineException;
import com.tokelon.toktales.core.engine.log.ILogger;
import com.tokelon.toktales.core.engine.ui.IConsoleUIExtension;
import com.tokelon.toktales.core.engine.ui.IDebugUIExtension;
import com.tokelon.toktales.core.engine.ui.IUIService;
import com.tokelon.toktales.core.game.state.IGameStateInputHandler;
import com.tokelon.toktales.core.game.state.InjectGameState;
import com.tokelon.toktales.extensions.core.game.state.console.IConsoleGamestate;

@InjectGameState
public class AndroidConsoleInputHandler implements IGameStateInputHandler, IScreenButtonCallback {


	private IConsoleGamestate consoleGamestate;
	private ILogger logger;
	
	@InjectGameState
	protected void passGamestate(IConsoleGamestate gamestate) {
		this.consoleGamestate = gamestate;
		this.logger = gamestate.getLogging().getLogger(getClass());
	}

	
	@Override
	public boolean handleScreenButtonInput(IScreenButtonInputEvent event) {
		boolean handled = true;
		
		if(event.getAction() == TokelonTypeAInputs.BUTTON_PRESS) {
			switch (event.getButton()) {
			case TokelonTypeAInputs.VB_SP1:
				buttonSP1Pressed();
				break;
			case TokelonTypeAInputs.VB_SET:
				buttonSETPressed();
				break;
			default:
				handled = false;
				break;
			}
		}
		else if(event.getAction() == TokelonTypeAInputs.BUTTON_RELEASE) {
			// Nothing yet
		}
		else {
			handled = false;
		}
		
		return handled;
	}
	

	private void buttonSP1Pressed() {
		IUIService uiService = consoleGamestate.getEngine().getUIService();
		
		try {
			uiService.getExtensionByTypeOrFail(IConsoleUIExtension.class).openConsoleInput(consoleGamestate.getConsoleController());
		} catch (EngineException e) {
			logger.error("Error opening console input:", e);
		}
	}
	
	private void buttonSETPressed() {
		IUIService uiService = consoleGamestate.getEngine().getUIService();
		
		try {
			uiService.getExtensionByTypeOrFail(IDebugUIExtension.class).openContextMenu();
		} catch (EngineException e) {
			logger.error("Error opening context menu:", e);
		}
	}

}
