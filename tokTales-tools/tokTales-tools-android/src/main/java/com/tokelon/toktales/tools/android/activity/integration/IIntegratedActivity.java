package com.tokelon.toktales.tools.android.activity.integration;

import android.app.Activity;

public interface IIntegratedActivity {


	/**
	 * @return This object.
	 */
	public Activity asActivity();
	
	/**
	 * @return The integrator for this activity. 
	 */
	public IActivityIntegrator getIntegrator();
	
}
