package com.tokelon.toktales.desktop.ui.window;

import com.tokelon.toktales.core.engine.EngineException;
import com.tokelon.toktales.core.engine.IEngineContext;

public interface IWindowHandler {


	public void createWindowContext(IEngineContext engineContext) throws EngineException;
	
	public void destroyWindowContext() throws EngineException;
	
	
	public IWindowContext getWindowContext();
	
	
	public boolean windowShouldClose();
	
	public void renderFrame();
	
	public void processWindowInput();
	
}
