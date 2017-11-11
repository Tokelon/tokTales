package com.tokelon.toktales.core.render.shapes;

public class BaseShape {


	private float size = 1.0f;
	
	private float positionX = 0.0f;
	private float positionY = 0.0f;
	
	
	/** Sets the shape scale to the given size.
	 * <br><br>
	 * Use this if your shape values are normalized (0.0 - 1.0)
	 * 
	 * @param size
	 */
	public void setSize(float size) {
		this.size = size;
	}

	/** Adds the given size values to the current size.
	 * 
	 * @param relSize
	 */
	public void setRelativeSize(float relSize) {
		this.size += relSize;
	}
	
	
	/** Sets the shape position to the given coordinates.
	 * 
	 * The position defines the top-left corner of the shape and is unaffected by the shape size. 
	 * 
	 * @param wx
	 * @param wy
	 */
	public void setPosition(float wx, float wy) {
		this.positionX = wx;
		this.positionY = wy;
	}
	
	/** Adds the given coordinate values to the current coordinates.
	 * 
	 * @param wxRel
	 * @param wyRel
	 */
	public void setRelativePosition(float wxRel, float wyRel) {
		this.positionX += wxRel;
		this.positionY += wyRel;
	}
	

	/** The default value is 1.0.
	 * 
	 * @return The current size.
	 */
	public float getSize() {
		return size;
	}

	/** The default value is 0.0.
	 * 
	 * @return The x coordinate of the current position.
	 */
	public float getPositionX() {
		return positionX;
	}

	/** The default value is 0.0.
	 * 
	 * @return The y coordinate of the current position.
	 */
	public float getPositionY() {
		return positionY;
	}
	
	
}
