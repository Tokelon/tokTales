package com.tokelon.toktales.core.engine.ui;

import com.tokelon.toktales.core.engine.IEngineService.IServiceExtension;
import com.tokelon.toktales.core.game.controller.IConsoleController;

public interface IUIConsoleExtension extends IServiceExtension {
	
	// TODO: Not the controller should be passed here, but some other interface
	public int openConsoleInput(IConsoleController consoleController);

}
