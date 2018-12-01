package com.tokelon.toktales.extens.def.android.states.console;

import com.tokelon.toktales.android.input.TokelonTypeAInputs;
import com.tokelon.toktales.android.input.dispatch.IAndroidInputRegistration.IScreenButtonCallback;
import com.tokelon.toktales.android.input.events.IScreenButtonInputEvent;
import com.tokelon.toktales.core.engine.EngineException;
import com.tokelon.toktales.core.engine.ui.IConsoleUIExtension;
import com.tokelon.toktales.core.engine.ui.IDebugUIExtension;
import com.tokelon.toktales.core.engine.ui.IUIService;
import com.tokelon.toktales.core.game.states.IGameStateInputHandler;
import com.tokelon.toktales.core.game.states.InjectGameState;
import com.tokelon.toktales.extens.def.core.game.states.console.IConsoleGamestate;

@InjectGameState
public class AndroidConsoleInputHandler implements IGameStateInputHandler, IScreenButtonCallback {

	public static final String TAG = "AndroidConsoleInputHandler";

	
	private IConsoleGamestate consoleGamestate;
	
	@InjectGameState
	protected void passGamestate(IConsoleGamestate gamestate) {
		this.consoleGamestate = gamestate;
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
			consoleGamestate.getLog().e(TAG, "Error opening console input: " + e.getMessage());
		}
	}
	
	private void buttonSETPressed() {
		IUIService uiService = consoleGamestate.getEngine().getUIService();
		
		try {
			uiService.getExtensionByTypeOrFail(IDebugUIExtension.class).openContextMenu();
		} catch (EngineException e) {
			consoleGamestate.getLog().e(TAG, "Error opening context menu: " + e.getMessage());
		}
	}

}
