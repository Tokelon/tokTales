package com.tokelon.toktales.desktop.ui.window;

import com.tokelon.toktales.core.engine.IEngineContext;

public interface IWindowHandler {


	public void createWindowContext(IEngineContext engineContext);
	
	public void destroyWindowContext();
	
	
	public IWindowContext getWindowContext();
	
	
	public boolean windowShouldClose();
	
	public void renderFrame();
	
	public void processWindowInput();
	
}
