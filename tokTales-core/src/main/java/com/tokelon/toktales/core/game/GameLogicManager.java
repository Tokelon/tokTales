package com.tokelon.toktales.core.game;

import javax.inject.Inject;
import javax.inject.Provider;

import com.tokelon.toktales.core.engine.IEngineContext;
import com.tokelon.toktales.core.engine.TokTales;


public class GameLogicManager implements IGameLogicManager {

	private IGame game;
	
	private final Provider<IGame> gameProvider;
	
	public GameLogicManager() { 
	    gameProvider = () -> game;
	}
	
	@Inject
	public GameLogicManager(Provider<IGame> gameProvider) {
	    this.gameProvider = gameProvider;
	}
	
	
	public void setupGame(IGame game) {
		this.game = game;
	}
	
	
	@Override
	public void onGameCreate() {
		if(game == null) {
		    game = gameProvider.get();
			//throw new IllegalStateException("No game was provided");
		}

		// TODO: Important - Fix this
		// Either take the values from the game, or pass the context as a parameter
		IEngineContext engineContext = TokTales.getContext();
		
		game.getGameAdapter().onCreate(engineContext);		
	}

	@Override
	public void onGameStart() {
		
		game.getGameAdapter().onStart();
	}

	@Override
	public void onGameResume() {
		/* Start the time */
		game.getTimeManager().startTime();
		
		
		game.getGameAdapter().onResume();
	}

	@Override
	public void onGamePause() {
		/* Stop the time */
		game.getTimeManager().stopTime();
		
		
		game.getGameAdapter().onPause();
	}

	@Override
	public void onGameStop() {
		
		game.getGameAdapter().onStop();
	}

	@Override
	public void onGameDestroy() {
		
		game.getGameAdapter().onDestroy();
	}

	@Override
	public void onGameUpdate() {
		long gt = game.getTimeManager().getGameTimeMillis();
		game.getStateControl().update(gt);
		
		
		game.getGameAdapter().onUpdate();
	}

	@Override
	public void onGameRender() {
		game.getStateControl().render();
		
		
		game.getGameAdapter().onRender();
	}


}
