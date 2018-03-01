package com.tokelon.toktales.android.states;

import javax.inject.Inject;

import com.tokelon.toktales.android.input.TokelonTypeAInputs;
import com.tokelon.toktales.core.engine.EngineException;
import com.tokelon.toktales.core.engine.log.ILogger;
import com.tokelon.toktales.core.engine.ui.IDebugUIExtension;
import com.tokelon.toktales.core.engine.ui.IUIService;

public class AndroidInitialGamestateInputHandler implements IAndroidGameStateInputHandler {
	// TODO: Is this needed? Otherwise delete
	
	public static final String TAG = "AndroidInitialGamestateInputHandler";
	
	
	private final ILogger logger;
	private final IUIService uiService;
	
	@Inject
	public AndroidInitialGamestateInputHandler(ILogger logger, IUIService uiService) {
		this.logger = logger;
		this.uiService = uiService;
	}

	
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
		try {
			uiService.getExtensionByTypeOrFail(IDebugUIExtension.class).openContextMenu();
		} catch (EngineException e) {
			logger.e(TAG, "Error opening context menu: " + e.getMessage());
		}
	}


}
