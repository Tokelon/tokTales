package com.tokelon.toktales.core.game.control;

import java.util.HashSet;
import java.util.Set;

import javax.inject.Inject;

import com.tokelon.toktales.core.engine.log.ILogger;
import com.tokelon.toktales.core.game.IGameLogicManager;

public class GameControl implements IGameControl {
	// TODO: Important - Reevaluate the thread safe implementation
	// -> TODO: Do not make thread safe, and remove the runtime exceptions
	
	// TODO: Implement state machine for created, running, paused, destroyed
	
	public static final String TAG = "GameControl";
	
	
	//private final Set<IGameListener> gameListeners;
	
	private final Set<IGameModeListener> gameModeListeners = new HashSet<IGameModeListener>();
	private final Set<IGameModeBasicListener> gameModeBasicListeners = new HashSet<IGameModeBasicListener>();
	private final Set<IGameStatusListener> gameStatusListeners = new HashSet<IGameStatusListener>();

	
	private final Set<Integer> modesEnabled = new HashSet<Integer>();

	private boolean statusStarted;
	private boolean statusRunning;
	
	
	private final ILogger logger;
	private final IGameLogicManager logicManager;

	@Inject
	public GameControl(ILogger logger, IGameLogicManager logicManager) {
		this.logger = logger;
		this.logicManager = logicManager;
	}
	
	
	
	@Override
	public void createGame() {
		// TODO: Check status and throw exception if needed
		logicManager.onGameCreate();
	}
	
	@Override
	public void destroyGame() {
		// TODO: Check status and throw exception if needed
		logicManager.onGameDestroy();
	}
	
	
	@Override
	public synchronized void startGame() {
		logger.d(TAG, "Game is starting...");
		
		if(statusStarted) {
			throw new GameStatusException("Game is already started");
		}
		
		logicManager.onGameStart();
		

		statusStarted = true;
		for(IGameStatusListener gsl: gameStatusListeners) {
			gsl.gameStarted();
		}
		
		logger.i(TAG, "Game was started");
	}

	@Override
	public synchronized void pauseGame() {
		logger.d(TAG, "Game is pausing...");
		
		if(!statusRunning) {
			throw new GameStatusException("Cannot pause game that isn't running");
		}
		
		logicManager.onGamePause();
		
		
		statusRunning = false;
		for(IGameStatusListener gsl: gameStatusListeners) {
			gsl.gamePaused();
		}
		
		logger.i(TAG, "Game was paused");
	}

	@Override
	public synchronized void resumeGame() {
		logger.d(TAG, "Game is resuming...");
		
		if(!statusStarted) {
			throw new GameStatusException("Cannot unpause game that isn't started");
		}
		
		if(statusRunning) {
			throw new GameStatusException("Cannot unpause game that is running");
		}
		
		logicManager.onGameResume();
		
		
		statusRunning = true;
		for(IGameStatusListener gsl: gameStatusListeners) {
			gsl.gameRunning();
		}
		
		logger.i(TAG, "Game was resumed");
	}
	
	
	@Override
	public synchronized void stopGame() {
		logger.d(TAG, "Game is stopping...");
		
		if(statusRunning) {
			throw new GameStatusException("Cannot stop game that isn't paused");
		}
		
		if(!statusStarted) {
			throw new GameStatusException("Cannot stop game that isn't started");
		}
	
		logicManager.onGameStop();
		
		
		statusStarted = false;
		for(IGameStatusListener gsl: gameStatusListeners) {
			gsl.gameStopped();
		}
		
		logger.i(TAG, "Game was stopped");
	}

	
	@Override
	public void updateGame() {
		if(!statusRunning) {
			throw new GameStatusException("Cannot update game that isn't running");
		}
		
		logicManager.onGameUpdate();
	}
	
	@Override
	public void renderGame() {
		if(!statusRunning) {
			throw new GameStatusException("Cannot render game that isn't running");
		}
		
		logicManager.onGameRender();
	}
	
	
	
	
	
	@Override
	public synchronized void enterMode(int mode) {
		logger.d(TAG, "Game is entering mode: " + mode);
		
		if(modesEnabled.contains(mode)) {
			//throw new GameStateException("Mode is already activated");
			return;
		}
		
		
		modesEnabled.add(mode);
		for(IGameModeListener gml: gameModeListeners) {
			gml.gameEnteredMode(mode);
		}
		for(IGameModeBasicListener gmbl: gameModeBasicListeners) {
			gmbl.gameModeChanged(mode, true);
		}
		
		logger.i(TAG, "Game has entered mode: " + mode);
	}

	@Override
	public synchronized void exitMode(int mode) {
		logger.d(TAG, "Game is exiting mode: " + mode);

		if(!modesEnabled.contains(mode)) {
			//throw new GameStateException("Mode is not activated");
			return;
		}
		
		
		modesEnabled.remove(mode);
		for(IGameModeListener gml: gameModeListeners) {
			gml.gameExitMode(mode);
		}
		for(IGameModeBasicListener gmbl: gameModeBasicListeners) {
			gmbl.gameModeChanged(mode, false);
		}
		
		logger.i(TAG, "Game has exited mode: " + mode);
	}


	@Override
	public synchronized boolean isModeActivated(int mode) {
		return modesEnabled.contains(mode);
	}
	
	
	
	@Override
	public synchronized void registerModeListener(IGameModeListener modeListener) {
		registerModeListener(modeListener, true);
	}
	@Override
	public synchronized void registerModeListener(IGameModeListener modeListener, boolean receivePast) {
		gameModeListeners.add(modeListener);
		
		if(receivePast) {
			for(Integer m: modesEnabled) {
				modeListener.gameEnteredMode(m);
			}
		}
	}
	
	@Override
	public synchronized void registerModeListener(IGameModeBasicListener basicModeListener) {
		registerModeListener(basicModeListener, true);
	}
	@Override
	public synchronized void registerModeListener(IGameModeBasicListener basicModeListener, boolean receivePast) {
		gameModeBasicListeners.add(basicModeListener);
		
		if(receivePast) {
			for(Integer m: modesEnabled) {
				basicModeListener.gameModeChanged(m, true);
			}
		}
	}
	
	@Override
	public synchronized void registerStatusListener(IGameStatusListener stateListener) {
		registerStatusListener(stateListener, true);
	}
	@Override
	public synchronized void registerStatusListener(IGameStatusListener stateListener,	boolean receivePast) {
		gameStatusListeners.add(stateListener);

		if(receivePast && statusStarted) {
			stateListener.gameStarted();

			if(statusRunning) {
				stateListener.gameRunning();
			}
		}
	}
	
	
	@Override
	public synchronized void removeModeListener(IGameModeListener listener) {
		gameModeListeners.remove(listener);
	}
	
	@Override
	public synchronized void removeModeListener(IGameModeBasicListener basicModeListener) {
		gameModeBasicListeners.remove(basicModeListener);
	}

	@Override
	public synchronized void removeStateListener(IGameStatusListener listener) {
		gameStatusListeners.remove(listener);
	}

}
