package com.tokelon.toktales.android.ui;

import java.util.HashSet;
import java.util.Set;

import android.app.Activity;

public class UserInterface implements IUserInterface {

	private final Set<UserInterfaceListener> listeners = new HashSet<UserInterfaceListener>();
	
	private Activity currentActivity;
	

	@Override
	public boolean isValid() {
		return currentActivity != null;
	}
	
	
	@Override
	public void updateCurrentActivity(Activity activity) {
		if(activity == null) {
			throw new NullPointerException();
		}
		
		currentActivity = activity;
		
		for(UserInterfaceListener l: listeners) {
			l.activityUpdated(currentActivity);
		}
	}
	
	@Override
	public void clearCurrentActivity() {
		currentActivity = null;
		
		for(UserInterfaceListener l: listeners) {
			l.activityCleared();
		}
	}
	
	@Override
	public Activity getCurrentActivity() {
		return currentActivity;
	}


	
	@Override
	public void addListener(UserInterfaceListener listener) {
		listeners.add(listener);
		
		// TODO: Call updateCurrentActivity on listener if currentActivity is not null ?
	}

	@Override
	public boolean removeListener(UserInterfaceListener listener) {
		return listeners.remove(listener);
	}
	
}

