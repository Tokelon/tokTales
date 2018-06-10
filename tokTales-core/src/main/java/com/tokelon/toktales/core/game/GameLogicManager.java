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

		game.getContentManager().startLoaders();

		IEngineContext engineContext = engineContextProvider.get();
		game.getGameAdapter().onCreate(engineContext);
	}

	@Override
	public void onGameStart() {
		
		game.getGameAdapter().onStart();
	}

	@Override
	public void onGameResume() {
		// Start the time
		game.getTimeManager().startTime();
		
		// Resume the active state
		game.getStateControl().resumeState();
		
		
		game.getGameAdapter().onResume();
	}

	@Override
	public void onGamePause() {
		// Stop the time
		game.getTimeManager().stopTime();
		
		// Pause the active state
		game.getStateControl().pauseState();
		
		
		game.getGameAdapter().onPause();
	}

	@Override
	public void onGameStop() {
		
		game.getGameAdapter().onStop();
	}

	@Override
	public void onGameDestroy() {
		game.getContentManager().stopLoaders();
		
		game.getGameAdapter().onDestroy();
	}

	
	@Override
	public void onGameUpdate() {
		// Update the active state
		long gt = game.getTimeManager().getGameTimeMillis();
		game.getStateControl().updateState(gt);
		
		
		game.getGameAdapter().onUpdate();
	}

	@Override
	public void onGameRender() {
		// Render the active state
		game.getStateControl().renderState();
		
		
		game.getGameAdapter().onRender();
	}

}
