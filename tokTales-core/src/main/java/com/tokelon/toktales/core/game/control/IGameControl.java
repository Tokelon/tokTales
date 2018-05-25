package com.tokelon.toktales.core.game.control;

import java.util.Set;

/** The game control can be used to control the states and modes of the game.
 */
public interface IGameControl {
	// Does this need thread safety? If yes, document/implement it

	
	public enum GameStatus {
		CREATED,
		STARTED,
		RUNNING,
		PAUSED,
		STOPPED,
		DESTROYED
	}
	
	
	/**
	 * @throws GameStatusException
	 */
	public void createGame();
	
	/**
	 * @throws GameStatusException
	 */
	public void destroyGame();
	
	
	/**
	 * @throws GameStatusException
	 */
	public void startGame();
	
	/**
	 * @throws GameStatusException
	 */
	public void pauseGame();
	
	/**
	 * @throws GameStatusException
	 */
	public void resumeGame();
	
	/**
	 * @throws GameStatusException
	 */
	public void stopGame();
	

	/**
	 * @throws GameStatusException
	 */
	public void updateGame();

	/**
	 * @throws GameStatusException
	 */
	public void renderGame();
	
	
	/**
	 * @return The current game status.
	 */
	public GameStatus getGameStatus();
	
	
	

	/** Activates the given mode.
	 * <p>
	 * If the mode is already active, there will be no effect.
	 * 
	 * @param mode
	 */
	public void enterMode(String mode);
	
	/** Deactivates the given mode.
	 * <p>
	 * If the mode is not active, there will be no effect.
	 * 
	 * @param mode
	 */
	public void exitMode(String mode);

	/** Returns whether the given mode is active or not.
	 * 
	 * @param mode
	 * @return True if the given mode is active, false if not.
	 */
	public boolean isModeActive(String mode);

	/**
	 * @return An unmodifiable set of the currently active modes.
	 */
	public Set<String> getActiveModes();
	
	

	/** Equivalent to {@code registerStatusListener(stateListener, true)}.
	 */
	public void registerStatusListener(IGameStatusListener stateListener);
	public void registerStatusListener(IGameStatusListener stateListener, boolean receivePast);
	
	public void removeStatusListener(IGameStatusListener stateListener);

	
	/** Equivalent to {@code registerModeListener(modeListener, true)}.
	 */
	public void registerModeListener(IGameModeListener modeListener);
	public void registerModeListener(IGameModeListener modeListener, boolean receivePast);
	
	public void removeModeListener(IGameModeListener modeListener);
	
}
