package com.tokelon.toktales.desktop.game.states;

import com.tokelon.toktales.core.game.states.IGameStateInput;
import com.tokelon.toktales.desktop.input.IDesktopInputRegistration;

public interface IDesktopGameStateInput extends IGameStateInput, IDesktopInputRegistration {

	
	/**
	 * @return The master mouse button callback, used for posting events.
	 */
	public IMouseButtonCallback getMasterMouseButtonCallback();
	
	/**
	 * @return The master cursor move callback, used for posting events.
	 */
	public ICursorMoveCallback getMasterCursorMoveCallback();
	
	/**
	 * @return The master key input callback, used for posting events.
	 */
	public IKeyInputCallback getMasterKeyInputCallback();
	
	/**
	 * @return The master char input callback, used for posting events.
	 */
	public ICharInputCallback getMasterCharInputCallback();
	
}
