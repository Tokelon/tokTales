package com.tokelon.toktales.android.ui;

import com.tokelon.toktales.android.activity.integration.IIntegratedActivity;

public interface IUserInterface {

	
	public boolean hasActivity();
	public IIntegratedActivity getCurrentActivity();

	public void updateCurrentActivity(IIntegratedActivity activity);
	public void clearCurrentActivity();
	
	
	public void addListener(IUserInterfaceListener listener);
	public boolean removeListener(IUserInterfaceListener listener);

	
	public interface IUserInterfaceListener {
		
		public void activityUpdated(IIntegratedActivity newActivity);
		public void activityCleared();
	}

}
