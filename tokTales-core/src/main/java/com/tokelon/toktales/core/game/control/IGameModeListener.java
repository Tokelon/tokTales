package com.tokelon.toktales.core.game.control;

public interface IGameModeListener extends IGameListener {

	public void gameEnteredMode(int mode);	//gameModeActivated
	
	public void gameExitMode(int mode);
	
}
