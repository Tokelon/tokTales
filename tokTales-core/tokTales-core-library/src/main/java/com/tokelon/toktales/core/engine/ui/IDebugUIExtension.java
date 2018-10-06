package com.tokelon.toktales.core.engine.ui;

import com.tokelon.toktales.core.engine.EngineException;
import com.tokelon.toktales.core.engine.IEngineService.IServiceExtension;

public interface IDebugUIExtension extends IServiceExtension {

	
	public void openContextMenu() throws EngineException;
	
}
