package com.tokelon.toktales.android.activity.integration;

import java.util.ArrayList;

import javax.inject.Inject;

import com.tokelon.toktales.core.engine.log.ILogger;
import com.tokelon.toktales.core.engine.log.ILogging;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

public class SimpleRequestPermissionsIntegration implements ISimpleRequestPermissionsIntegration {


	public static final int DEFAULT_PERMISSIONS_REQUEST_CODE = 1;
	
	
	private final ILogger logger;
	private final int requestCode;
	private final String[] permissions;
	
	public SimpleRequestPermissionsIntegration(ILogging logging, String... permissions) {
		this(logging, DEFAULT_PERMISSIONS_REQUEST_CODE, permissions);
	}
	
	public SimpleRequestPermissionsIntegration(ILogging logging, int requestCode, String... permissions) {
		this.logger = logging.getLogger(getClass());
		this.requestCode = requestCode;
		this.permissions = permissions;
	}
	
	
	@Override
	public void onActivityCreate(IIntegratedActivity activity) {
		checkPermissions(activity.asActivity());
	}
	
	protected void checkPermissions(Activity activity) {
		logger.debug("Checking permissions...");
		
		ArrayList<String> neededPermissions = new ArrayList<>();
		for (String permission: permissions) {
			if (ContextCompat.checkSelfPermission(activity, permission)	!= PackageManager.PERMISSION_GRANTED) {
				neededPermissions.add(permission);
			}
		}
		
		if(neededPermissions.isEmpty()) {
			logger.info("All permissions granted");
		}
		else {
			logger.info("Requesting permissions: {}", neededPermissions);
			ActivityCompat.requestPermissions(activity,	neededPermissions.toArray(new String[neededPermissions.size()]), requestCode);
		}
	}
	
	
	/** Optional callback for logging purposes.
	 *
	 * @param activity
	 * @param requestCode
	 * @param permissions
	 * @param grantResults
	 */
	@Override
	public void onActivityRequestPermissionsResult(IIntegratedActivity activity, int requestCode, String permissions[], int[] grantResults) {
		// If request is cancelled, the result arrays are empty
		if(requestCode == this.requestCode) {
			if(grantResults.length > 0) {
				ArrayList<String> deniedPermissions = new ArrayList<>();
				for(int i = 0; i < permissions.length; i++) {
					if(grantResults[i] == PackageManager.PERMISSION_DENIED) {
						deniedPermissions.add(permissions[i]);
					}
				}
				
				if(!deniedPermissions.isEmpty()) {
					logger.error("Following permissions were denied: {}" + deniedPermissions);
				}
			}
			else {
				logger.error("Permission request was cancelled");
			}
		}
	}
	
	
	public static class SimpleRequestPermissionsIntegrationFactory implements ISimpleRequestPermissionsIntegrationFactory {
		private final ILogging logging;

		@Inject
		public SimpleRequestPermissionsIntegrationFactory(ILogging logging) {
			this.logging = logging;
		}

		
		@Override
		public ISimpleRequestPermissionsIntegration create(String... permissions) {
			return new SimpleRequestPermissionsIntegration(logging, permissions);
		}

		@Override
		public ISimpleRequestPermissionsIntegration create(int requestCode, String... permissions) {
			return new SimpleRequestPermissionsIntegration(logging, requestCode, permissions);
		}
	}
	
}
