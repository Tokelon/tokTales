package com.tokelon.toktales.android.activity.integration;

import com.tokelon.toktales.android.activity.integration.IActivityIntegration.IActivityIntegrationFactory;

public interface IActivityIntegratorBuilder {


	public void addIntegration(String name, IActivityIntegration integration);
	public void addIntegration(String name, IActivityIntegrationFactory integrationFactory);
	
	public void removeIntegration(String name);
	

	//public void preBuild(IIntegratedActivity activity);
	//public void postBuild(IIntegratedActivity activity);
	
	public IActivityIntegrator build(IIntegratedActivity activity);
	
}
