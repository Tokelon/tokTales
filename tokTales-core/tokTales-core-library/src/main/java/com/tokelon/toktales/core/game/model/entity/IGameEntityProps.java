package com.tokelon.toktales.core.game.model.entity;

import com.tokelon.toktales.core.game.model.IRectangle2f;
import com.tokelon.toktales.core.game.model.IPoint2f.IMutablePoint2f;
import com.tokelon.toktales.core.game.model.IRectangle2f.IMutableRectangle2f;

public interface IGameEntityProps {
	// TODO: Add *back version to the methods that do not have it?
	// TODO: Add rotation
	// TODO: Add looking direction?
	

	public boolean isActive();
	public boolean isVisible();

	public float getHeight();
	public float getWidth();
	
	public float getSpeedX();
	public float getSpeedY();
	

	public float getVelocityX();
	public float getVelocityY();
	
	/** The velocity of an entity defines the speed and velocity currently applied.
	 * 
	 * @param result Where the result will be stored.
	 * @return The given object containing the entity velocity.
	 */
	public IMutablePoint2f getVelocity(IMutablePoint2f result);	// IPoint2f is not perfect, IVector2f would be more fitting
	
	
	
	// The reason I added the postfix 'back' to the "normal" getters, is to discourage wide use in favor of the parameterized versions
	
	/** The bounds of an entity designate the area it is covering. 
	 * 
	 * @param result Where the result will be stored.
	 * @return The given object containing the entity bounds.
	 */
	public IMutableRectangle2f getBounds(IMutableRectangle2f result);
	
	/** Useful when the result will not be assigned.
	 * <br><br>
	 * <b>Caution:</b> The result may be modified over time.
	 * <br>
	 * Preferable use of {@link #getBounds(IMutableRectangle2f)} is recommended.
	 * 
	 * @return The backing object for the entity bounds.
	 */
	public IRectangle2f getBoundsBack();


	/** The collision bounds of an entity designate the area where it's collision applies.
	 * 
	 * @param result Where the result will be stored.
	 * @return The given object containing the entity collision bounds.
	 */
	public IMutableRectangle2f getCollisionBounds(IMutableRectangle2f result);
	
	/** Useful when the result will not be assigned.
	 * <br><br>
	 * <b>Caution:</b> The result may be modified over time.
	 * <br>
	 * Preferable use of {@link #getCollisionBounds(IMutableRectangle2f)} is recommended.
	 * 
	 * @return The backing object for the entity collision bounds.
	 */
	public IRectangle2f getCollisionBoundsBack();
	
	
	/** The collision box of an entity designates the size and relative position where collision will be applied.
	 * 
	 * @param result Where the result will be stored.
	 * @return The given object containing the entity collision box.
	 */
	public IMutableRectangle2f getCollisionBox(IMutableRectangle2f result);
	
	/** Useful when the result will not be assigned.
	 * <br><br>
	 * <b>Caution:</b> The result may be modified over time.
	 * <br>
	 * Preferable use of {@link #getCollisionBox(IMutableRectangle2f)} is recommended.
	 * 
	 * @return The backing object for the entity collision box.
	 */
	public IRectangle2f getCollisionBoxBack();
	
	
	public float getOriginX();
	public float getOriginY();
	
	/** The origin of an entity is the point which added to the raw coordinates,
	 * designates the center of the entity and it's world coordinates. 
	 * 
	 * @param result Where the result will be stored.
	 * @return The given object containing the entity origin.
	 */
	public IMutablePoint2f getOrigin(IMutablePoint2f result);
	
	
	public float getRawWorldX();
	public float getRawWorldY();
	
	/** The raw coordinates of an entity point to the top left corner of it's bounds.  
	 * 
	 * @param result Where the result will be stored.
	 * @return The given object containing the entity raw coordinates.
	 */
	public IMutablePoint2f getRawWorldCoordinates(IMutablePoint2f result);

	
	public float getWorldX();
	public float getWorldY();
	
	/** The world coordinates of an entity point to the center of it's bounds.  
	 * 
	 * @param result Where the result will be stored.
	 * @return The given object containing the entity world coordinates.
	 */
	public IMutablePoint2f getWorldCoordinates(IMutablePoint2f result);
	
}
