package com.tokelon.toktales.android.states;

import com.tokelon.toktales.android.input.IAndroidInputRegistration.IScreenButtonCallback;
import com.tokelon.toktales.core.engine.IEngineContext;
import com.tokelon.toktales.core.engine.TokTales;
import com.tokelon.toktales.core.engine.ui.IUIService;
import com.tokelon.toktales.core.game.screen.order.IRenderOrder;
import com.tokelon.toktales.core.game.states.InitialGamestate;
import com.tokelon.toktales.android.input.TokelonTypeAInputs;

public class AndroidInitialGamestate extends InitialGamestate {

	
	private final AndroidGameStateInput input = new AndroidGameStateInput();
	
	public AndroidInitialGamestate(IEngineContext context) {
		super(context);
		
		input.registerScreenButtonCallback(new InitialGamestateInputHandler());
		setStateInput(input);
	}
	
	
	public AndroidInitialGamestate(IEngineContext context, IRenderOrder baseOrder) {
		super(context, baseOrder);

		input.registerScreenButtonCallback(new InitialGamestateInputHandler());
		setStateInput(input);
	}



	private class InitialGamestateInputHandler implements IScreenButtonCallback {
		// TODO: Is this needed? Otherwise delete

		@Override
		public boolean invokeScreenButton(int vb, int action) {
			boolean handled = true;
			
			if(action == TokelonTypeAInputs.BUTTON_PRESS) {
				if(vb == TokelonTypeAInputs.VB_A) {
					buttonAPressed();
				}
				else if(vb == TokelonTypeAInputs.VB_B) {
					buttonBPressed();
				}
				else {
					handled = false;
				}
			}
			else if(action == TokelonTypeAInputs.BUTTON_RELEASE) {
				handled = false;
			}
			else {
				handled = false;
			}
			
			return handled;
		}
		
		
		private void buttonAPressed() {

			// Nothing
		}

		private void buttonBPressed() {
		
			//IUIFramework uiFramework = (IUIFramework) Prog.getPIP().getProgramInterface(IPIP.IID_FRAMEWORK_UI);
			IUIService uiService = TokTales.getEngine().getUIService();
			
			uiService.openExternalUI(IUIService.EXTERNAL_UI_CODE_DEBUG);
		}

	}

	
}
