package com.tokelon.toktales.android.render;

import com.tokelon.toktales.core.screen.surface.ISurfaceHandler;

public interface IViewRenderer {
	//Add these?
	//public void onPrepareFrame();
	//public void onCommitFrame();


	public void onSurfaceCreated(ISurfaceHandler surfaceHandler);
	
	public void onSurfaceChanged(int width, int height);
	
	public void onSurfaceDestroyed();

	
	public void onDrawFrame();

}
