package com.tokelon.toktales.tools.android.activity.integration;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public abstract class AbstractIntegratedCompatActivity extends AppCompatActivity implements IIntegratedActivity {


	private IActivityIntegrator integrator;
	
	private final IActivityIntegratorBuilder initialIntegratorBuilder;

	public AbstractIntegratedCompatActivity() {
		this.initialIntegratorBuilder = null;
	}
	
	public AbstractIntegratedCompatActivity(IActivityIntegratorBuilder integratorBuilder) {
		this.initialIntegratorBuilder = integratorBuilder;
	}


	protected IActivityIntegratorBuilder createIntegrationBuilder() {
		return initialIntegratorBuilder == null ? new SimpleIntegratorBuilder() : initialIntegratorBuilder;
	}
	
	protected IActivityIntegrator buildIntegrator(IActivityIntegratorBuilder builder) {
		return builder.build(this);
	}
	
	protected void initializeIntegrations() {
		integrator = buildIntegrator(createIntegrationBuilder());
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
