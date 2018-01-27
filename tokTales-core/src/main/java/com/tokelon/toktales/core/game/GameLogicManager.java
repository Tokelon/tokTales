package com.tokelon.toktales.core.game;

import javax.inject.Inject;
import javax.inject.Provider;

import com.tokelon.toktales.core.engine.IEngineContext;


public class GameLogicManager implements IGameLogicManager {

	private IGame game;
	
	private final Provider<IEngineContext> engineContextProvider;
	private final Provider<IGame> gameProvider;
	
	@Inject
	public GameLogicManager(Provider<IEngineContext> engineContextProvider, Provider<IGame> gameProvider) {
		this.engineContextProvider = engineContextProvider;
	    this.gameProvider = gameProvider;
	}
	
	
	@Override
	public void onGameCreate() {
		game = gameProvider.get();
		
		IEngineContext engineContext = engineContextProvider.get();
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
