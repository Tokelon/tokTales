package com.tokelon.toktales.core.logic.process;

import com.tokelon.toktales.core.engine.IEngineContext;
import com.tokelon.toktales.core.engine.log.ILogger;
import com.tokelon.toktales.core.game.IGame;

public class GameProcess implements IPauseableProcess { // TODO: Use or remove


	private final IGame game;
	private final ILogger logger;
	
	public GameProcess(IEngineContext context) {
		game = context.getGame();
		logger = context.getLogging().getLogger(getClass());
	}
	
	
	@Override
	public void startProcess() {
		logger.debug("GameProcess started");
		
		game.getGameControl().startGame();
	}

	@Override
	public void unpause() {
		logger.debug("GameProcess unpaused");
		
		game.getGameControl().resumeGame();
	}
	
	@Override
	public void pause() {
		logger.debug("GameProcess paused");
		
		game.getGameControl().pauseGame();
	}

	@Override
	public void endProcess() {
		logger.debug("GameProcess ended");
		
		game.getGameControl().stopGame();
	}
	
}
