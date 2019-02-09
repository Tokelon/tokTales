package com.tokelon.toktales.core.game.model.entity;

import com.tokelon.toktales.core.game.model.IPoint2f;
import com.tokelon.toktales.core.game.model.IPoint2f.IMutablePoint2f;
import com.tokelon.toktales.core.game.model.IRectangle2f;
import com.tokelon.toktales.core.game.model.IRectangle2f.IMutableRectangle2f;
import com.tokelon.toktales.core.game.model.Point2fImpl;
import com.tokelon.toktales.core.game.model.Rectangle2fImpl;

public class GameEntityModel implements IGameEntityModel {
	
	
	private boolean entityActive = true;
	private boolean entityVisible = true;

	private float entityWidth = 0.0f;
	private float entityHeight = 0.0f;

	private float entitySpeedX = 0.0f;
	private float entitySpeedY = 0.0f;
	
	//private float mRotation = 0.0f;

	
	private final Point2fImpl entityOrigin = new Point2fImpl();
	private final Point2fImpl entityCoordinates = new Point2fImpl();
	private final Point2fImpl entityVelocity = new Point2fImpl();

	private final Rectangle2fImpl entityBounds = new Rectangle2fImpl();	
	private final Rectangle2fImpl entityCollisionBox = new Rectangle2fImpl();
	private final Rectangle2fImpl entityCollisionBounds = new Rectangle2fImpl();


	
	@Override
	public boolean isActive() {
		return entityActive;
	}

	@Override
	public boolean isVisible() {
		return entityVisible;
	}
	
	@Override
	public float getHeight() {
		return entityHeight;
	}
	
	@Override
	public float getWidth() {
		return entityWidth;
	}
	
		
	@Override
	public IMutableRectangle2f getBounds(IMutableRectangle2f result) {
		return result.set(entityBounds);
	}
	
	@Override
	public IRectangle2f getBoundsBack() {
		return entityBounds;
	}

	
	@Override
	public IMutableRectangle2f getCollisionBounds(IMutableRectangle2f result) {
		return result.set(entityCollisionBounds);
	}
	
	@Override
	public IRectangle2f getCollisionBoundsBack() {
		return entityCollisionBounds;
	}
	
	
	@Override
	public IMutableRectangle2f getCollisionBox(IMutableRectangle2f result) {
		return result.set(entityCollisionBox);
	}
	
	@Override
	public IRectangle2f getCollisionBoxBack() {
		return entityCollisionBox;
	}

	
	@Override
	public float getOriginX() {
		return entityOrigin.x;
	}
	
	@Override
	public float getOriginY() {
		return entityOrigin.y;
	}
	
	@Override
	public IMutablePoint2f getOrigin(IMutablePoint2f result) {
		return result.set(entityOrigin);
	}
	
	
	@Override
	public float getRawWorldX() {
		return entityCoordinates.x - entityOrigin.x;
	}
	
	@Override
	public float getRawWorldY() {
		return entityCoordinates.y - entityOrigin.y;
	}
	
	@Override
	public IMutablePoint2f getRawWorldCoordinates(IMutablePoint2f result) {
		return result.set(getRawWorldX(), getRawWorldY());
	}
	

	@Override
	public float getWorldX() {
		return entityCoordinates.x;
	}

	@Override
	public float getWorldY() {
		return entityCoordinates.y;
	}

	@Override
	public IMutablePoint2f getWorldCoordinates(IMutablePoint2f result) {
		return result.set(entityCoordinates);
	}

	
	@Override
	public float getSpeedX() {
		return entitySpeedX;
	}
	
	@Override
	public float getSpeedY() {
		return entitySpeedY;
	}
	
	
	@Override
	public float getVelocityX() {
		return entityVelocity.x;
	}
	
	@Override
	public float getVelocityY() {
		return entityVelocity.y;
	}

	@Override
	public IMutablePoint2f getVelocity(IMutablePoint2f result) {
		return result.set(entityVelocity);		
	}
	
	
	

