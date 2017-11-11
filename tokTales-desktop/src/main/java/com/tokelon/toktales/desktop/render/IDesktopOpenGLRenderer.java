package com.tokelon.toktales.desktop.render;


public interface IDesktopOpenGLRenderer {

	
	//public void onWindowCreated(LWJGLWindow window);	// Cannot have LWJGLWindow here, no abstraction
	
	//public void onSurfaceChanged();

	
	public void onDrawFrame();
	
}
