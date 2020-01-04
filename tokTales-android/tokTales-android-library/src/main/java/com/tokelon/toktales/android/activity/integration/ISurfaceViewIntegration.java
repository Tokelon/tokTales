package com.tokelon.toktales.android.activity.integration;

import com.tokelon.toktales.android.render.IRenderView;
import com.tokelon.toktales.tools.android.activity.integration.IActivityIntegration;

/** An activity integration that will create the main renderer for the engine,
 * by using a given surface view.
 */
public interface ISurfaceViewIntegration extends IActivityIntegration {

	
	/** Sets the name that will be used for the surface of this view.
	 * <p>
	 * To have an effect this method must be called before the graphics context has been created,
	 * so before {@link #integrateRenderView(IRenderView)} is called.
	 * 
	 * @param surfaceName
	 */
	public void setRenderSurfaceName(String surfaceName);
	
	
	/** Passes the integration renderer for this activity to the given render view.
	 * <p>
	 * The renderer will then create the graphics context.
	 * 
	 * @param renderView
	 */
	public void integrateRenderView(IRenderView renderView);

}
