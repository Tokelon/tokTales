package com.tokelon.toktales.desktop.lwjgl;

import com.tokelon.toktales.core.engine.EngineException;
import com.tokelon.toktales.core.engine.IEngineContext;
import com.tokelon.toktales.core.engine.setup.ISetupStep;

public class LWJGLProgramManageSetupStep implements ISetupStep {


	private LWJGLProgram program;

	@Override
	public void onBuildUp(IEngineContext engineContext) throws EngineException {
		ILWJGLInputService inputService = engineContext.getInjector().getInstance(ILWJGLInputService.class);

		this.program = new LWJGLProgram(inputService.getMainInputDispatch().getGLFWInputConsumer());
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
