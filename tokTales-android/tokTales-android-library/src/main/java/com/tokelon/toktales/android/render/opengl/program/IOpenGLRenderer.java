package com.tokelon.toktales.android.render.opengl.program;

import android.view.MotionEvent;

public interface IOpenGLRenderer {

	public void onSurfaceCreated();
	
	public void onSurfaceChanged(int width, int height);
	
	public void onDrawFrame();
	

	
	public void onResume();

	public void onPause();
	
	
	public boolean onTouch(MotionEvent motionEvent);
	
}
