package com.tokelon.toktales.android.render.opengl.program;

import com.tokelon.toktales.core.engine.render.ISurfaceController;

import android.view.MotionEvent;

public interface IOpenGLRenderer {


	/** Sets the name that will be used for the surface.
	 * <p>
	 * Only has an effect if called before {@link #onSurfaceCreated()}.
	 * 
	 * @param name
	 */
	public void setSurfaceName(String name);
	
	
	public void onSurfaceCreated();
	
	public void onSurfaceChanged(int width, int height);
	
	public void onDrawFrame();
	

	
	public void onResume();

	public void onPause();
	
	
	public boolean onTouch(MotionEvent motionEvent);
	
	
	public interface IOpenGLRendererFactory {
		
		public IOpenGLRenderer create(ISurfaceController surfaceController);
	}
	
}
