package com.tokelon.toktales.android.activity.integration;

import com.tokelon.toktales.android.render.opengl.IGLRenderView;

/** An activity integration that will create the main renderer for the engine,
 * by using a given surface view.
 */
public interface ISurfaceViewIntegration extends IActivityIntegration {

	
	/** Passes the integration renderer for this activity to the given render view.
	 * <p>
	 * The renderer will then create the graphics context. 
	 * 
	 * @param renderView
	 */
	public void integrateRenderView(IGLRenderView renderView);

}
