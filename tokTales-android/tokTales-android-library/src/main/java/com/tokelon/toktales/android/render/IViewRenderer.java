package com.tokelon.toktales.android.render;

import com.tokelon.toktales.core.engine.render.ISurfaceController;

public interface IViewRenderer {


	/** Sets the name that will be used for the surface.
	 * <p>
	 * Only has an effect if called before {@link #onSurfaceCreated()}.
	 * 
	 * @param name
	 */
	public void setSurfaceName(String name);
	
	
	public void onSurfaceCreated();
	
	public void onSurfaceChanged(int width, int height);
	
	public void onSurfaceDestroyed();

	
	public void onDrawFrame();

	
	
	public interface IViewRendererFactory {
		
		public IViewRenderer create(ISurfaceController surfaceController);
	}
	
}
