package com.tokelon.toktales.tools.android.activity.integration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tokelon.toktales.tools.android.activity.integration.IActivityIntegration.IActivityIntegrationFactory;

public class SimpleIntegratorBuilder implements IActivityIntegratorBuilder {


	private final Map<String, IActivityIntegrationFactory> integrationProviders = new HashMap<>();
	
	
	@Override
	public void addIntegration(String name, IActivityIntegration integration) {
		integrationProviders.put(name, () -> integration);
	}

	@Override
	public void addIntegration(String name, IActivityIntegrationFactory integrationFactory) {
		integrationProviders.put(name, integrationFactory);
	}
	
	@Override
	public void removeIntegration(String name) {
		integrationProviders.remove(name);
	}
	

	@Override
	public IActivityIntegrator build(IIntegratedActivity activity) {
		List<IActivityIntegration> integrations = new ArrayList<>();
		
		for (IActivityIntegrationFactory integrationSupplier: integrationProviders.values()) {
			integrations.add(integrationSupplier.create());
		}
		
		return new ActivityIntegrator(activity, integrations);
	}

}
