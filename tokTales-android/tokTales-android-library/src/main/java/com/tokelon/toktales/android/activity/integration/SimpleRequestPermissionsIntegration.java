package com.tokelon.toktales.android.activity.integration;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import com.tokelon.toktales.core.engine.log.ILogger;

import java.util.ArrayList;

public class SimpleRequestPermissionsIntegration implements IActivityIntegration {
	
	public static final String TAG = "SimpleRequestPermissionsIntegration";
	
	public static final int DEFAULT_PERMISSIONS_REQUEST_CODE = 1;
	
	
	private final ILogger logger;
	private final int requestCode;
	private final String[] permissions;
	
	public SimpleRequestPermissionsIntegration(ILogger logger, String... permissions) {
		this(logger, DEFAULT_PERMISSIONS_REQUEST_CODE, permissions);
	}
	
	public SimpleRequestPermissionsIntegration(ILogger logger, int requestCode, String... permissions) {
		this.logger = logger;
		this.requestCode = requestCode;
		this.permissions = permissions;
	}
	
	
	@Override
	public void onActivityCreate(IIntegratedActivity activity) {
		checkPermissions(activity.asActivity());
	}
	
	protected void checkPermissions(Activity activity) {
		logger.d(TAG, "Checking permissions...");
		
		ArrayList<String> neededPermissions = new ArrayList<>();
		for (String permission: permissions) {
			if (ContextCompat.checkSelfPermission(activity, permission)	!= PackageManager.PERMISSION_GRANTED) {
				neededPermissions.add(permission);
			}
		}
		
		if(neededPermissions.isEmpty()) {
			logger.i(TAG, "All permissions granted");
		}
		else {
			logger.i(TAG, "Requesting permissions: " + neededPermissions);
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
					logger.e(TAG, "Following permissions were denied: " + deniedPermissions);
				}
			}
			else {
				logger.e(TAG, "Permission request was cancelled");
			}
		}
	}
	
}
