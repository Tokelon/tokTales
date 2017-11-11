package com.tokelon.toktales.core.game.logic.motion;


public interface IGameMotion extends IBaseMotion {

	
	public void stopMotion();
	public void resetMotion();
	public boolean isMotionStopped();
	
	
	public void setVelocity(float velocityHorizontal, float velocityVertical);
	public float getHorizontalVelocity();
	public float getVerticalVelocity();
	
	
	public void setOrigin(float originx, float originy);
	public void removeOrigin();
	public float getOriginX();
	public float getOriginY();
	public boolean hasOrigin();
	
	public void setDestination(float destinationx, float destinationy);
	public void removeDestination();
	public float getDestinationX();
	public float getDestinationY();
	public boolean hasDestination();
	
}
