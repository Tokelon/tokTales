package com.tokelon.toktales.android.render.tools;

import android.graphics.Rect;

public interface ICrossControlOverlayView extends IUIOverlayView {

	public static final int CROSS_OUTSIDE = 1;
	public static final int CROSS_CONTROL_NONE = 2;
	public static final int CROSS_CONTROL_LEFT = 3;
	public static final int CROSS_CONTROL_UP = 4;
	public static final int CROSS_CONTROL_RIGHT = 5;
	public static final int CROSS_CONTROL_DOWN = 6;
	
	
	/**
	 * @return A new rectangle containing the bounds of the cross.
	 */
	public Rect getCrossBounds();
	
	/** Writes the bounds of the cross into the given rectangle.
	 * 
	 * @param result The rectangle to use for the result.
	 */
	public void getCrossBounds(Rect result);
	
	
	
	/**
	 * @param crossControl The control which bounds should be returned.
	 * @return A new rectangle containing the bounds for the given cross control.
	 * @throws IllegalArgumentException If crossControl is not one of the known cross control codes.
	 */
	public Rect getControlBounds(int crossControl);
	
	/** Writes the bounds for the given cross control into the given rectangle.
	 * 
	 * @param crossControl The control which bounds should be returned. 
	 * @param result The rectangle to use for the result.
	 * @throws IllegalArgumentException If crossControl is not one of the known cross control codes.
	 */
	public void getControlBounds(int crossControl, Rect result);
	
	

	
	/** Find out if a given point is inside the cross bounds.
	 * 
	 * @param x The horizontal value of the point.
	 * @param y The vertical value of the point.
	 * @return True if the point is on the cross, false if not.
	 */
	//public boolean isInCrossBounds(int x, int y);		// Replaced by containsPoint()
	
	
	
	/** Get the cross control this point is on.<br><br>
	 * 
	 * Following values can be returned:<br>
	 * - {@link CROSS_OUTSIDE} If the point is not inside the cross bounds at all.<br>
	 * - {@link CROSS_CONTROL_NONE} If the point is inside the cross bounds but on none of the controls.<br>
	 * - Otherwise one of the control codes {@link CROSS_LEFT}, {@link CROSS_UP}, {@link CROSS_RIGHT}, {@link CROSS_DOWN}.<br>
	 * 
	 * @param x The horizontal value of the point.
	 * @param y The vertical value of the point.
	 * @return The code of the cross control this point is on.
	 */
	public int getControlInCross(int x, int y);

}
