package com.tokelon.toktales.android.activity.integration;

public interface IActivityIntegrator {
	// Implement add, remove, to use after creation? - Maybe like IGameStateIntegrator
	
	
	public Iterable<IActivityIntegration> getAllIntegrations();

	public <T extends IActivityIntegration> T getIntegrationByType(Class<T> type);
	
	
	/* Integrated methods */

	public void onCreate();

	public void onStart();

	public void onResume();

	public void onPause();

	public void onStop();

	public void onDestroy();
	
	

	public interface IActivityIntegratorFactory {
		
		public IActivityIntegrator create(IIntegratedActivity activity, Iterable<IActivityIntegration> integrations);
	}
	
}
