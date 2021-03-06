package com.tokelon.toktales.tools.android.activity.integration;

import android.app.Activity;
import android.os.Bundle;

public abstract class AbstractIntegratedActivity extends Activity implements IIntegratedActivity {


	private IActivityIntegrator integrator;
	
	private final IActivityIntegratorBuilder initialIntegratorBuilder;

	public AbstractIntegratedActivity() {
		this.initialIntegratorBuilder = null;
	}
	
	public AbstractIntegratedActivity(IActivityIntegratorBuilder integratorBuilder) {
		this.initialIntegratorBuilder = integratorBuilder;
	}
	
	
	protected IActivityIntegratorBuilder createIntegrationBuilder() {
		return initialIntegratorBuilder == null ? new SimpleIntegratorBuilder() : initialIntegratorBuilder;
	}
	
	protected void configureIntegrator(IActivityIntegratorBuilder builder) {
		// Nothing to configure here
	}
	
	protected IActivityIntegrator buildIntegrator(IActivityIntegratorBuilder builder) {
		return builder.build(this);
	}
	
	protected void initializeIntegrations() {
		IActivityIntegratorBuilder integrationBuilder = createIntegrationBuilder();
		configureIntegrator(integrationBuilder);
		this.integrator = buildIntegrator(integrationBuilder);
	}
	
	
	@Override
	public Activity asActivity() {
		return this;
	}
	
	@Override
	public IActivityIntegrator getIntegrator() {
		return integrator;
	}
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		initializeIntegrations(); // Call this before super.onCreate() ?
		
		integrator.onCreate();
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		
		integrator.onStart();
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		
		integrator.onResume();
	}


	@Override
	protected void onPause() {
		super.onPause();
		
		integrator.onPause();
	}
		
	@Override
	protected void onStop() {
		super.onStop();
		
		integrator.onStop();
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		
		integrator.onDestroy();
	}
	
}
