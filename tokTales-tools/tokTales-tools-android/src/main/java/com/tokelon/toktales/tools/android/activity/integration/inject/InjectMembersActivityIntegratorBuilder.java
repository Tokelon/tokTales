package com.tokelon.toktales.tools.android.activity.integration.inject;

import com.tokelon.toktales.tools.android.activity.integration.IActivityIntegratorBuilder;
import com.tokelon.toktales.tools.android.activity.integration.SimpleIntegratorBuilder;
import com.tokelon.toktales.tools.core.inject.IInjector;

public class InjectMembersActivityIntegratorBuilder extends SimpleIntegratorBuilder implements IActivityIntegratorBuilder {


	public static final String ACTIVITY_INTEGRATION_INJECT = "Activity_Integration_Inject";

	
	public InjectMembersActivityIntegratorBuilder(IInjector injector) {
		addIntegration(ACTIVITY_INTEGRATION_INJECT, new InjectMembersActivityIntegration(injector));
	}
	
}
