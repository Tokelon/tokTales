package com.tokelon.toktales.core.game;

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
