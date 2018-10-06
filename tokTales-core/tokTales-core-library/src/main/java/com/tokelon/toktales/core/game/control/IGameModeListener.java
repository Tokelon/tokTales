package com.tokelon.toktales.core.game.control;

public interface IGameModeListener extends IGameListener {

	
	/** Called when a game mode changes.
	 * <p>
	 * The default implementation will call {@link #gameEnteredMode(String)} if active is true, or {@link #gameExitedMode(String)} if active is false.
	 * 
	 * @param mode The game mode that changed.
	 * @param active True if the mode is active, false if not.
	 */
	public default void gameModeChanged(String mode, boolean active) {
		if(active) {
			gameEnteredMode(mode);
		}
		else {
			gameExitedMode(mode);
		}
	}
	
	
	public default void gameEnteredMode(String mode) { } //gameModeActivated?
	
	public default void gameExitedMode(String mode) { }
	
}
