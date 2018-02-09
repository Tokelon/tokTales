package com.tokelon.toktales.android.activity.integration;

import java.util.ArrayList;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import com.tokelon.toktales.core.prog.annotation.CustomFunctionalInterface;

public class ActivityIntegrator implements IActivityIntegrator {

	
	private final ArrayList<IActivityIntegration> integrationList = new ArrayList<>();
	
	private final IIntegratedActivity activity;
	
	@Inject
	public ActivityIntegrator(@Assisted IIntegratedActivity activity, @Assisted Iterable<IActivityIntegration> integrations) {
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

	
	
	@CustomFunctionalInterface
	private interface IntegrationMethodRunner {
		public void run(IActivityIntegration integration);
	}

	
}
