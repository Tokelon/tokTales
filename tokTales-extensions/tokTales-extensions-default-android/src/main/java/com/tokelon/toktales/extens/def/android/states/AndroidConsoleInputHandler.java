package com.tokelon.toktales.extens.def.android.states;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import com.tokelon.toktales.android.input.IAndroidInputRegistration.IScreenButtonCallback;
import com.tokelon.toktales.android.input.TokelonTypeAInputs;
import com.tokelon.toktales.core.engine.EngineException;
import com.tokelon.toktales.core.engine.ui.IConsoleUIExtension;
import com.tokelon.toktales.core.engine.ui.IDebugUIExtension;
import com.tokelon.toktales.core.engine.ui.IUIService;
import com.tokelon.toktales.extens.def.core.game.states.IConsoleGamestate;
import com.tokelon.toktales.extens.def.core.game.states.IConsoleGamestateInputHandler;

public class AndroidConsoleInputHandler implements IConsoleGamestateInputHandler, IScreenButtonCallback {

	public static final String TAG = "AndroidConsoleInputHandler";

	
	private final IConsoleGamestate consoleGamestate;
	
	@Inject
	public AndroidConsoleInputHandler(@Assisted IConsoleGamestate consoleGamestate) {
		this.consoleGamestate = consoleGamestate;
	}
	
	
	@Override
	public boolean invokeScreenButton(int vb, int action) {
		boolean handled = true;
		
		if(action == TokelonTypeAInputs.BUTTON_PRESS) {
			switch (vb) {
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
		else if(action == TokelonTypeAInputs.BUTTON_RELEASE) {
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
