package com.tokelon.toktales.core.game.controller;

import com.tokelon.toktales.core.game.model.ICamera;
import com.tokelon.toktales.core.game.model.entity.IGameEntity;

public interface ICameraController extends IController {

	
	public ICamera getCamera();

	
	public void cameraStartMoving(int direction);
	public void cameraStopMoving();
	
	
	public void enableCameraFollow(IGameEntity followEntity);
	public void disableCameraFollow();
	public boolean isCameraFollowEnabled();
	public IGameEntity getCameraFollowEntity();


	//public void cameraPositionSetTo(int posx, int posy);
	//public void cameraMoveTo(int posx, int posy);
	//public voids cameraPlaceAt(int posx, int posy);

	
	public interface ICameraControllerFactory {
		
		public ICameraController create(ICamera camera);
	}
	
}
