package com.tokelon.toktales.android.render.tools;

import android.graphics.Rect;

/* This overlay supports one cross control and multiple buttons of which only one can be pressed at a time.
 * 
 */
public interface IUIOverlay {

	
	// TODO: Change these to strings

	public static final int BUTTON_NONE = 11;
	public static final int BUTTON_A = 12;
	public static final int BUTTON_B = 13;
	
	public static final int BUTTON_SP1 = 21;
	public static final int BUTTON_SET = 22;
	
	
	public ICrossControlOverlayView getCrossControlView();
	
	public IButtonOverlayView getButtonView(int button);
	

	
	
	
	/** Note that the returned values do not define the button's shape, only the button's bounds.
	 * 
	 * @param button The button which bounds should be returned.
	 * @return A new rectangle containing the bounds for the given button.
	 * @throws IllegalArgumentException If button is not one of the known button codes.
	 */
	public Rect getButtonBounds(int button);

	/** Writes the bounds for the given button into the given rectangle.<br><br>
	 * Note that the returned values do not define the button's shape, only the button's bounds.
	 * 
	 * @param button The button which bounds should be returned.
	 * @param result The rectangle to use for the result.
	 * @throws IllegalArgumentException If button is not one of the known button codes.
	 */
	public void getButtonBounds(int button, Rect result);
	
	
	
	/** Find out if a given point is inside a button's bounds.
	 * 
	 * @param button The button which bounds will be checked.
	 * @param x The horizontal value of the point.
	 * @param y The vertical value of the point.
	 * @return True if the point is on the given button.
	 * @throws IllegalArgumentException If button is not one of the known button codes.
	 */
	public boolean isInButtonBounds(int button, int x, int y);
	
	
	/** Get the button this point is on.<br><br>
	 * 
	 * Following values can be returned:<br>
	 * - {@link BUTTON_OUTSIDE} If the point is not inside any button.<br>
	 * Otherwise one of the button codes {@link BUTTON_A}, {@link BUTTON_B}.<br> 
	 * 
	 * 
	 * @param x The horizontal value of the point.
	 * @param y The vertical value of the point.
	 * @return The code of the button this point is on.
	 */
	public int getButton(int x, int y);
	
	
}
