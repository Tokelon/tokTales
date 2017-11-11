package com.tokelon.toktales.core.game.control;

import java.util.HashSet;
import java.util.Set;

import com.tokelon.toktales.core.game.IGameLogicManager;

public class GameControl implements IGameControl {


	private final IGameLogicManager logicManager;
	
	
	//private final Set<IGameListener> gameListeners;
	
	private final Set<IGameModeListener> gameModeListeners = new HashSet<IGameModeListener>();
	private final Set<IGameModeBasicListener> gameModeBasicListeners = new HashSet<IGameModeBasicListener>();
	private final Set<IGameStatusListener> gameStatusListeners = new HashSet<IGameStatusListener>();
	
	
	private boolean statusStarted;
	private boolean statusRunning;
	
	private final Set<Integer> modesEnabled = new HashSet<Integer>();
	
	
	// TODO: Important - Reevaluate the thread safe implementation
	// -> TODO: Do not make thread safe, and remove the runtime exceptions
	
	public GameControl(IGameLogicManager logicManager) {
		this.logicManager = logicManager;
	}
	
	
	
	@Override
	public void createGame() {
		logicManager.onGameCreate();
	}
	
	@Override
	public void destroyGame() {
		logicManager.onGameDestroy();
	}
	
	
	@Override
	public synchronized void startGame() {
		if(statusStarted) {
			throw new GameStatusException("Game is already started");
		}
		
		logicManager.onGameStart();
		

		statusStarted = true;
		for(IGameStatusListener gsl: gameStatusListeners) {
			gsl.gameStarted();
		}
		/*
		for(IGameListener gl: gameListeners) {
			if(gl instanceof IGameStateListener) {
				((IGameStateListener) gl).gameStarted();
			}
		}*/
	}

	@Override
	public synchronized void pauseGame() {
		if(!statusRunning) {
			throw new GameStatusException("Cannot pause game that isn't running");
		}
		
		logicManager.onGamePause();
		
		
		statusRunning = false;
		for(IGameStatusListener gsl: gameStatusListeners) {
			gsl.gamePaused();
		}
	}

	@Override
	public synchronized void resumeGame() {
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
		
		// TODO: Add log "game control is now unpaused'
	}
	
	
	@Override
	public synchronized void stopGame() {
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
		/*
		if(modesEnabled.contains(mode)) {
			throw new GameStateException("Mode is already activated");
		}*/
		if(modesEnabled.contains(mode)) {
			return;
		}
		
		
		modesEnabled.add(mode);
		for(IGameModeListener gml: gameModeListeners) {
			gml.gameEnteredMode(mode);
		}
		for(IGameModeBasicListener gmbl: gameModeBasicListeners) {
			gmbl.gameModeChanged(mode, true);
		}
	}

	@Override
	public synchronized void exitMode(int mode) {
		/*
		if(!modesEnabled.contains(mode)) {
			throw new GameStateException("Mode is not activated");
		}*/
		if(!modesEnabled.contains(mode)) {
			return;
		}
		
		
		modesEnabled.remove(mode);
		for(IGameModeListener gml: gameModeListeners) {
			gml.gameExitMode(mode);
		}
		for(IGameModeBasicListener gmbl: gameModeBasicListeners) {
			gmbl.gameModeChanged(mode, false);
		}
	}


	@Override
	public synchronized boolean isModeActivated(int mode) {
		return modesEnabled.contains(mode);
	}
	
	
	
	// TODO: Important - Implement callbacks for already activated modes and states
	
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
	
	
	
	// Not sure when removing a listener even makes sense
	
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
