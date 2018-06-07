package com.tokelon.toktales.core.game.controller;

import com.tokelon.toktales.core.game.model.IPlayer;

public interface IPlayerController extends IController {

	
	public IPlayer getPlayer();
	
	
	
	// TODO: Are these methods too hardcoded? Is there a way to make the functionality more abstract?
	
	public boolean playerLook(int direction);
	
	public boolean playerStartMoving(int direction);
	
	public boolean playerStopMoving();
	
	
	public boolean playerJump();
	

	
	public interface IPlayerControllerFactory {
		
		public IPlayerController create(IPlayer player);
	}
	
}
