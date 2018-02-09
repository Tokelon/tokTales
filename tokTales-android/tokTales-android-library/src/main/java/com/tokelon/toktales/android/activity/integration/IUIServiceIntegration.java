package com.tokelon.toktales.android.activity.integration;

import javax.inject.Inject;

import com.tokelon.toktales.android.ui.IAndroidUIService;

public interface IUIServiceIntegration extends IActivityIntegration {

	
	public class UIServiceIntegration implements IUIServiceIntegration {
		private IAndroidUIService uiService;

		@Inject
		private void dependencies(IAndroidUIService uiService) {
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

	
}
