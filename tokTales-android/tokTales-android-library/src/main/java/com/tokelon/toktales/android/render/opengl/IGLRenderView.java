package com.tokelon.toktales.android.render.opengl;

import com.tokelon.toktales.android.render.opengl.program.IOpenGLRenderer;

import android.view.MotionEvent;

public interface IGLRenderView {


	public void setMainRenderer(IOpenGLRenderer mainRenderer);

	/** Call before renderer is set for immediate effect.
	 * 
	 * @param debugFlags
	 */
	public void setDebugFlags(int debugFlags);

	
	public void onResume();
	public void onPause();

	public boolean onTouchEvent(MotionEvent event);

	
	public void queueEvent(Runnable event);
	
}