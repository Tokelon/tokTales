package com.tokelon.toktales.android.render;

import com.tokelon.toktales.core.render.ISurfaceManager;

public interface IViewRenderer {
	//Add these?
	//public void onPrepareFrame();
	//public void onCommitFrame();


	public void onSurfaceCreated(ISurfaceManager surfaceManager);
	
	public void onSurfaceChanged(int width, int height);
	
	public void onSurfaceDestroyed();

	
	public void onDrawFrame();

}
