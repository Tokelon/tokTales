package com.tokelon.toktales.core.engine.setup;

import com.tokelon.toktales.core.engine.EngineException;
import com.tokelon.toktales.core.engine.IEngineContext;

public class EmptySetupStep implements ISetupStep {


	@Override
	public void onBuildUp(IEngineContext engineContext) throws EngineException {
		// Nothing
	}

	@Override
	public void onTearDown(IEngineContext engineContext) throws EngineException {
		// Nothing
	}

}
