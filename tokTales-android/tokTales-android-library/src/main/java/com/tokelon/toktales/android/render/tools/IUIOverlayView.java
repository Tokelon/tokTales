package com.tokelon.toktales.android.render.tools;

public interface IUIOverlayView {

	
	/** Returns whether the given point is in this view's bounds. 
	 * 
	 * @param x The horizontal value of the point.
	 * @param y The vertical value of the point.
	 * @return True if this view contains the point, false if not.
	 */
	public boolean containsPoint(int x, int y);
	
	
	// TODO: Implement this ?
	//public Rect getViewBounds();
	
}
