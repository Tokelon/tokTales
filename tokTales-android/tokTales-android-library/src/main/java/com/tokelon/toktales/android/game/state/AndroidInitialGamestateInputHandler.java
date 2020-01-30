package com.tokelon.toktales.android.game.state;

import javax.inject.Inject;

import com.tokelon.toktales.android.input.TokelonTypeAInputs;
import com.tokelon.toktales.android.input.events.IScreenButtonInputEvent;
import com.tokelon.toktales.core.engine.EngineException;
import com.tokelon.toktales.core.engine.log.ILogger;
import com.tokelon.toktales.core.engine.log.ILogging;
import com.tokelon.toktales.core.engine.ui.IDebugUIExtension;
import com.tokelon.toktales.core.engine.ui.IUIService;

public class AndroidInitialGamestateInputHandler implements IAndroidGameStateInputHandler {
	// TODO: Is this needed? Otherwise delete


	private final ILogger logger;
	private final IUIService uiService;
	
	@Inject
	public AndroidInitialGamestateInputHandler(ILogging logging, IUIService uiService) {
		this.logger = logging.getLogger(getClass());
		this.uiService = uiService;
	}

	
	@Override
	public boolean handleScreenButtonInput(IScreenButtonInputEvent event) {
		boolean handled = true;
		
		int button = event.getButton();
		int action = event.getAction();

		if(action == TokelonTypeAInputs.BUTTON_PRESS) {
			if(button == TokelonTypeAInputs.VB_A) {
				buttonAPressed();
			}
			else if(button == TokelonTypeAInputs.VB_B) {
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
			logger.error("Error opening context menu", e);
		}
	}

}
