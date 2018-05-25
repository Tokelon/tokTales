package com.tokelon.toktales.core.game.control;

import com.tokelon.toktales.core.game.control.IGameControl.GameStatus;

public interface IGameStatusListener extends IGameListener {

	
	/** Called when the game status changes.
	 * <p>
	 * The default implementation will call one of the game*() callbacks, depending on the status given.
	 * 
	 * @param newGameStatus The new game status.
	 */
	public default void gameStatusChanged(GameStatus newGameStatus) {
		switch (newGameStatus) {
		case CREATED:
			gameCreated();
			break;
		case STARTED:
			gameStarted();
			break;
		case RUNNING:
			gameRunning();
			break;
		case PAUSED:
			gamePaused();
			break;
		case STOPPED:
			gameStopped();
			break;
		case DESTROYED:
			gameDestroyed();
			break;
		}
	}
	
	
	public default void gameCreated() { }
	
	public default void gameStarted() { }
	
	public default void gameRunning() { }
	
	public default void gamePaused() { }
	
	public default void gameStopped() { }
	
	public default void gameDestroyed() { }
	
}
