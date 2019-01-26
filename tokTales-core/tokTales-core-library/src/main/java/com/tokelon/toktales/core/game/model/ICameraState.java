package com.tokelon.toktales.core.game.model;

public interface ICameraState extends ICameraProps {


	/** Size is in world units. Resets the zoom to 1.0.
	 * 
	 * @param width
	 * @param height
	 */
	public void setSize(float width, float height);		// Same problem with centering ! Should we add a center boolean?
	
	// We set the zoom value here but what about centering ? (Its not happening)
	//public void setSize(float width, float height, float zoom);
	
	
	public void setPortraitOrientation(boolean portrait);
	
	/**
	 * @param ratio A value >= 1.0
	 */
	public void setAspectRatio(float ratio);
	
	/**
	 * @param zoom A value > 0. Default is 1.0.
	 * @param center
	 */
	public void setZoom(float zoom, boolean center);
	

	public void setSpeedX(float sx);
	public void setSpeedY(float sy);
	public void setSpeed(float sx, float sy);

	public void setVelocityX(float vx);
	public void setVelocityY(float vy);
	public void setVelocity(float vx, float vy);
	
	
	public void setWorldCoordinates(float worldX, float worldY);
	public void setWorldCoordinates(IPoint2f worldCoords);
	
}
