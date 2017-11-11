package com.tokelon.toktales.android.ui;

import android.app.Activity;

import com.tokelon.toktales.android.ui.IUserInterface.UserInterfaceListener;

public abstract class AbstractUIFWModule implements UserInterfaceListener {

	protected final AndroidUIService myService;
	
	public AbstractUIFWModule(AndroidUIService uiService) {
		myService = uiService;
		
		uiService.getUserInterface().addListener(this);
	}
	
	
	@Override
	public void activityCleared() {	}
	
	@Override
	public void activityUpdated(Activity newActivity) { }
	
}
