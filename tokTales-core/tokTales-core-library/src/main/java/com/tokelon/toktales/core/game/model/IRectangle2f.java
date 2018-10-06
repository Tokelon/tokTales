package com.tokelon.toktales.core.game.model;

/** A float precision rectangle in 2D space.<br><br>
 * 
 * Assumes <b>positive Y-Axis goes DOWN</b> and <b>values include 0f</b>.
 * <br>
 * That means it's origin lies in the top left corner.
 * <br><br>
 * Example:<br>
 * &emsp;&emsp;&emsp; top=0f
 * <br>
 * left=0f
 * &emsp;&emsp;&emsp;&emsp;&emsp; right=64f
 * <br>
 * &emsp;&emsp;&emsp; bottom=32f
 * <br><br>
 * Width = 64f
 * <br>
 * Height = 32f
 * <br>
 * 
 * Rectangle <b>does</b> contain (0f, 0f)
 * <br>
 * Rectangle <b>does not</b> contain (0f, 32f) or (64f, 0f)
 *
 */
public interface IRectangle2f {

	/* TODO: Important - Implement
	 * equals and hashCode similar to IPoint2f
	 * 
	 */
	
	/** Same as {@link #getLeft()}
	 * 
	 * @return
	 */
	public float left();
	
	/** Same as {@link #getTop()}
	 * 
	 * @return
	 */
	public float top();
	
	/** Same as {@link #getRight()}
	 * 
	 * @return
	 */
	public float right();
	
	/** Same as {@link #getBottom()}
	 * 
	 * @return
	 */
	public float bottom();
	
	
	/** Same as {@link #getWidth()}
	 * 
	 * @return
	 */
	public float width();
	
	/** Same as {@link #getHeight()}
	 * 
	 * @return
	 */
	public float height();
	
	
	public float getLeft();
	public float getTop();
	public float getRight();
	public float getBottom();
	
	
	public float getWidth();
	public float getHeight();
	
	//public float getCenter();
	
	
	/** 
	 * A rectangle <b>does contains</b> it's own left and top sides.<br>
	 * A rectangle <b>does not contain</b> it's own right and bottom sides.
	 * 
	 * @param x
	 * @param y
	 * @return True if this rectangle contains the given point, false if not.
	 */
	public boolean contains(float x, float y);
	
	/**
	 * A rectangle <b>does contain</b> it's own bounds.
	 * 
	 * @param left
	 * @param top
	 * @param right
	 * @param bottom
	 * @return True if this rectangle contains the given bounds, false if not.
	 */
	public boolean contains(float left, float top, float right, float bottom);
	
	/**
	 * A rectangle <b>does contain</b> itself.
	 * 
	 * @param rectangle
	 * @return True if this rectangle contains the given rectangle, false it not.
	 */
	public boolean contains(IRectangle2f rectangle);
	
	
	public boolean intersects(float left, float top, float right, float bottom);
	public boolean intersects(IRectangle2f rectangle);

	
	/** Use with caution.<br><br>
	 * Compares left, right and top, bottom as float values.<br>
	 * Assumes left < right and top < bottom.
	 * 
	 * @return True if left >= right and top >= bottom, false if not.
	 */
	public boolean isEmpty();
	//public boolean isAlmostEmpty(); // add compare with epsilon?
	
	// TODO:
	//public IRectangle2i asRectangle2i();
	
	public interface IMutableRectangle2f extends IRectangle2f {
	
		public IMutableRectangle2f setLeft(float left);
		public IMutableRectangle2f setTop(float top);
		public IMutableRectangle2f setRight(float right);
		public IMutableRectangle2f setBottom(float bottom);
		
		
		public IMutableRectangle2f set(float left, float top, float right, float bottom);
		public IMutableRectangle2f set(IRectangle2f rectangle);

		public IMutableRectangle2f setWidth(float width);
		public IMutableRectangle2f setHeight(float height);
		
		
		public IMutableRectangle2f moveTo(float x, float y);
		public IMutableRectangle2f moveBy(float dx, float dy);
		
	}
	
}
