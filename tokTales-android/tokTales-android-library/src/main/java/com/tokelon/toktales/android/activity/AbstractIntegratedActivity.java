package com.tokelon.toktales.android.activity;

import java.util.HashMap;
import java.util.Map;

import com.tokelon.toktales.android.activity.integration.IActivityIntegration;
import com.tokelon.toktales.android.activity.integration.IActivityIntegrator;
import com.tokelon.toktales.android.activity.integration.IIntegratedActivity;
import com.tokelon.toktales.android.activity.integration.IUIServiceIntegration.UIServiceIntegration;
import com.tokelon.toktales.android.activity.integration.KeyboardActivityIntegration;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;

public abstract class AbstractIntegratedActivity extends Activity implements IIntegratedActivity {
	
	public static final String ACTIVITY_INTEGRATION_UI_SERVICE = "AbstractIntegratedActivity_Integration_UIService";
	public static final String ACTIVITY_INTEGRATION_KEYBOARD = "AbstractIntegratedActivity_Integration_Keyboard";

	
	private final Map<String, IActivityIntegration> integrationsMap;
	private final IActivityIntegrator integrator;
	

	public AbstractIntegratedActivity() {
		integrationsMap = createActivityIntegrations();
		integrator = ActivityHelper.createActivityIntegrator(this, integrationsMap.values());
	}
	
	
	
	/**
	 * @return A map containing all integrations that are used in this activity, with their names as keys.
	 */
	protected Map<String, IActivityIntegration> getActivityIntegrations() {
		return integrationsMap;
	}

	/**
	 * @return A map containing all integrations that should be used in this activity, with their names as keys.
	 */
	protected Map<String, IActivityIntegration> createActivityIntegrations() {
		HashMap<String, IActivityIntegration> map = new HashMap<>();
		
		map.put(ACTIVITY_INTEGRATION_UI_SERVICE, new UIServiceIntegration());
		map.put(ACTIVITY_INTEGRATION_KEYBOARD, new KeyboardActivityIntegration(new Handler()));
		
		return map;
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
