package com.tokelon.toktales.android.activity.integration;

import javax.inject.Inject;

import com.tokelon.toktales.core.game.IGame;

public class GameIntegration implements IGameIntegration {


	private final IGame game;
	
	@Inject
	public GameIntegration(IGame game) {
		this.game = game;
	}
	
	
	@Override
	public void onActivityStart(IIntegratedActivity activity) {
		game.getGameControl().startGame();
	}
	
	@Override
	public void onActivityResume(IIntegratedActivity activity) {
		game.getGameControl().resumeGame();
	}
	
	@Override
	public void onActivityPause(IIntegratedActivity activity) {
		game.getGameControl().pauseGame();
	}
	
	@Override
	public void onActivityStop(IIntegratedActivity activity) {
		game.getGameControl().stopGame();
	}
	
}
