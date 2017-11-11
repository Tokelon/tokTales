package com.tokelon.toktales.extens.def.android.states;

import com.tokelon.toktales.android.input.IAndroidInputRegistration.IScreenButtonCallback;
import com.tokelon.toktales.core.engine.TokTales;
import com.tokelon.toktales.core.engine.ui.IUIConsoleExtension;
import com.tokelon.toktales.core.engine.ui.IUIService;
import com.tokelon.toktales.core.game.states.IGameStateInputHandler;
import com.tokelon.toktales.extens.def.core.game.states.ConsoleGamestate;
import com.tokelon.toktales.android.input.TokelonTypeAInputs;

public class AndroidConsoleInputHandler implements IGameStateInputHandler, IScreenButtonCallback {

	public static final String TAG = "AndroidConsoleInputHandler"; 

	
	private final ConsoleGamestate consoleGamestate;
	
	public AndroidConsoleInputHandler(ConsoleGamestate consoleGamestate) {
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
		IUIConsoleExtension cons = TokTales.getEngine().getUIService().getExtensionAs("console", IUIConsoleExtension.class);
		cons.openConsoleInput(consoleGamestate.getConsoleController());
	}
	
	private void buttonSETPressed() {
		IUIService uiService = TokTales.getEngine().getUIService();
		uiService.openExternalUI(IUIService.EXTERNAL_UI_CODE_DEBUG);
	}
	

}
