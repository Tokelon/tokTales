package com.tokelon.toktales.desktop.render;

import com.tokelon.toktales.core.engine.render.ISurface;
import com.tokelon.toktales.desktop.ui.window.IWindow;

public interface IDesktopOpenGLRenderer { // TODO: Rename to IWindowRenderer
	// TODO: Handle surface changes


	public ISurface create(IWindow window);
	
	public void destroy();
	
	
	public void prepareFrame();
	
	public void drawFrame();
	
	public void commitFrame();
	
	
	public IWindow getWindow();
	
	
	//public void onSurfaceChanged();
	
}
