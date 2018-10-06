package com.tokelon.toktales.core.game.control;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.inject.Inject;

import com.tokelon.toktales.core.engine.log.ILogger;
import com.tokelon.toktales.core.game.IGameLogicManager;

public class GameControl implements IGameControl {
	
	public static final String TAG = "GameControl";
	
	
	//private final Set<IGameListener> gameListeners;
	
	private final Set<IGameModeListener> gameModeListeners = new HashSet<IGameModeListener>();
	private final Set<IGameStatusListener> gameStatusListeners = new HashSet<IGameStatusListener>();

	
	private final Set<String> modesEnabled = new HashSet<>();
	private final Set<String> modesEnabledUnmodifiable = Collections.unmodifiableSet(modesEnabled);

	private GameStatus gameStatus = GameStatus.DESTROYED;

	
	private final ILogger logger;
	private final IGameLogicManager logicManager;

	@Inject
	public GameControl(ILogger logger, IGameLogicManager logicManager) {
		this.logger = logger;
		this.logicManager = logicManager;
	}
	
	
	private void changeStatusTo(GameStatus newGameStatus) {
		gameStatus = newGameStatus;
		notifyOfStatusChange(newGameStatus);
	}
	
	private void notifyOfStatusChange(GameStatus newGameStatus) {
		for(IGameStatusListener gameStatusListener: gameStatusListeners) {
			gameStatusListener.gameStatusChanged(newGameStatus);
		}
	}
	
	
	@Override
	public void createGame() {
		if(gameStatus == GameStatus.CREATED) {
			logger.d(TAG, "Game is already created");
			return;
		}
		if(gameStatus != GameStatus.DESTROYED) {
			throw new GameStatusException("Cannot create game with status: " + gameStatus);
		}

		logger.d(TAG, "Game is being created...");

		logicManager.onGameCreate();

		changeStatusTo(GameStatus.CREATED);
		logger.i(TAG, "Game was created");
	}
	
	
	@Override
	public synchronized void startGame() {
		if(gameStatus == GameStatus.STARTED) {
			logger.d(TAG, "Game is already started");
			return;
		}
		if(gameStatus != GameStatus.CREATED && gameStatus != GameStatus.STOPPED) {
			throw new GameStatusException("Cannot start game with status: " + gameStatus);
		}
		
		logger.d(TAG, "Game is starting...");
		
		logicManager.onGameStart();
		
		changeStatusTo(GameStatus.STARTED);
		logger.i(TAG, "Game was started");
	}


	@Override
	public synchronized void resumeGame() {
		if(gameStatus == GameStatus.RUNNING) {
			logger.d(TAG, "Game is already running");
			return;
		}
		if(gameStatus != GameStatus.STARTED && gameStatus != GameStatus.PAUSED) {
			throw new GameStatusException("Cannot resume game with status: " + gameStatus);
		}
		
		logger.d(TAG, "Game is resuming...");
		
		logicManager.onGameResume();
		
		changeStatusTo(GameStatus.RUNNING);
		logger.i(TAG, "Game was resumed");
	}
	
	
	@Override
	public synchronized void pauseGame() {
		if(gameStatus == GameStatus.PAUSED) {
			logger.d(TAG, "Game is already paused");
			return;
		}
		if(gameStatus != GameStatus.RUNNING) {
			throw new GameStatusException("Cannot pause game with status: " + gameStatus);
		}
		
		logger.d(TAG, "Game is pausing...");
		
		logicManager.onGamePause();
		
		changeStatusTo(GameStatus.PAUSED);
		logger.i(TAG, "Game was paused");
	}

	
	@Override
	public synchronized void stopGame() {
		if(gameStatus == GameStatus.STOPPED) {
			logger.d(TAG, "Game is already stopped");
			return;
		}
		if(gameStatus != GameStatus.STARTED && gameStatus != GameStatus.PAUSED) {
			throw new GameStatusException("Cannot stop game with status: " + gameStatus);
		}
		
		logger.d(TAG, "Game is stopping...");
	
		logicManager.onGameStop();
		
		changeStatusTo(GameStatus.STOPPED);
		logger.i(TAG, "Game was stopped");
	}


