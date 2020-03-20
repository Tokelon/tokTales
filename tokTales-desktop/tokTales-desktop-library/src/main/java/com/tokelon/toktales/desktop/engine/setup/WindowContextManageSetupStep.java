package com.tokelon.toktales.desktop.engine.setup;

import com.tokelon.toktales.core.engine.EngineException;
import com.tokelon.toktales.core.engine.IEngineContext;
import com.tokelon.toktales.core.engine.setup.ISetupStep;
import com.tokelon.toktales.desktop.ui.window.IWindowHandler;

public class WindowContextManageSetupStep implements ISetupStep {


	private IWindowHandler windowHandler;

	public WindowContextManageSetupStep(IWindowHandler windowHandler) {
		this.windowHandler = windowHandler;
	}


	@Override
	public void onBuildUp(IEngineContext engineContext) throws EngineException {
		windowHandler.createWindowContext(engineContext);
	}

	@Override
	public void onTearDown(IEngineContext engineContext) throws EngineException {
		windowHandler.destroyWindowContext();
	}

}
