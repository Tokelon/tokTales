package com.tokelon.toktales.core.screen.view;

import com.tokelon.toktales.core.game.model.IPoint2f;
import com.tokelon.toktales.core.game.model.IRectangle2f;
import com.tokelon.toktales.core.game.model.IPoint2f.IMutablePoint2f;
import com.tokelon.toktales.core.game.model.IRectangle2f.IMutableRectangle2f;

/* Defines an area of the screen in pixel coordinates.
 * 
 * Note that while all methods use float values,
 * it is up to the implementation whether values will be used as is or rounded to decimal numbers. 
 */
public interface IScreenViewport {	// TODO: Rename to IViewport


	public static final int ORIENTATION_NONE = 0;
	public static final int ORIENTATION_PORTRAIT = 1;
	public static final int ORIENTATION_LANDSCAPE = 2;
	
	/** Returns the current orientation or this viewport.
	 * 
	 * @return {@link #ORIENTATION_PORTRAIT} if height > width, {@link #ORIENTATION_LANDSCAPE} if height < width, or {@link #ORIENTATION_NONE} if height == width.
	 */
	public int getOrientation();
	
	public float getWidth();
	public float getHeight();
	
	public float getHorizontalOffset();
	public float getVerticalOffset();
	
	public IRectangle2f getBounds();
	
	
	
	/** Transforms the horizontal value of a screen coordinate to it's value on the viewport.
	 * 
	 * @param sx
	 * @return X viewport value.
	 */
	public float transformX(float sx);
	
	/** Transforms the vertical value of a screen coordinate to it's value on the viewport.
	 * 
	 * @param sy
	 * @return Y viewport value.
	 */
	public float transformY(float sy);
	
	/** Transforms a screen coordinate to a viewport coordinate. 
	 * 
	 * @param sx
	 * @param sy
	 * @param vResult Returns a viewport coordinate.
	 */
	public void transform(float sx, float sy, IMutablePoint2f vResult);
	
	/** Same as {@link #transform(float, float, IMutablePoint2f)} for a point.
	 * 
	 * @param sPoint
	 * @param vResult Returns a viewport coordinate.
	 */
	public void transform(IPoint2f sPoint, IMutablePoint2f vResult);
	
	/** Same as {@link #transform(float, float, IMutablePoint2f)} for a rectangle.
	 * 
	 * @param sRect
	 * @param vResult Returns a viewport area.
	 */
	public void transform(IRectangle2f sRect, IMutableRectangle2f vResult);
	
	
	/** Resolves the horizontal value of a viewport coordinate to it's value on the screen.
	 * 
	 * @param vx
	 * @return X screen value.
	 */
	public float resolveX(float vx);
	
	/** Resolves the vertical value of a viewport coordinate to it's value on the screen.
	 * 
	 * @param vy
	 * @return Y screen value.
	 */
	public float resolveY(float vy);
	
	/** Resolves a viewport coordinate to a screen coordinate.
	 * 
	 * @param vx
	 * @param vy
	 * @param sResult Returns a screen coordinate.
	 */
	public void resolve(float vx, float vy, IMutablePoint2f sResult);
	
	/** Same as {@link #resolve(float, float, IMutablePoint2f)} for a point.
	 * 
	 * @param vPoint
	 * @param sResult Returns a screen coordinate.
	 */
	public void resolve(IPoint2f vPoint, IMutablePoint2f sResult);
	
	/** Same as {@link #resolve(float, float, IMutablePoint2f)} for a rectangle.
	 * 
	 * @param vRect
	 * @param sResult Returns a screen area.
	 */
	public void resolve(IRectangle2f vRect, IMutableRectangle2f sResult);
	
	
	
	// Already in IRectangle2i | use that I guess?
	//public boolean contains(int x, int y);
	//public boolean containsPoint(IPoint2i point);
	//public boolean containsRectangle(IRectangle2i rect);
	
}
