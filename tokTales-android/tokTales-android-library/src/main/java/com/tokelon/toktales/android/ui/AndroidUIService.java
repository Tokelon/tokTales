package com.tokelon.toktales.android.ui;

import javax.inject.Inject;

import com.tokelon.toktales.android.activity.IDebugActivity;
import com.tokelon.toktales.core.engine.AbstractEngineService;
import com.tokelon.toktales.core.engine.log.ILogger;
import com.tokelon.toktales.core.engine.ui.IUIService;

import android.app.Activity;

public class AndroidUIService extends AbstractEngineService implements IUIService {

	private static final String TAG = "AndroidUIService";
	
	
	// TODO: Inject this
	private final IUserInterface userInterface;
	
	private final ILogger logger;
	
	@Inject
	public AndroidUIService(ILogger logger) {
		this.logger = logger;
		
		this.userInterface = new UserInterface();
	}
	
	public IUserInterface getUserInterface() {
		return userInterface;
	}

	
	
	@Override
	public void openExternalUI(int uiCode) {
		
		Activity currentActivity = userInterface.getCurrentActivity();
		
		// TODO: Refactor as debug module ?
		if(uiCode == EXTERNAL_UI_CODE_DEBUG) {
			if(currentActivity instanceof IDebugActivity) {
				IDebugActivity da = (IDebugActivity) currentActivity;
				da.openContextMenu();
			}
			else {
				logger.d(TAG, "Could not open external UI. Unsupported Activity running");
			}
		}
		else {
			logger.w(TAG, "Unknown UI Code: " +uiCode);
		}
	}
	
	
}
