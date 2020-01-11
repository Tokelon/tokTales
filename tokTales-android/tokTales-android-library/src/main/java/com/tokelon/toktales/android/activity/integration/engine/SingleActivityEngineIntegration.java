package com.tokelon.toktales.android.activity.integration.engine;

import javax.inject.Inject;

import com.tokelon.toktales.tools.android.activity.integration.IIntegratedActivity;

public class SingleActivityEngineIntegration implements ISingleActivityEngineIntegration {


	private final IEnterActivityEngineIntegration enterActivityEngineIntegration;
	private final IRunActivityEngineIntegration runActivityEngineIntegration;
	private final IExitActivityEngineIntegration exitActivityEngineIntegration;

	@Inject
	public SingleActivityEngineIntegration(
			IEnterActivityEngineIntegration enterActivityEngineIntegration,
			IRunActivityEngineIntegration runActivityEngineIntegration,
			IExitActivityEngineIntegration exitActivityEngineIntegration
	) {
		this.enterActivityEngineIntegration = enterActivityEngineIntegration;
		this.runActivityEngineIntegration = runActivityEngineIntegration;
		this.exitActivityEngineIntegration = exitActivityEngineIntegration;
	}
	
	
	@Override
	public void onActivityCreate(IIntegratedActivity activity) {
		enterActivityEngineIntegration.onActivityCreate(activity);
		runActivityEngineIntegration.onActivityCreate(activity);
		exitActivityEngineIntegration.onActivityCreate(activity);
	}
	
	@Override
	public void onActivityStart(IIntegratedActivity activity) {
		enterActivityEngineIntegration.onActivityStart(activity);
		runActivityEngineIntegration.onActivityStart(activity);
		exitActivityEngineIntegration.onActivityStart(activity);
	}
	
	@Override
	public void onActivityResume(IIntegratedActivity activity) {
		enterActivityEngineIntegration.onActivityResume(activity);
		runActivityEngineIntegration.onActivityResume(activity);
		exitActivityEngineIntegration.onActivityResume(activity);
	}
	
	@Override
	public void onActivityPause(IIntegratedActivity activity) {
		enterActivityEngineIntegration.onActivityPause(activity);
		runActivityEngineIntegration.onActivityPause(activity);
		exitActivityEngineIntegration.onActivityPause(activity);
	}
	
	@Override
	public void onActivityStop(IIntegratedActivity activity) {
		enterActivityEngineIntegration.onActivityStop(activity);
		runActivityEngineIntegration.onActivityStop(activity);
		exitActivityEngineIntegration.onActivityStop(activity);
	}
	
	@Override
	public void onActivityDestroy(IIntegratedActivity activity) {
		enterActivityEngineIntegration.onActivityDestroy(activity);
		runActivityEngineIntegration.onActivityDestroy(activity);
		exitActivityEngineIntegration.onActivityDestroy(activity);
	}
	
}
