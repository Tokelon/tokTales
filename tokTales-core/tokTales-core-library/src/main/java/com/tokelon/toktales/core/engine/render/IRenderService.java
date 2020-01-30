package com.tokelon.toktales.core.engine.render;

import com.tokelon.toktales.core.engine.IEngineService;
import com.tokelon.toktales.core.screen.surface.ISurfaceManager;

public interface IRenderService extends IEngineService {

	// TODO: Somehow implement rendering lock like this
	//public IActionScheduler getRenderingScheduler();
	
	
	public ISurfaceManager getSurfaceManager();
	

	public IRenderAccess getRenderAccess();
	
}
