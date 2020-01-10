package com.tokelon.toktales.core.engine;

import javax.inject.Inject;

import com.tokelon.toktales.core.game.IGame;

public class DefaultEngineDriver implements IEngineDriver {


	private final IGame game;

	@Inject
	public DefaultEngineDriver(IGame game) {
		this.game = game;
	}
	
	
	@Override
	public void update() {
		/*
		if(game.getGameControl().getGameStatus() != IGameControl.GameStatus.RUNNING) {
			return;
		}
		*/
		
		game.getGameControl().updateGame();
	}

	@Override
	public void render() {
		game.getGameControl().renderGame();
	}

	@Override
	public void processInput(IEngineInputProcessor inputProcessor) {
		inputProcessor.process();
	}

}
