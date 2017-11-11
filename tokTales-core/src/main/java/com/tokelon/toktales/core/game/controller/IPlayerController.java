package com.tokelon.toktales.core.game.controller;

import com.tokelon.toktales.core.game.model.IPlayer;

public interface IPlayerController extends IController { //, IDataPlace<DataRead, DataWrite> {

	
	public IPlayer getPlayer();
	
	
	//public void enableCameraFollowPlayer(boolean enable);
	//public boolean isCameraFollowPlayerEnabled();
	
	
	
	
	// TODO: Are these methods too hardcoded? Is there a way to make the functionality more abstract?
	
	public boolean playerLook(int direction);
	
	public boolean playerStartMoving(int direction);
	
	public boolean playerStopMoving();
	
	
	public boolean playerJump();
	

}
