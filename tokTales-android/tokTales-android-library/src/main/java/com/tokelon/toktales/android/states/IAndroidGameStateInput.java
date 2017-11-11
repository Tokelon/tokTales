package com.tokelon.toktales.android.states;

import com.tokelon.toktales.android.input.IAndroidInputRegistration;
import com.tokelon.toktales.core.game.states.IGameStateInput;

public interface IAndroidGameStateInput extends IGameStateInput, IAndroidInputRegistration {
	
	
	/**
	 * @return The master screen button callback, used for posting events.
	 */
	public IScreenButtonCallback getMasterScreenButtonCallback();
	
	/**
	 * @return The master screen press callback, used for posting events.
	 */
	public IScreenPressCallback getMasterScreenPressCallback();
	
	/**
	 * @return The master screen pointer callback, used for posting events.
	 */
	public IScreenPointerCallback getMasterScreenPointerCallback();
	
}
