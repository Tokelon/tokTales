package com.tokelon.toktales.desktop.lwjgl;

import com.tokelon.toktales.core.engine.EngineException;
import com.tokelon.toktales.core.engine.IEngineContext;
import com.tokelon.toktales.core.engine.setup.ISetupStep;
import com.tokelon.toktales.desktop.input.IDesktopInputService;
import com.tokelon.toktales.desktop.lwjgl.input.IGLFWInputConsumer;

public class LWJGLManageCallbacksSetupStep implements ISetupStep {


	@Override
	public void onBuildUp(IEngineContext engineContext) throws EngineException {
		getMainGLFWInputConsumer(engineContext).registerCallbacks();
	}

	@Override
	public void onTearDown(IEngineContext engineContext) throws EngineException {
		getMainGLFWInputConsumer(engineContext).unregisterCallbacks();
	}

	protected IGLFWInputConsumer getMainGLFWInputConsumer(IEngineContext engineContext) {
		IDesktopInputService desktopInputService = engineContext.getInjector().getInstance(IDesktopInputService.class);

		// We assume the cast will succeed, otherwise we cannot use this setup step
		ILWJGLInputDispatch mainInputDispatch = (ILWJGLInputDispatch) desktopInputService.getMainInputDispatch();
		return mainInputDispatch.getGLFWInputConsumer();
	}

}
