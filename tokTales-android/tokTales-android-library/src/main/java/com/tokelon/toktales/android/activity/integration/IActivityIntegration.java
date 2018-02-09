package com.tokelon.toktales.android.activity.integration;

public interface IActivityIntegration {

	
	public default void onActivityCreate(IIntegratedActivity activity) { }

	public default void onActivityStart(IIntegratedActivity activity) { }

	public default void onActivityResume(IIntegratedActivity activity) { }

	public default void onActivityPause(IIntegratedActivity activity) { }

	public default void onActivityStop(IIntegratedActivity activity) { }

	public default void onActivityDestroy(IIntegratedActivity activity) { }
	
}
