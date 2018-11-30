package com.tokelon.toktales.android.states;

import com.tokelon.toktales.android.input.IAndroidInputRegistration.IScreenButtonCallback;
import com.tokelon.toktales.android.input.IAndroidInputRegistration.IScreenPointerCallback;
import com.tokelon.toktales.android.input.IAndroidInputRegistration.IScreenPressCallback;
import com.tokelon.toktales.android.input.events.IScreenButtonInputEvent;
import com.tokelon.toktales.android.input.events.IScreenPointerInputEvent;
import com.tokelon.toktales.android.input.events.IScreenPressInputEvent;
import com.tokelon.toktales.core.engine.input.IInputEvent;
import com.tokelon.toktales.core.game.states.IGameStateInputHandler;

public interface IAndroidGameStateInputHandler extends IGameStateInputHandler,
	IScreenButtonCallback, IScreenPressCallback, IScreenPointerCallback {

	
	@Override
	public default boolean handle(IInputEvent event) { return false; }
	
	@Override
	public default boolean handleScreenButtonInput(IScreenButtonInputEvent event) { return false; }
	
	@Override
	public default boolean handleScreenPressInput(IScreenPressInputEvent event) { return false; }
	
	@Override
	public default boolean handleScreenPointerInput(IScreenPointerInputEvent event) { return false; }
	
}
