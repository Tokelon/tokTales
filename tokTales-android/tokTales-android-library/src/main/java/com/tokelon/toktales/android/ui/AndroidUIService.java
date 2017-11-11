package com.tokelon.toktales.android.ui;

import android.app.Activity;

import com.tokelon.toktales.android.activity.IDebugActivity;
import com.tokelon.toktales.core.engine.AbstractEngineService;
import com.tokelon.toktales.core.engine.TokTales;
import com.tokelon.toktales.core.engine.ui.IUIService;

public class AndroidUIService extends AbstractEngineService implements IUIService {

	private static final String TAG = "AndroidUIService";
	
	private final IUserInterface userInterface;
	
	
	
	public AndroidUIService() {
		userInterface = new UserInterface();
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
				TokTales.getLog().d(TAG, "Could not open external UI. Unsupported Activity running");
			}
		}
		else {
			TokTales.getLog().w(TAG, "Unknown UI Code: " +uiCode);
		}
		
	}
	
	
}
