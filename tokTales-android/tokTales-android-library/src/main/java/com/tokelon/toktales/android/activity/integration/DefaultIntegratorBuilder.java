package com.tokelon.toktales.android.activity.integration;

import javax.inject.Inject;

import com.google.inject.Injector;

public class DefaultIntegratorBuilder extends InjectorIntegratorBuilder implements IDefaultActivityIntegratorBuilder {


	@Inject
	public DefaultIntegratorBuilder(Injector injector) {
		super(injector);
		
		addIntegration(ACTIVITY_INTEGRATION_UI_SERVICE, IUIServiceIntegration.class);
		addIntegration(ACTIVITY_INTEGRATION_KEYBOARD, IKeyboardActivityIntegration.class);
	}

}
