package com.tokelon.toktales.core.logic.process;

import com.tokelon.toktales.core.engine.IEngineContext;
import com.tokelon.toktales.core.engine.TokTales;
import com.tokelon.toktales.core.game.IGame;
import com.tokelon.toktales.core.prog.annotation.TokTalesRequired;

@TokTalesRequired
public class GameProcess implements IPauseableProcess {
	
	// TODO: Refactor and remove this crap

	public static final String TAG = "GameProcess";
	
	
	private IGame game;
	
	public GameProcess(IEngineContext context) {
		game = context.getGame();
	}
	
	
	@Override
	public void startProcess() {
		TokTales.getLog().d(TAG, "GameProcess started");
		
		
		// Call this here or only when map has been loaded?
		game.getGameControl().startGame();
	}

	
	@Override
	public void endProcess() {
		TokTales.getLog().d(TAG, "GameProcess ended");
		
		game.getGameControl().stopGame();
	}

	
	
	@Override
	public void pause() {
		TokTales.getLog().d(TAG, "GameProcess paused");
		
		game.getGameControl().pauseGame();
	}

	
	@Override
	public void unpause() {
		TokTales.getLog().d(TAG, "GameProcess unpaused");
		
		game.getGameControl().resumeGame();
	}

	
	
	
}
