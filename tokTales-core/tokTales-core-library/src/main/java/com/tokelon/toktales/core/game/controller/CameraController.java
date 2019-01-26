package com.tokelon.toktales.core.game.controller;

import javax.inject.Inject;

import com.tokelon.toktales.core.game.model.ICamera;
import com.tokelon.toktales.core.game.model.entity.IGameEntity;
import com.tokelon.toktales.core.game.model.entity.IGameEntity.GameEntityObserver;
import com.tokelon.toktales.core.game.model.entity.IGameEntity.IGameEntityObserver;
import com.tokelon.toktales.core.game.world.ICrossDirection;
import com.tokelon.toktales.core.values.CameraValues;

public class CameraController extends AbstractController implements ICameraController {

	
	private IGameEntity followEntity;

	
	private final FollowEntityObserver followEntityObserver;
	
	private final ICamera camera;

	@Inject
	public CameraController(ICamera camera) {
		this.camera = camera;

		this.followEntityObserver = new FollowEntityObserver();
		
		setupCamera();
	}
	
	
	private void setupCamera() {
		float cameraMoveSpeedUnits = CameraValues.CAMERA_MOVE_SPEED_UNITS;

		camera.setSpeedX(cameraMoveSpeedUnits);
		camera.setSpeedY(cameraMoveSpeedUnits);
	}
	
	
	@Override
	public ICamera getCamera() {
		return camera;
	}
	
	

	@Override
	public void cameraStartMoving(int direction) {
		camera.setVelocity( // Use ICompassDirection?
				camera.getSpeedX() * ICrossDirection.Tools.horizontalVelocitySignFromDirection(direction),
				camera.getSpeedY() * ICrossDirection.Tools.verticalVelocitySignFromDirectionInvertY(direction)
		);
	}

	@Override
	public void cameraStopMoving() {
		camera.setVelocity(0f, 0f);
	}
	
	
	@Override
	public void enableCameraFollow(IGameEntity followEntity) {
		this.followEntity = followEntity;
		followEntity.getObservation().addObserver(followEntityObserver);
		followEntityObserver.entityCoordinatesChanged(followEntity);
	}
	
	@Override
	public void disableCameraFollow() {
		if(followEntity != null) {
			followEntity.getObservation().removeObserver(followEntityObserver);
			followEntity = null;
		}
	}
	
	@Override
	public boolean isCameraFollowEnabled() {
		return followEntity != null;
	}
	
	@Override
	public IGameEntity getCameraFollowEntity() {
		return followEntity;
	}
	
	
	
	private class FollowEntityObserver extends GameEntityObserver implements IGameEntityObserver {

		@Override
		public boolean hasInterest(IGameEntity subject, String change) {
			return change.equals(IGameEntity.CHANGE_ENTITY_COORDINATES);
		}
		
		@Override
		public void entityCoordinatesChanged(IGameEntity entity) {
			
			if(CameraController.this.isCameraFollowEnabled()) {
				getCamera().setWorldCoordinates(entity.getWorldX(), entity.getWorldY());
			}
		}
	}
	
	
	public static class CameraControllerFactory implements ICameraControllerFactory {

		@Override
		public ICameraController create(ICamera camera) {
			return new CameraController(camera);
		}
	}
	
}
