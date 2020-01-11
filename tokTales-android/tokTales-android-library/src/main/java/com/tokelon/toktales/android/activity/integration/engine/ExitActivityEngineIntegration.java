package com.tokelon.toktales.android.activity.integration.engine;

import javax.inject.Inject;

import com.tokelon.toktales.core.engine.IEngineDriver;
import com.tokelon.toktales.tools.android.activity.integration.IIntegratedActivity;

public class ExitActivityEngineIntegration implements IExitActivityEngineIntegration {


	private final IEngineDriver engineDriver;
	
	@Inject
	public ExitActivityEngineIntegration(IEngineDriver engineDriver) {
		this.engineDriver = engineDriver;
	}
	
	
	@Override
	public void onActivityDestroy(IIntegratedActivity activity) {
		engineDriver.destroy();
	}
	
}
