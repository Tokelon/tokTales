package com.tokelon.toktales.android.activity.integration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Provider;

import com.google.inject.Injector;
import com.tokelon.toktales.android.activity.integration.IActivityIntegration.IActivityIntegrationFactory;
import com.tokelon.toktales.android.activity.integration.IActivityIntegrator.IActivityIntegratorFactory;

public class InjectorIntegratorBuilder implements IInjectActivityIntegratorBuilder {


	private final Map<String, Provider<? extends IActivityIntegration>> integrationProviders = new HashMap<>();
	
	private final Injector injector;
	
	@Inject
	public InjectorIntegratorBuilder(Injector injector) {
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
		
		for (Provider<? extends IActivityIntegration> integrationSupplier: integrationProviders.values()) {
			integrations.add(integrationSupplier.get());
		}
		
		return injector.getInstance(IActivityIntegratorFactory.class).create(activity, integrations);
	}
	
}
