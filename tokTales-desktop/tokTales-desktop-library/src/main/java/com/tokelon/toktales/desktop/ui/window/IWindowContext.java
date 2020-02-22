package com.tokelon.toktales.desktop.ui.window;

import com.tokelon.toktales.core.engine.EngineException;
import com.tokelon.toktales.core.engine.IEngineContext;
import com.tokelon.toktales.desktop.input.IDesktopInputDriver;
import com.tokelon.toktales.desktop.render.IWindowRenderer;

public interface IWindowContext {
	// TODO: Implement builder via factories with engineContext?


	public void create(IEngineContext engineContext) throws EngineException;
	
	public void destroy() throws EngineException;
	

	
	public IWindowFactory getWindowFactory();
	
	public IWindowToolkit getWindowToolkit();
	
	
	public IWindow getWindow();
	
	public IWindowRenderer getRenderer();
	
	public IDesktopInputDriver getInputDriver();
	
}
