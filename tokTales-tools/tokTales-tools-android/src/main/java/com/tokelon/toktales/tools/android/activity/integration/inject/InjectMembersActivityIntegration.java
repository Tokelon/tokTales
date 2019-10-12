package com.tokelon.toktales.tools.android.activity.integration.inject;

import com.tokelon.toktales.tools.android.activity.integration.IActivityIntegration;
import com.tokelon.toktales.tools.android.activity.integration.IIntegratedActivity;
import com.tokelon.toktales.tools.core.inject.IInjector;

class InjectMembersActivityIntegration implements IActivityIntegration {


	private final IInjector injector;

	public InjectMembersActivityIntegration(IInjector injector) {
		this.injector = injector;
	}
	
	
	@Override
	public void onActivityCreate(IIntegratedActivity activity) {
		injector.injectMembers(activity);
	}
	
}
