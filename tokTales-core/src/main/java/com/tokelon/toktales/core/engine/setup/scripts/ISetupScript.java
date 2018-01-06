package com.tokelon.toktales.core.engine.setup.scripts;

import com.tokelon.toktales.core.engine.EngineException;
import com.tokelon.toktales.core.engine.IEngineContext;

public interface ISetupScript {
	
	public void run(IEngineContext context) throws EngineException;

}
