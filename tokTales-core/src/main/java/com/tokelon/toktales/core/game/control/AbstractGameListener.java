package com.tokelon.toktales.core.game.control;

public class AbstractGameListener implements IGameStatusListener, IGameModeListener, IGameModeBasicListener {

	@Override
	public void gameStarted() {	}

	@Override
	public void gameRunning() {	}

	@Override
	public void gamePaused() { }

	@Override
	public void gameStopped() {	}

	@Override
	public void gameEnteredMode(int mode) {	}

	@Override
	public void gameExitMode(int mode) { }

	@Override
	public void gameModeChanged(int mode, boolean activ) { }

}
