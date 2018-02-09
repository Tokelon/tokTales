package com.tokelon.toktales.android.ui;

import javax.inject.Inject;

import com.tokelon.toktales.android.activity.IDebugActivity;
import com.tokelon.toktales.core.engine.AbstractEngineService;
import com.tokelon.toktales.core.engine.log.ILogger;

import android.app.Activity;

public class AndroidUIService extends AbstractEngineService implements IAndroidUIService {

	private static final String TAG = "AndroidUIService";
	
	
	private final ILogger logger;
	private final IUserInterface userInterface;

	@Inject
	public AndroidUIService(ILogger logger, IUserInterface userInterface) {
		this.logger = logger;
		this.userInterface = userInterface;
	}
	
	
	@Override
	public IUserInterface getUserInterface() {
		return userInterface;
	}

	
	
	@Override
	public void openExternalUI(int uiCode) {
		
		Activity currentActivity = userInterface.getCurrentActivity().asActivity();
		
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
