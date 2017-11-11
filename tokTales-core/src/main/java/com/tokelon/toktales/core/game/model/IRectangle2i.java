package com.tokelon.toktales.core.game.model;

/** An int precision rectangle in 2D space.<br><br>
 * 
 * Assumes <b>positive Y-Axis goes DOWN</b> and <b>values include 0</b>.
 * <br>
 * That means it's origin lies in the top left corner.
 * <br><br>
 * Example:<br>
 * &emsp;&emsp;&emsp; top=0
 * <br>
 * left=0
 * &emsp;&emsp;&emsp;&emsp;&emsp; right=64
 * <br>
 * &emsp;&emsp;&emsp; bottom=32
 * <br><br>
 * Width = 64
 * <br>
 * Height = 32
 * <br>
 * 
 * Rectangle <b>does</b> contain (0, 0)
 * <br>
 * Rectangle <b>does not</b> contain (0, 32) or (64, 0)
 *
 */
public interface IRectangle2i {

	
	/** Same as {@link #getLeft()}.
	 * 
	 * @return
	 */
	public int left();
	
	/** Same as {@link #getTop()}.
	 * 
	 * @return
	 */
	public int top();
	
	/** Same as {@link #getRight()}.
	 * 
	 * @return
	 */
	public int right();
	
	/** Same as {@link #getBottom()}.
	 * 
	 * @return
	 */
	public int bottom();
	
	
	/** Same as {@link #getWidth()}.
	 * 
	 * @return
	 */
	public int width();
	
	/** Same as {@link #getHeight()}.
	 * 
	 * @return
	 */
	public int height();
	
	
	public int getLeft();
	public int getTop();
	public int getRight();
	public int getBottom();
	
	public int getWidth();
	public int getHeight();
	
	//public int getCenter();
	//public float getPreciseCenter();
	
	
	/** 
	 * A rectangle <b>does contains</b> it's own left and top sides.<br>
	 * A rectangle <b>does not contain</b> it's own right and bottom sides.
	 * 
	 * @param x
	 * @param y
	 * @return True if this rectangle contains the given point, false if not.
	 */
	public boolean contains(int x, int y);
	
	/**
	 * A rectangle <b>does contain</b> it's own bounds.
	 * 
	 * @param left
	 * @param top
	 * @param right
	 * @param bottom
	 * @return True if this rectangle contains the given bounds, false if not.
	 */
	public boolean contains(int left, int top, int right, int bottom);
	
	/**
	 * A rectangle <b>does contain</b> itself.
	 * 
	 * @param rectangle
	 * @return True if this rectangle contains the given rectangle, false it not.
	 */
	public boolean contains(IRectangle2i rectangle);
	
	
	public boolean intersects(int left, int top, int right, int bottom);
	public boolean intersects(IRectangle2i rectangle);
	
	
	/**
	 * Assumes left < right and top < bottom.
	 * 
	 * @return True if left >= right and top >= bottom, false if not.
	 */
	public boolean isEmpty();
	
	
	public interface IMutableRectangle2i extends IRectangle2i {
		
		public IMutableRectangle2i setLeft(int left);
		public IMutableRectangle2i setTop(int top);
		public IMutableRectangle2i setRight(int right);
		public IMutableRectangle2i setBottom(int bottom);
		
		
		public IMutableRectangle2i set(int left, int top, int right, int bottom);
		public IMutableRectangle2i set(IRectangle2i rectangle);

		public IMutableRectangle2i setWidth(int width);
		public IMutableRectangle2i setHeight(int height);
		
		
		public IMutableRectangle2i moveTo(int x, int y);
		public IMutableRectangle2i moveBy(int dx, int dy);
		
		
	}
	
}
