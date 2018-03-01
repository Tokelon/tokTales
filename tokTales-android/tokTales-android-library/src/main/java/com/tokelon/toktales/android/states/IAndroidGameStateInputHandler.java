package com.tokelon.toktales.android.states;

import com.tokelon.toktales.android.input.IAndroidInputRegistration.IScreenButtonCallback;
import com.tokelon.toktales.android.input.IAndroidInputRegistration.IScreenPointerCallback;
import com.tokelon.toktales.android.input.IAndroidInputRegistration.IScreenPressCallback;
import com.tokelon.toktales.core.game.states.IGameStateInputHandler;

public interface IAndroidGameStateInputHandler extends IGameStateInputHandler, IScreenButtonCallback, IScreenPressCallback, IScreenPointerCallback {

	
	@Override
	public default boolean invokeScreenButton(int vb, int action) {	return false; }

	@Override
	public default boolean invokeScreenPress(double xpos, double ypos) { return false; }

	@Override
	public default boolean invokeScreenPointer(int pointerId, int action, double xpos, double ypos) { return false; }

}
