package com.tokelon.toktales.core.logic.process;

import com.tokelon.toktales.core.engine.IEngineContext;
import com.tokelon.toktales.core.engine.log.ILogger;
import com.tokelon.toktales.core.game.IGame;

public class GameProcess implements IPauseableProcess { // TODO: Use or remove
	
	public static final String TAG = "GameProcess";
	
	
	private final IGame game;
	private final ILogger logger;
	
	public GameProcess(IEngineContext context) {
		game = context.getGame();
		logger = context.getLog();
	}
	
	
	@Override
	public void startProcess() {
		logger.d(TAG, "GameProcess started");
		
		game.getGameControl().startGame();
	}

	@Override
	public void unpause() {
		logger.d(TAG, "GameProcess unpaused");
		
		game.getGameControl().resumeGame();
	}
	
	@Override
	public void pause() {
		logger.d(TAG, "GameProcess paused");
		
		game.getGameControl().pauseGame();
	}

	@Override
	public void endProcess() {
		logger.d(TAG, "GameProcess ended");
		
		game.getGameControl().stopGame();
	}
	
}
