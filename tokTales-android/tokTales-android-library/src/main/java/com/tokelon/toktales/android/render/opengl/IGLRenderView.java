package com.tokelon.toktales.android.render.opengl;

import com.tokelon.toktales.android.render.opengl.program.IOpenGLRenderer;

import android.view.MotionEvent;

public interface IGLRenderView {

	
	public void setMainRenderer(IOpenGLRenderer mainRenderer);

	public void onPause();

	public void onResume();

	public boolean onTouchEvent(MotionEvent event);

	
	public void queueEvent(Runnable event);
	
}