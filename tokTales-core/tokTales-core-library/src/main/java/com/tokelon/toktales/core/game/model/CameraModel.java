package com.tokelon.toktales.core.game.model;

import javax.inject.Inject;

import com.tokelon.toktales.core.game.model.IPoint2f.IMutablePoint2f;
import com.tokelon.toktales.core.game.model.IRectangle2f.IMutableRectangle2f;

public class CameraModel implements ICameraModel {

	
	private float cameraWidth = 0.0f;
	private float cameraHeight = 0.0f;
	
	private float cameraSpeedX = 0.0f;
	private float cameraSpeedY = 0.0f;
	
	
	private float cameraAspectRatio = 0.0f;
	
	private float cameraZoom = 1.0f;
	
	
	private final Point2fImpl cameraOrigin = new Point2fImpl();
	private final Point2fImpl cameraCoordinates = new Point2fImpl();
	private final Point2fImpl cameraVelocity = new Point2fImpl();

	private final Rectangle2fImpl cameraBounds = new Rectangle2fImpl();


	
	@Inject
	public CameraModel() { }

	

	@Override
	public float getAspectRatio() {
		return cameraAspectRatio;
	}
	
	@Override
	public float getZoom() {
		return cameraZoom;
	}


	@Override
	public boolean hasPortraitOrientation() {
		return getHeight() >= getWidth();
	}
	
	@Override
	public float getWidth() {
		return cameraWidth; //* (1.0f / mZoom);
	}
	
	@Override
	public float getHeight() {
		return cameraHeight;
	}
	
	@Override
	public float getSpeedX() {
		return cameraSpeedX;
	}
	
	@Override
	public float getSpeedY() {
		return cameraSpeedY;
	}
	
	@Override
	public float getOriginX() {
		return cameraOrigin.x;
	}
	
	@Override
	public float getOriginY() {
		return cameraOrigin.y;
	}
	
	@Override
	public IMutablePoint2f getOrigin(IMutablePoint2f result) {
		return result.set(cameraOrigin);
	}
	

	@Override
	public float getVelocityX() {
		return cameraVelocity.x;
	}
	
	@Override
	public float getVelocityY() {
		return cameraVelocity.y;
	}

	@Override
	public IMutablePoint2f getVelocity(IMutablePoint2f result) {
		return result.set(cameraVelocity);		
	}
	
	
	
	@Override
	public float getRawWorldX() {
		return cameraCoordinates.x - cameraOrigin.x;
	}
	
	@Override
	public float getRawWorldY() {
		return cameraCoordinates.y - cameraOrigin.y;
	}
	
	@Override
	public IMutablePoint2f getRawWorldCoordinates(IMutablePoint2f result) {
		return result.set(getRawWorldX(), getRawWorldY());
	}

	@Override
	public IMutableRectangle2f getWorldBounds(IMutableRectangle2f result) {
		return result.set(cameraBounds);
	}
	
	@Override
	public IRectangle2f getWorldBoundsBack() {
		return cameraBounds;
	}

	
	@Override
	public float getWorldX() {
		return cameraCoordinates.x;
	}

	@Override
	public float getWorldY() {
		return cameraCoordinates.y;
	}

	@Override
	public IMutablePoint2f getWorldCoordinates(IMutablePoint2f result) {
		return result.set(cameraCoordinates);
	}

	

	private void setGeometry(float width, float height, float zoom) {
		this.cameraWidth = width;
		this.cameraHeight = height;
		this.cameraZoom = zoom;
		
		this.cameraOrigin.x = width / 2.0f;
		this.cameraOrigin.y = height / 2.0f;
		
		this.cameraAspectRatio = calculateAspectRatio(width, height);
		
		this.cameraBounds.setWidth(width);
		this.cameraBounds.setHeight(height);

		// Re-center camera by correcting the camera bounds
		this.cameraBounds.moveTo(cameraCoordinates.x - cameraOrigin.x, cameraCoordinates.y - cameraOrigin.y);
	}
	

	private float calculateAspectRatio(float width, float height) {
		float res;
		if(hasPortraitOrientation()) {	// TODO: Fix: width or height could be 0
			res = height / width;
		}
		else {
			res = width / height;
		}
		
		return res;
	}
	
	
	@Override
	public void setSize(float width, float height) {
		setGeometry(width, height, 1.0f);
	}
	
	@Override
	public void setPortraitOrientation(boolean portrait) {
		if(portrait != hasPortraitOrientation()) {
			setGeometry(cameraHeight, cameraWidth, cameraZoom);
		}
	}
	
	@Override
	public void setAspectRatio(float ratio) {
		
		if(hasPortraitOrientation()) {
			// Portrait
			setGeometry(cameraWidth, ratio * cameraWidth, cameraZoom);
		}
		else {
			// Landscape
			setGeometry(ratio * cameraHeight, cameraHeight, cameraZoom);
		}
	}
	
	@Override
	public void setZoom(float zoom, boolean center) {

		float newWidth = getWidth() * cameraZoom / zoom;
		float newHeight = getHeight() * cameraZoom / zoom;
		
		
		/* TODO: Fix: It's already centering in setSize()
		if(center) {
			float diffWidth = Math.abs(mWidth - newWidth);
			float diffHeight = Math.abs(mHeight - newHeight);
			
			coordsStill.set(coordsStill.x + (diffWidth / 2.0f), coordsStill.y + (diffHeight / 2.0f));
			mCoords.set(coordsStill.x + coordsMotion.x, coordsStill.y + coordsMotion.y);
		}
		*/
		
		setGeometry(newWidth, newHeight, zoom);
	}
	

	@Override
	public void setSpeed(float sx, float sy) {
		cameraSpeedX = sx;
		cameraSpeedY = sy;
	}
	
	@Override
	public void setSpeedX(float sx) {
		cameraSpeedX = sx;
	}
	
	@Override
	public void setSpeedY(float sy) {
		cameraSpeedY = sy;
	}


	@Override
	public void setVelocityX(float vx) {
		cameraVelocity.setX(vx);
	}
	
	@Override
	public void setVelocityY(float vy) {
		cameraVelocity.setY(vy);
	}
	
	@Override
	public void setVelocity(float vx, float vy) {
		cameraVelocity.set(vx, vy);
	}
	
	

	@Override
	public void setWorldCoordinates(IPoint2f worldCoords) {
		setWorldCoordinates(worldCoords.x(), worldCoords.y());
	}
	
	@Override
	public void setWorldCoordinates(float worldX, float worldY) {
		cameraCoordinates.set(worldX, worldY);
		
		cameraBounds.moveTo(cameraCoordinates.x - cameraOrigin.x, cameraCoordinates.y - cameraOrigin.y);
	}

}
