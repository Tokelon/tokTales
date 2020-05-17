package com.tokelon.toktales.desktop.lwjgl.setup;

import com.tokelon.toktales.core.engine.EngineException;
import com.tokelon.toktales.core.engine.IEngineContext;
import com.tokelon.toktales.core.engine.setup.ISetupStep;
import com.tokelon.toktales.desktop.lwjgl.ILWJGLInputService;
import com.tokelon.toktales.desktop.lwjgl.input.IGLFWInputConsumer;

public class LWJGLRegisterCallbacksSetupStep implements ISetupStep {


	@Override
	public void onBuildUp(IEngineContext engineContext) throws EngineException {
		getMainGLFWInputConsumer(engineContext).registerCallbacks();
	}

	@Override
	public void onTearDown(IEngineContext engineContext) throws EngineException {
		getMainGLFWInputConsumer(engineContext).unregisterCallbacks();
	}

	protected IGLFWInputConsumer getMainGLFWInputConsumer(IEngineContext engineContext) {
		// We assume the instance will be resolved, otherwise we cannot use this setup step
		ILWJGLInputService inputService = engineContext.getInjector().getInstance(ILWJGLInputService.class);
		return inputService.getMainInputDispatch().getGLFWInputConsumer();
	}

}
