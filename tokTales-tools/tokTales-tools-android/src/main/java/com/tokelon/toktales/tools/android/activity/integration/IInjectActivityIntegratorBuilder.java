package com.tokelon.toktales.tools.android.activity.integration;

public interface IInjectActivityIntegratorBuilder extends IActivityIntegratorBuilder {


	public void addIntegration(String name, Class<? extends IActivityIntegration> integrationClass);
	
}
