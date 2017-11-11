package com.tokelon.toktales.android.render.tools;

import android.graphics.Rect;

public interface IButtonOverlayView extends IUIOverlayView {

	
	/** Note that these bounds do not define the shape of a button, which is implementation specific.
	 * 
	 * @return The view bounds for this button.
	 */
	public Rect getButtonBounds();
	
	/** Writes the view bounds for this button into the given rectangle.<br><br>
	 * Note that these bounds do not define the shape of a button, which is implementation specific.
	 * 
	 * @param result The rectangle to use for the result.
	 */
	public void getButtonBounds(Rect result);
	
	
	public int getButtonID();

}
