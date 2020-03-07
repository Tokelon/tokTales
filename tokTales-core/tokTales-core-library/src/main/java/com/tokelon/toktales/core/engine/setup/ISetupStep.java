package com.tokelon.toktales.core.engine.setup;

import com.tokelon.toktales.core.engine.EngineException;
import com.tokelon.toktales.core.engine.IEngineContext;

public interface ISetupStep {


	public void run(IEngineContext context) throws EngineException;

}
