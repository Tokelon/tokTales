package com.tokelon.toktales.desktop.lwjgl;

import com.tokelon.toktales.core.engine.EngineException;
import com.tokelon.toktales.core.engine.IEngineContext;
import com.tokelon.toktales.core.engine.setup.ISetupStep;

public class LWJGLProgramManageSetupStep implements ISetupStep {


	private LWJGLProgram program;

	@Override
	public void onBuildUp(IEngineContext engineContext) throws EngineException {
		this.program = new LWJGLProgram();
		try {
			program.setup();
		}
		catch (LWJGLException e) {
			throw new EngineException(e);
		}
	}

	@Override
	public void onTearDown(IEngineContext engineContext) throws EngineException {
		program.tearDown();
	}

}
