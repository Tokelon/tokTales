package com.tokelon.toktales.tools.android.activity.integration;

import java.util.ArrayList;

public class ActivityIntegrator implements IActivityIntegrator {


	private final ArrayList<IActivityIntegration> integrationList = new ArrayList<>();
	
	private final IIntegratedActivity activity;
	
	public ActivityIntegrator(IIntegratedActivity activity, Iterable<IActivityIntegration> integrations) {
		this.activity = activity;
		
		for(IActivityIntegration integration: integrations) {
			integrationList.add(integration);
		}
	}
	

	@Override
	public Iterable<IActivityIntegration> getAllIntegrations() {
		return integrationList;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public <T extends IActivityIntegration> T getIntegrationByType(Class<T> type) {
		T result = null;
		for(IActivityIntegration integration: integrationList) {
			
			if(type.isInstance(integration)) {
				result = (T) integration;
				break;
			}
		}
		
		return result;
	}


	
	private void callActivityIntegrations(IntegrationMethodRunner runner) {
		for(IActivityIntegration integration: integrationList) {
			runner.run(integration);
		}
	}
	
	
	@Override
	public void onCreate() {
		callActivityIntegrations(integration -> integration.onActivityCreate(activity));
	}

	@Override
	public void onStart() {
		callActivityIntegrations(integration -> integration.onActivityStart(activity));
	}
	
	@Override
	public void onResume() {
		callActivityIntegrations(integration -> integration.onActivityResume(activity));
	}
	
	@Override
	public void onPause() {
		callActivityIntegrations(integration -> integration.onActivityPause(activity));
	}

	@Override
	public void onStop() {
		callActivityIntegrations(integration -> integration.onActivityStop(activity));
	}
	
	@Override
	public void onDestroy() {
		callActivityIntegrations(integration -> integration.onActivityDestroy(activity));
	}

	
	
	private interface IntegrationMethodRunner {
		
		public void run(IActivityIntegration integration);
	}
	
	
	public static class ActivityIntegratorFactory implements IActivityIntegratorFactory {

		@Override
		public IActivityIntegrator create(IIntegratedActivity activity, Iterable<IActivityIntegration> integrations) {
			return new ActivityIntegrator(activity, integrations);
		}
	}
	
}
