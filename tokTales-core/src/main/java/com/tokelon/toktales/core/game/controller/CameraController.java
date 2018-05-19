package com.tokelon.toktales.core.game.controller;

import javax.inject.Inject;

import com.tokelon.toktales.core.game.model.Camera;
import com.tokelon.toktales.core.game.model.ICamera;
import com.tokelon.toktales.core.game.model.ICamera.CameraParticipant;
import com.tokelon.toktales.core.game.model.entity.IGameEntity;
import com.tokelon.toktales.core.game.model.entity.IGameEntity.GameEntityObserver;
import com.tokelon.toktales.core.game.model.entity.IGameEntity.IGameEntityObserver;
import com.tokelon.toktales.core.game.world.ICompassDirection;

public class CameraController extends AbstractController implements ICameraController {

	
	private IGameEntity followEntity;

	private final FollowEntityObserver followEntityObserver;
	
	private final Camera mCamera;

	@Inject
	public CameraController() {
		mCamera = new Camera();
		mCamera.getParticipation().addParticipant(new MyCameraParticipant());
		
		followEntityObserver = new FollowEntityObserver();
	}
	
	
	@Override
	public ICamera getCamera() {
		return mCamera;
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
	
	
	
	@Override
	public void startMoving(int direction, int speedHorizontal, int speedVertical) {
		mCamera.setVelocity(
				ICompassDirection.Tools.horizontalVelocitySignFromDirection(direction) * speedHorizontal,
				ICompassDirection.Tools.verticalVelocitySignFromDirectionInvertY(direction) * speedVertical);
	}

	@Override
	public void stopMoving() {
		mCamera.setVelocity(0f, 0f);
	}

	
	
	private class MyCameraParticipant extends CameraParticipant {

		@Override
		public boolean hasInterest(ICamera subject, String change) {
			return true;
		}
		
		
		@Override
		public boolean isGeneric() {
			return true;
		}
		
		
		@Override
		public boolean onSubjectChange(ICamera subject, String change) {
			
			if(change.equals(IGameEntity.CHANGE_ENTITY_STATE_ADJUST)) {
				//...
				//return true;
			}
			
			return false;
		}
		

		@Override
		public void subjectChanged(ICamera subject, String change) {
			/*
			if(!change.equals(ICamera.CHANGE_CAMERA_ADJUST_STATE)) {
				TokTales.getLog().d("CameraController", "subjectChanged: " + change);	
			}
			*/
		}
		
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
	
}
