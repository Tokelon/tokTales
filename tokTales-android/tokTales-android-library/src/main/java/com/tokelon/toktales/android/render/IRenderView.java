package com.tokelon.toktales.android.render;

import com.tokelon.toktales.core.engine.render.ISurfaceController;

public interface IRenderView extends ISurfaceController {
	//public boolean fireTouchEvent(MotionEvent event); // Add this?



	/** Callback for when this render view is resumed.
	 */
	public void onResume();
	
	/** Callback for when this render view is paused.
	 */
	public void onPause();
	

	/** Sets the view adapter that rendering should be delegated to.
	 * <p>
	 * This method can be called in any state of the view.
	 * 
	 * @param adapter
	 */
	public void setRenderViewAdapter(IRenderViewAdapter adapter);

	
	/** Sets the view name.
	 * <p>
	 * Must be called before {@link #setRenderViewAdapter(IRenderViewAdapter)} to have an effect.
	 * 
	 * @param name
	 */
	public void setViewName(String name);
	
	/**
	 * @return The view name, or null if none has been set.
	 */
	public String getViewName();

}