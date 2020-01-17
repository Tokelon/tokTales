package com.tokelon.toktales.desktop.render;

import com.tokelon.toktales.desktop.ui.window.IWindow;

public interface IWindowRenderer {


	/*
	 * Must be called from the main thread.
	 */
	public void create(IWindow window);
	
	/*
	 * Must be called from the thread that the renderer will be associated to.
	 */
	public void createContext(); // TODO: Add ISurfaceManager here, similar to IViewRenderer?
	
	public void destroyContext();
	
	public void destroy();
	
	
	public void prepareFrame();
	
	public void drawFrame();
	
	public void commitFrame();
	
	
	public IWindow getWindow();
	
}
