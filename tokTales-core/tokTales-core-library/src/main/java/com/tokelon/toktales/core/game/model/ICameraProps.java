package com.tokelon.toktales.core.game.model;

import com.tokelon.toktales.core.game.model.IPoint2f.IMutablePoint2f;
import com.tokelon.toktales.core.game.model.IRectangle2f.IMutableRectangle2f;

public interface ICameraProps {

	
	/**
	 * @return A value >= 1.0
	 */
	public float getAspectRatio();
	
	
	/**
	 * @return A value > 0.0
	 */
	public float getZoom();

	
	public boolean hasPortraitOrientation();

	
	public float getWidth();
	public float getHeight();

	public float getSpeedX();
	public float getSpeedY();

	
	public float getOriginX();
	public float getOriginY();
	public IMutablePoint2f getOrigin(IMutablePoint2f result);
	
	public float getVelocityX();
	public float getVelocityY();
	public IMutablePoint2f getVelocity(IMutablePoint2f result);
	
	
	public float getRawWorldX();
	public float getRawWorldY();
	public IMutablePoint2f getRawWorldCoordinates(IMutablePoint2f result);
	
	public float getWorldX();
	public float getWorldY();
	public IMutablePoint2f getWorldCoordinates(IMutablePoint2f result);

	
	public IMutableRectangle2f getWorldBounds(IMutableRectangle2f result);
	
	/** Useful when the result will not be assigned.
	 * <br><br>
	 * <b>Caution:</b> The result may be modified over time.
	 * <br>
	 * Preferable use of {@link #getWorldBounds(IMutableRectangle2f)} is recommended.
	 * 
	 * @return The backing object for the camera bounds.
	 */
	public IRectangle2f getWorldBoundsBack();
	
}
