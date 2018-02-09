package com.tokelon.toktales.android.ui;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import com.tokelon.toktales.android.activity.integration.IIntegratedActivity;

public class UserInterface implements IUserInterface {

	
	private final Set<IUserInterfaceListener> listeners;
	
	private IIntegratedActivity currentActivity;

	
	public UserInterface() {
		this.listeners = Collections.synchronizedSet(new HashSet<IUserInterfaceListener>());
	}
	

	@Override
	public boolean hasActivity() {
		return currentActivity != null;
	}
	
	
	@Override
	public void updateCurrentActivity(IIntegratedActivity activity) {
		if(activity == null) {
			throw new NullPointerException();
		}
		
		currentActivity = activity;
		
		synchronized (listeners) {
			for(IUserInterfaceListener l: listeners) {
				l.activityUpdated(currentActivity);
			}	
		}
	}
	
	@Override
	public void clearCurrentActivity() {
		currentActivity = null;
		
		synchronized (listeners) {
			for(IUserInterfaceListener l: listeners) {
				l.activityCleared();
			}
		}
	}
	
	@Override
	public IIntegratedActivity getCurrentActivity() {
		return currentActivity;
	}


	
	@Override
	public void addListener(IUserInterfaceListener listener) {
		listeners.add(listener);
	}

	@Override
	public boolean removeListener(IUserInterfaceListener listener) {
		return listeners.remove(listener);
	}
	
	
}

