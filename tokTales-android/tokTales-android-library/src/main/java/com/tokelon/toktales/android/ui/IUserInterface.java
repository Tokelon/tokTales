package com.tokelon.toktales.android.ui;

import android.app.Activity;

public interface IUserInterface {

	public boolean isValid();
	
	public Activity getCurrentActivity();

	public void updateCurrentActivity(Activity activity);
	
	public void clearCurrentActivity();
	
	
	public void addListener(UserInterfaceListener listener);
	
	public boolean removeListener(UserInterfaceListener listener);
	
	
	
	public interface UserInterfaceListener {
		
		public void activityUpdated(Activity newActivity);
		
		public void activityCleared();
		
	}

}
