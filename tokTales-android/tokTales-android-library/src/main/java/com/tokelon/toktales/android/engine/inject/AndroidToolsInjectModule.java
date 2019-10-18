package com.tokelon.toktales.android.engine.inject;

import com.tokelon.toktales.android.activity.integration.DefaultIntegratorBuilder;
import com.tokelon.toktales.android.activity.integration.IDefaultActivityIntegratorBuilder;
import com.tokelon.toktales.android.activity.integration.InjectorIntegratorBuilder;
import com.tokelon.toktales.core.engine.inject.AbstractInjectModule;
import com.tokelon.toktales.tools.android.activity.integration.ActivityIntegrator.ActivityIntegratorFactory;
import com.tokelon.toktales.tools.android.activity.integration.IActivityIntegrator.IActivityIntegratorFactory;
import com.tokelon.toktales.tools.android.activity.integration.IActivityIntegratorBuilder;
import com.tokelon.toktales.tools.android.activity.integration.IInjectActivityIntegratorBuilder;

public class AndroidToolsInjectModule extends AbstractInjectModule {


	@Override
	protected void configure() {
		bind(IActivityIntegratorFactory.class).to(ActivityIntegratorFactory.class);
		bind(IActivityIntegratorBuilder.class).to(IInjectActivityIntegratorBuilder.class);
		bind(IInjectActivityIntegratorBuilder.class).to(InjectorIntegratorBuilder.class);
		
		bind(IDefaultActivityIntegratorBuilder.class).to(DefaultIntegratorBuilder.class);
	}
	
}
