package com.tokelon.toktales.core.game.control;

public interface IGameStatusListener extends IGameListener {

	public void gameStarted();
	
	public void gameRunning();
	
	public void gamePaused();
	
	public void gameStopped();
	
}
