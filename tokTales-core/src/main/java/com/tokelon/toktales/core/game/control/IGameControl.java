package com.tokelon.toktales.core.game.control;

/** Implementations must be threadsafe! -> Why?
 * 
 * 
 *
 */
public interface IGameControl {

	// TODO: Document the importance of thread safety
	
	
	/*
	public static final int GAME_MODE_EMPTY = 0;
	public static final int GAME_MODE_FPS = 1;
	public static final int GAME_MODE_EDIT = 2;
	public static final int GAME_MODE_MAP = 3;
	public static final int GAME_MODE_DIALOG = 4;
	public static final int GAME_MODE_CUTSCENE = 5;
	*/

	
	
	/* TODO: Move these into IGame ?
	 * 
	 */
	
	public void createGame();
	
	public void destroyGame();	// exitGame()
	
	
	
	
	
	//public void start(String gamestateName, IGameState gamestate);

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
	
	
	
	// TODO: Probably refactor modes
	
	/**
	 * @throws GameStatusException
	 */
	public void enterMode(int mode);
	
	/**
	 * @throws GameStatusException
	 */
	public void exitMode(int mode);

	
	public boolean isModeActivated(int mode);
	
	
	
	/** Calls {@code registerModeListener(modeListener, true)}.
	 */
	public void registerModeListener(IGameModeListener modeListener);
	public void registerModeListener(IGameModeListener modeListener, boolean receivePast);
	
	/** Calls {@code registerModeListener(basicModeListener, true)}.
	 */
	public void registerModeListener(IGameModeBasicListener basicModeListener);
	public void registerModeListener(IGameModeBasicListener basicModeListener, boolean receivePast);
	
	/** Calls {@code registerStateListener(stateListener, true)}.
	 */
	public void registerStatusListener(IGameStatusListener stateListener);
	public void registerStatusListener(IGameStatusListener stateListener, boolean receivePast);
	
	
	
	public void removeModeListener(IGameModeListener modeListener);
	public void removeModeListener(IGameModeBasicListener basicModeListener);
	
	public void removeStateListener(IGameStatusListener stateListener);
	
}
