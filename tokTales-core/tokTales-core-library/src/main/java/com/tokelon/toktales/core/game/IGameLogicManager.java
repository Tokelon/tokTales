package com.tokelon.toktales.core.game;

/** The game logic manager is responsible for reacting to game events and generally handling the game flow.
 */
public interface IGameLogicManager {
	
	
	public void onGameCreate();
	public void onGameStart();
	public void onGameResume();
	public void onGamePause();
	public void onGameStop();
	public void onGameDestroy();
	
	public void onGameUpdate();
	public void onGameRender();
	
}