	@Override
	public void setActive(boolean isActive) {
		this.entityActive = isActive;
	}
	
	@Override
	public void setVisible(boolean isVisible) {
		this.entityVisible = isVisible;
	}

	
	@Override
	public void setWidth(float width) {
		this.entityWidth = width;
		entityBounds.setWidth(width);
	}
	
	@Override
	public void setHeight(float height) {
		this.entityHeight = height;
		entityBounds.setHeight(height);
	}
	
	@Override
	public void setSize(float width, float height) {
		this.entityWidth = width;
		this.entityHeight = height;
		
		entityBounds.setWidth(width);
		entityBounds.setHeight(height);
	}


	@Override
	public void setSpeed(float sx, float sy) {
		entitySpeedX = sx;
		entitySpeedY = sy;
	}
	
	@Override
	public void setSpeedX(float sx) {
		entitySpeedX = sx;
	}
	
	@Override
	public void setSpeedY(float sy) {
		entitySpeedY = sy;
	}
	

	@Override
	public void setVelocityX(float vx) {
		entityVelocity.setX(vx);
	}
	
	@Override
	public void setVelocityY(float vy) {
		entityVelocity.setY(vy);
	}
	
	@Override
	public void setVelocity(float vx, float vy) {
		entityVelocity.set(vx, vy);
	}
	

	@Override
	public void setOrigin(float originx, float originy) {
		this.entityOrigin.set(originx, originy);
	}
	
	
	@Override
	public void setCollisionBox(float left, float top, float right, float bottom) {
		this.entityCollisionBox.set(left, top, right, bottom);
		
		entityCollisionBounds.set(entityCollisionBox.left() + entityBounds.left(), entityCollisionBox.top() + entityBounds.top(), entityCollisionBox.right() + entityBounds.left(), entityCollisionBox.bottom() + entityBounds.top());
	}
	
	@Override
	public void setCollisionBox(IRectangle2f box) {
		this.entityCollisionBox.set(box);
		
		entityCollisionBounds.set(entityCollisionBox.left() + entityBounds.left(), entityCollisionBox.top() + entityBounds.top(), entityCollisionBox.right() + entityBounds.left(), entityCollisionBox.bottom() + entityBounds.top());
	}
	
	
	@Override
	public void setWorldCoordinates(IPoint2f worldCoords) {
		setWorldCoordinatesInternal(worldCoords.x(), worldCoords.y());
	}
	
	@Override
	public void setWorldCoordinates(float worldX, float worldY) {
		setWorldCoordinatesInternal(worldX, worldY);
	}
	
	private void setWorldCoordinatesInternal(float worldX, float worldY) {
		entityCoordinates.set(worldX, worldY);
		
		entityBounds.moveTo(entityCoordinates.x - entityOrigin.x, entityCoordinates.y - entityOrigin.y);
		entityCollisionBounds.set(entityCollisionBox.left() + entityBounds.left(), entityCollisionBox.top() + entityBounds.top(), entityCollisionBox.right() + entityBounds.left(), entityCollisionBox.bottom() + entityBounds.top());
	}
	

	/*
	@Override
	public void setRotation(float rotation) {
		// Rotation will not change the position, the bounds or collision (probably)
	
		this.mRotation = rotation % 360f;
		
		/*
		// It does change your coordinates
		float rotLeft = 
		float rotX = mCoords.x - mOrigin.x Math.
		
		Math.toRadians(rotation);
		Math.sin
		mBounds.moveTo(mCoords.x - mOrigin.x, mCoords.y - mOrigin.y);
		mCollisionBounds.set(mCollisionBox.left + mBounds.left, mCollisionBox.top + mBounds.top, mCollisionBox.right + mBounds.left, mCollisionBox.bottom + mBounds.top);
		*//*
		
		//TODO: How to do rotation
		
		getParticipation().notifyOfChange(CHANGE_ENTITY_ROTATION);
		// WHat about bounds and collision bounds ? they need notifications too ?
	}
	*/
	
}
