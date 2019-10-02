package com.tokelon.toktales.android.activity.integration;

public interface ISimpleRequestPermissionsIntegration extends IActivityIntegration {


	public void onActivityRequestPermissionsResult(IIntegratedActivity activity, int requestCode, String permissions[], int[] grantResults);
	
	
	public interface ISimpleRequestPermissionsIntegrationFactory {
		
		public ISimpleRequestPermissionsIntegration create(String... permissions);
		public ISimpleRequestPermissionsIntegration create(int requestCode, String... permissions);
	}
	
}
