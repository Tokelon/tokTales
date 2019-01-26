package com.tokelon.toktales.core.game.model;

import com.tokelon.toktales.core.game.logic.observers.IParticipable;
import com.tokelon.toktales.core.game.model.IPoint2f.IMutablePoint2f;
import com.tokelon.toktales.core.game.model.IRectangle2f.IMutableRectangle2f;

public abstract class CameraStateDecorator implements ICameraState, IParticipable<ICamera> {

	
	private ICameraModel cameraModel;
	
	public CameraStateDecorator(ICameraModel model) {
		if(model == null) {
			throw new NullPointerException();
		}
		
		this.cameraModel = model;
	}
	
	
	public ICameraModel getModel() {
		return cameraModel;
	}
	
	public void setModel(ICameraModel model) {
		if(model == null) {
			throw new NullPointerException();
		}
		
		this.cameraModel = model;
	}
	

	@Override
	public float getAspectRatio() {
		return cameraModel.getAspectRatio();
	}

	@Override
	public float getZoom() {
		return cameraModel.getZoom();
	}

	@Override
	public boolean hasPortraitOrientation() {
		return cameraModel.hasPortraitOrientation();
	}

	@Override
	public float getWidth() {
		return cameraModel.getWidth();
	}

	@Override
	public float getHeight() {
		return cameraModel.getHeight();
	}
	
	@Override
	public IMutablePoint2f getOrigin(IMutablePoint2f result) {
		return cameraModel.getOrigin(result);
	}

	@Override
	public float getSpeedX() {
		return cameraModel.getSpeedX();
	}

	@Override
	public float getSpeedY() {
		return cameraModel.getSpeedY();
	}

	@Override
	public float getOriginX() {
		return cameraModel.getOriginX();
	}

	@Override
	public float getOriginY() {
		return cameraModel.getOriginY();
	}

	@Override
	public float getVelocityX() {
		return cameraModel.getVelocityX();
	}

	@Override
	public float getVelocityY() {
		return cameraModel.getVelocityY();
	}

	@Override
	public IMutablePoint2f getVelocity(IMutablePoint2f result) {
		return cameraModel.getVelocity(result);
	}

	@Override
	public float getRawWorldX() {
		return cameraModel.getRawWorldX();
	}

	@Override
	public float getRawWorldY() {
		return cameraModel.getRawWorldY();
	}

	@Override
	public IMutablePoint2f getRawWorldCoordinates(IMutablePoint2f result) {
		return cameraModel.getRawWorldCoordinates(result);
	}

	@Override
	public float getWorldX() {
		return cameraModel.getWorldX();
	}

	@Override
	public float getWorldY() {
		return cameraModel.getWorldY();
	}

	@Override
	public IMutablePoint2f getWorldCoordinates(IMutablePoint2f result) {
		return cameraModel.getWorldCoordinates(result);
	}

	@Override
	public IMutableRectangle2f getWorldBounds(IMutableRectangle2f result) {
		return cameraModel.getWorldBounds(result);
	}

	@Override
	public IRectangle2f getWorldBoundsBack() {
		return cameraModel.getWorldBoundsBack();
	}
	

	@Override
	public void setSize(float width, float height) {
		cameraModel.setSize(width, height);
		
		getParticipation().notifyOfChange(ICamera.CHANGE_CAMERA_SIZE);
		getParticipation().notifyOfChange(ICamera.CHANGE_CAMERA_ORIGIN);
		getParticipation().notifyOfChange(ICamera.CHANGE_CAMERA_ASPECT_RATIO);
	}

	@Override
	public void setPortraitOrientation(boolean portrait) {
		boolean changed = hasPortraitOrientation() != portrait;
		cameraModel.setPortraitOrientation(portrait);
		
		if(changed) {
			getParticipation().notifyOfChange(ICamera.CHANGE_CAMERA_ORIENTATION);
			
			getParticipation().notifyOfChange(ICamera.CHANGE_CAMERA_SIZE);
			getParticipation().notifyOfChange(ICamera.CHANGE_CAMERA_ORIGIN);
			getParticipation().notifyOfChange(ICamera.CHANGE_CAMERA_ASPECT_RATIO);
		}
	}

	@Override
	public void setAspectRatio(float ratio) {
		cameraModel.setAspectRatio(ratio);

		getParticipation().notifyOfChange(ICamera.CHANGE_CAMERA_SIZE);
		getParticipation().notifyOfChange(ICamera.CHANGE_CAMERA_ORIGIN);
		getParticipation().notifyOfChange(ICamera.CHANGE_CAMERA_ASPECT_RATIO);
	}

	@Override
	public void setZoom(float zoom, boolean center) {
		cameraModel.setZoom(zoom, center);

		getParticipation().notifyOfChange(ICamera.CHANGE_CAMERA_ZOOM);
		
		getParticipation().notifyOfChange(ICamera.CHANGE_CAMERA_SIZE);
		getParticipation().notifyOfChange(ICamera.CHANGE_CAMERA_ORIGIN);
		getParticipation().notifyOfChange(ICamera.CHANGE_CAMERA_ASPECT_RATIO);
	}

	@Override
	public void setSpeedX(float sx) {
		cameraModel.setSpeedX(sx);
		
		getParticipation().notifyOfChange(ICamera.CHANGE_CAMERA_SPEED);
	}

	@Override
	public void setSpeedY(float sy) {
		cameraModel.setSpeedY(sy);
		
		getParticipation().notifyOfChange(ICamera.CHANGE_CAMERA_SPEED);
	}

	@Override
	public void setSpeed(float sx, float sy) {
		cameraModel.setSpeed(sx, sy);
		
		getParticipation().notifyOfChange(ICamera.CHANGE_CAMERA_SPEED);
	}

	@Override
	public void setVelocityX(float vx) {
		cameraModel.setVelocityX(vx);
		
		getParticipation().notifyOfChange(ICamera.CHANGE_CAMERA_VELOCITY);
	}

	@Override
	public void setVelocityY(float vy) {
		cameraModel.setVelocityY(vy);

		getParticipation().notifyOfChange(ICamera.CHANGE_CAMERA_VELOCITY);
	}

	@Override
	public void setVelocity(float vx, float vy) {
		cameraModel.setVelocity(vx, vy);

		getParticipation().notifyOfChange(ICamera.CHANGE_CAMERA_VELOCITY);
	}

	@Override
	public void setWorldCoordinates(float worldX, float worldY) {
		cameraModel.setWorldCoordinates(worldX, worldY);

		getParticipation().notifyOfChange(ICamera.CHANGE_CAMERA_COORDINATES);
	}

	@Override
	public void setWorldCoordinates(IPoint2f worldCoords) {
		cameraModel.setWorldCoordinates(worldCoords);
		
		getParticipation().notifyOfChange(ICamera.CHANGE_CAMERA_COORDINATES);
	}

}
