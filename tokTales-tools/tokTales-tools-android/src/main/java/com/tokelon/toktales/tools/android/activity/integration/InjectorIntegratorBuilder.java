package com.tokelon.toktales.tools.android.activity.integration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tokelon.toktales.tools.android.activity.integration.IActivityIntegration.IActivityIntegrationFactory;
import com.tokelon.toktales.tools.android.activity.integration.IActivityIntegrator.IActivityIntegratorFactory;
import com.tokelon.toktales.tools.core.inject.IInjector;
import com.tokelon.toktales.tools.core.inject.IProvider;

public class InjectorIntegratorBuilder implements IInjectActivityIntegratorBuilder {


	private final Map<String, IProvider<? extends IActivityIntegration>> integrationProviders = new HashMap<>();
	
	private final IInjector injector;
	
	public InjectorIntegratorBuilder(IInjector injector) {
		this.injector = injector;
	}
	
	
	@Override
	public void addIntegration(String name, Class<? extends IActivityIntegration> integrationClass) {
		integrationProviders.put(name, injector.getProvider(integrationClass));
	}
	
	@Override
	public void addIntegration(String name, IActivityIntegration integration) {
		integrationProviders.put(name, () -> {
			injector.injectMembers(integration);
			return integration;
		});
	}

	@Override
	public void addIntegration(String name, IActivityIntegrationFactory integrationFactory) {
		integrationProviders.put(name, () -> {
			IActivityIntegration integration = integrationFactory.create();
			injector.injectMembers(integration);
			return integration;
		});
	}
	
	@Override
	public void removeIntegration(String name) {
		integrationProviders.remove(name);
	}
	

	@Override
	public IActivityIntegrator build(IIntegratedActivity activity) {
		List<IActivityIntegration> integrations = new ArrayList<>();
		
		for (IProvider<? extends IActivityIntegration> integrationSupplier: integrationProviders.values()) {
			integrations.add(integrationSupplier.get());
		}
		
		return injector.getInstance(IActivityIntegratorFactory.class).create(activity, integrations);
	}
	
}
