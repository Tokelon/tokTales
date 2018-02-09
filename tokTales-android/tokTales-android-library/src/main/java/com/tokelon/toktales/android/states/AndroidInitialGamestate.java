package com.tokelon.toktales.android.states;

import javax.inject.Inject;

import com.tokelon.toktales.android.input.IAndroidInputRegistration.IScreenButtonCallback;
import com.tokelon.toktales.android.input.TokelonTypeAInputs;
import com.tokelon.toktales.core.engine.EngineException;
import com.tokelon.toktales.core.engine.IEngineContext;
import com.tokelon.toktales.core.engine.ui.IDebugUIExtension;
import com.tokelon.toktales.core.engine.ui.IUIService;
import com.tokelon.toktales.core.game.screen.order.IRenderOrder;
import com.tokelon.toktales.core.game.states.InitialGamestate;

public class AndroidInitialGamestate extends InitialGamestate {

	
	private final AndroidGameStateInput input = new AndroidGameStateInput();
	

	public AndroidInitialGamestate(IEngineContext context) {
		super(context);
		
		input.registerScreenButtonCallback(new InitialGamestateInputHandler());
		setStateInput(input);
	}
	
	@Inject
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
			IUIService uiService = getEngine().getUIService();
			
			try {
				uiService.getExtensionByTypeOrFail(IDebugUIExtension.class).openContextMenu();
			} catch (EngineException e) {
				getLog().e(TAG, "Error opening context menu: " + e.getMessage());
			}
		}

	}

	
}