	@Override
	public void destroyGame() {
		if(gameStatus == GameStatus.DESTROYED) {
			logger.d(TAG, "Game is already destroyed");
			return;
		}
		if(gameStatus != GameStatus.CREATED && gameStatus != GameStatus.STOPPED) {
			throw new GameStatusException("Cannot destroy game with status: " + gameStatus);
		}
		
		logger.d(TAG, "Game is being destroyed...");

		logicManager.onGameDestroy();
		
		changeStatusTo(GameStatus.DESTROYED);
		logger.i(TAG, "Game was destroyed");
	}
	
	
	@Override
	public void updateGame() {
		if(gameStatus != GameStatus.RUNNING) {
			throw new GameStatusException("Cannot update game with status: " + gameStatus);
		}
		
		logicManager.onGameUpdate();
	}
	
	@Override
	public void renderGame() {
		if(gameStatus != GameStatus.RUNNING) {
			throw new GameStatusException("Cannot render game with status: " + gameStatus);
		}
		
		logicManager.onGameRender();
	}
	
	
	@Override
	public GameStatus getGameStatus() {
		return gameStatus;
	}
	
	
	
	@Override
	public synchronized void enterMode(String mode) {
		logger.d(TAG, "Game is entering mode: " + mode);
		
		if(modesEnabled.contains(mode)) {
			logger.d(TAG, "Game mode is already active: " + mode);
			return;
		}
		
		
		modesEnabled.add(mode);
		for(IGameModeListener gml: gameModeListeners) {
			gml.gameModeChanged(mode, true);
		}
		
		logger.i(TAG, "Game has entered mode: " + mode);
	}

	@Override
	public synchronized void exitMode(String mode) {
		logger.d(TAG, "Game is exiting mode: " + mode);

		if(!modesEnabled.contains(mode)) {
			logger.d(TAG, "Game mode is not yet active: " + mode);
			return;
		}
		
		
		modesEnabled.remove(mode);
		for(IGameModeListener gml: gameModeListeners) {
			gml.gameModeChanged(mode, false);
		}
		
		logger.i(TAG, "Game has exited mode: " + mode);
	}


	@Override
	public synchronized boolean isModeActive(String mode) {
		return modesEnabled.contains(mode);
	}
	
	@Override
	public Set<String> getActiveModes() {
		return modesEnabledUnmodifiable;
	}
	
	

	@Override
	public synchronized void registerStatusListener(IGameStatusListener stateListener) {
		registerStatusListener(stateListener, true);
	}
	
	@Override
	public synchronized void registerStatusListener(IGameStatusListener stateListener,	boolean receivePast) {
		gameStatusListeners.add(stateListener);

		if(receivePast) {
			switch (gameStatus) {
			case CREATED:
				stateListener.gameStatusChanged(GameStatus.CREATED);
				break;
			case STARTED:
				stateListener.gameStatusChanged(GameStatus.CREATED);
				stateListener.gameStatusChanged(GameStatus.STARTED);
				break;
			case RUNNING:
				stateListener.gameStatusChanged(GameStatus.CREATED);
				stateListener.gameStatusChanged(GameStatus.STARTED);
				stateListener.gameStatusChanged(GameStatus.RUNNING);
				break;
			case PAUSED:
				stateListener.gameStatusChanged(GameStatus.CREATED);
				stateListener.gameStatusChanged(GameStatus.STARTED);
				stateListener.gameStatusChanged(GameStatus.PAUSED);
				break;
			case STOPPED:
				stateListener.gameStatusChanged(GameStatus.CREATED);
				stateListener.gameStatusChanged(GameStatus.STOPPED);
				break;
			case DESTROYED:
				// Nothing
			}
		}
	}

	@Override
	public synchronized void removeStatusListener(IGameStatusListener listener) {
		gameStatusListeners.remove(listener);
	}
	
	
	@Override
	public synchronized void registerModeListener(IGameModeListener modeListener) {
		registerModeListener(modeListener, true);
	}
	
	@Override
	public synchronized void registerModeListener(IGameModeListener modeListener, boolean receivePast) {
		gameModeListeners.add(modeListener);
		
		if(receivePast) {
			for(String m: modesEnabled) {
				modeListener.gameModeChanged(m, true);
			}
		}
	}
	
	@Override
	public synchronized void removeModeListener(IGameModeListener listener) {
		gameModeListeners.remove(listener);
	}

}
