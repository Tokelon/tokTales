package com.tokelon.toktales.android.activity.integration;

import javax.inject.Inject;

import com.tokelon.toktales.android.ui.IAndroidUIService;

public class UIServiceIntegration implements IUIServiceIntegration {


	private final IAndroidUIService uiService;
	
	@Inject
	public UIServiceIntegration(IAndroidUIService uiService) {
		this.uiService = uiService;
	}
	

	@Override
	public void onActivityCreate(IIntegratedActivity activity) {
		uiService.getUserInterface().updateCurrentActivity(activity);
	}

	@Override
	public void onActivityResume(IIntegratedActivity activity) {
		uiService.getUserInterface().updateCurrentActivity(activity);
	}

	@Override
	public void onActivityPause(IIntegratedActivity activity) {
		uiService.getUserInterface().clearCurrentActivity();
	}

}