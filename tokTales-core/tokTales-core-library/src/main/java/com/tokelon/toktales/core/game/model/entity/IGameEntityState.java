package com.tokelon.toktales.core.game.model.entity;

import com.tokelon.toktales.core.game.model.IPoint2f;
import com.tokelon.toktales.core.game.model.IRectangle2f;

public interface IGameEntityState extends IGameEntityProps {
	// Only points have *X and *Y methods because often these methods are used independently of each other
	// They are also needed because there are no 'back' methods for these

	// Add 'back' version to speed, velocity etc?
	

	/* Add these? What about the other speed, size etc? -> If anything add to IGameEntity
	// IVelocityModifier?
	public void modifyVelocity(float dx, float dy);
	public float modifyVelocityX(float dx);
	public float modifyVelocityY(float dy);
	*/
	
	
	
	public void setActive(boolean isActive);
	public void setVisible(boolean isVisible);
	
	
	public void setWidth(float width);
	public void setHeight(float height);
	public void setSize(float width, float height);


	public void setSpeedX(float sx);
	public void setSpeedY(float sy);
	public void setSpeed(float sx, float sy);
	
	
	public void setVelocityX(float vx);
	public void setVelocityY(float vy);
	public void setVelocity(float vx, float vy);

	
	public void setOrigin(float originx, float originy);
	
	public void setCollisionBox(float left, float top, float right, float bottom);
	public void setCollisionBox(IRectangle2f box);
	
	public void setWorldCoordinates(float worldX, float worldY);
	public void setWorldCoordinates(IPoint2f worldCoords);
	
}
