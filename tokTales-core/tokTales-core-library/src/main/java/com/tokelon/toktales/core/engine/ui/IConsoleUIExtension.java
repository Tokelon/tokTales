package com.tokelon.toktales.core.engine.ui;

import com.tokelon.toktales.core.engine.EngineException;
import com.tokelon.toktales.core.engine.IEngineService.IServiceExtension;
import com.tokelon.toktales.core.game.controller.IConsoleController;

public interface IConsoleUIExtension extends IServiceExtension {
	
	// TODO: Not the controller should be passed here, but some other interface
	public void openConsoleInput(IConsoleController consoleController) throws EngineException;

}
