package com.tokelon.toktales.core.engine;

import javax.inject.Inject;

import com.tokelon.toktales.core.game.IGame;

/** Default implementation of {@link IEngineDriver}.
 */
public class DefaultEngineDriver implements IEngineDriver {


	private final IGame game;

	@Inject
	public DefaultEngineDriver(IGame game) {
		this.game = game;
	}


	@Override
	public void create() {
		game.getGameControl().createGame();
	}

	@Override
	public void start() {
		game.getGameControl().startGame();
	}

	@Override
	public void resume() {
		game.getGameControl().resumeGame();
	}

	@Override
	public void pause() {
		game.getGameControl().pauseGame();
	}

	@Override
	public void stop() {
		game.getGameControl().stopGame();
	}

	@Override
	public void destroy() {
		game.getGameControl().destroyGame();
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
