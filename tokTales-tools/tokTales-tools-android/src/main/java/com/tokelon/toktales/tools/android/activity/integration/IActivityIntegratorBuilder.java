package com.tokelon.toktales.tools.android.activity.integration;

import com.tokelon.toktales.tools.android.activity.integration.IActivityIntegration.IActivityIntegrationFactory;

public interface IActivityIntegratorBuilder {
	// TODO: Add methods to access all integrations?


	public void addIntegration(String name, IActivityIntegration integration);
	public void addIntegration(String name, IActivityIntegrationFactory integrationFactory);
	
	public void removeIntegration(String name);
	

	public IActivityIntegrator build(IIntegratedActivity activity);
	
}
