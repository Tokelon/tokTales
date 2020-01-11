package com.tokelon.toktales.android.activity.integration.engine;

import javax.inject.Inject;

import com.tokelon.toktales.core.engine.IEngineDriver;
import com.tokelon.toktales.tools.android.activity.integration.IIntegratedActivity;

public class EnterActivityEngineIntegration implements IEnterActivityEngineIntegration {


	private final IEngineDriver engineDriver;
	
	@Inject
	public EnterActivityEngineIntegration(IEngineDriver engineDriver) {
		this.engineDriver = engineDriver;
	}
	
	
	@Override
	public void onActivityCreate(IIntegratedActivity activity) {
		engineDriver.create();
	}
	
}
