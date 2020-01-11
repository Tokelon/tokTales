package com.tokelon.toktales.android.activity.integration.engine;

import javax.inject.Inject;

import com.tokelon.toktales.core.engine.IEngineDriver;
import com.tokelon.toktales.tools.android.activity.integration.IIntegratedActivity;

public class RunActivityEngineIntegration implements IRunActivityEngineIntegration {


	private final IEngineDriver engineDriver;
	
	@Inject
	public RunActivityEngineIntegration(IEngineDriver engineDriver) {
		this.engineDriver = engineDriver;
	}
	
	
	@Override
	public void onActivityStart(IIntegratedActivity activity) {
		engineDriver.start();
	}
	
	@Override
	public void onActivityResume(IIntegratedActivity activity) {
		engineDriver.resume();
	}
	
	@Override
	public void onActivityPause(IIntegratedActivity activity) {
		engineDriver.pause();
	}
	
	@Override
	public void onActivityStop(IIntegratedActivity activity) {
		engineDriver.stop();
	}
	
}
