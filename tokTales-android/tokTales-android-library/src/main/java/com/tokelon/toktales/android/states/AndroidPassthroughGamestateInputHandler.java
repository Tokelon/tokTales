package com.tokelon.toktales.android.states;

import com.tokelon.toktales.android.input.IAndroidInputRegistration.IScreenButtonCallback;
import com.tokelon.toktales.android.input.IAndroidInputRegistration.IScreenPointerCallback;
import com.tokelon.toktales.android.input.IAndroidInputRegistration.IScreenPressCallback;
import com.tokelon.toktales.android.input.events.IScreenButtonInputEvent;
import com.tokelon.toktales.android.input.events.IScreenPointerInputEvent;
import com.tokelon.toktales.android.input.events.IScreenPressInputEvent;
import com.tokelon.toktales.core.engine.input.IInputCallback;
import com.tokelon.toktales.core.engine.input.IInputEvent;
import com.tokelon.toktales.core.game.states.IGameState;
import com.tokelon.toktales.core.game.states.IGameStateInputHandler;

public class AndroidPassthroughGamestateInputHandler implements IGameStateInputHandler,
	IInputCallback, IScreenButtonCallback, IScreenPressCallback, IScreenPointerCallback {

	private static final String ACTION_GENERIC = "android_passthrough_action_generic";
	private static final String TARGET_GENERIC = "android_passthrough_target_generic";
	
	public static final String ACTION_SCREEN_POINTER = "android_passthrough_action_screen_pointer";
	public static final String TARGET_SCREEN_POINTER = "android_passthrough_target_screen_pointer";

	public static final String ACTION_SCREEN_PRESS = "android_passthrough_action_screen_press";
	public static final String TARGET_SCREEN_PRESS = "android_passthrough_target_screen_press";
	
	public static final String ACTION_SCREEN_BUTTON = "android_passthrough_action_screen_button";
	public static final String TARGET_SCREEN_BUTTON = "android_passthrough_target_screen_button";
	
	
	private final IGameState gamestate;
	
	public AndroidPassthroughGamestateInputHandler(IGameState gamestate) {
		this.gamestate = gamestate;
	}

	
	
	public void register(IAndroidGameStateInput stateInput) {
		stateInput.registerInputCallback(this);
		stateInput.registerInputCallback(this, IInputCallback.class);
	}
	
	public void unregister(IAndroidGameStateInput stateInput) {
		stateInput.unregisterInputCallback(this);
		stateInput.unregisterInputCallback(this, IInputCallback.class);
	}


	@Override
	public boolean handle(IInputEvent event) {
		return gamestate.getStateControlHandler().handleAction(TARGET_GENERIC, ACTION_GENERIC, event);
	}

	@Override
	public boolean handleScreenButtonInput(IScreenButtonInputEvent event) {
		return gamestate.getStateControlHandler().handleAction(TARGET_SCREEN_BUTTON, ACTION_SCREEN_BUTTON, event.getButton(), event.getAction());
	}
	
	@Override
	public boolean handleScreenPressInput(IScreenPressInputEvent event) {
		return gamestate.getStateControlHandler().handleAction(TARGET_SCREEN_PRESS, ACTION_SCREEN_PRESS, event.getXPos(), event.getYPos());
	}
	
	@Override
	public boolean handleScreenPointerInput(IScreenPointerInputEvent event) {
		return gamestate.getStateControlHandler().handleAction(TARGET_SCREEN_POINTER, ACTION_SCREEN_POINTER, event.getPointerId(), event.getAction(), event.getXPos(), event.getYPos());
	}
	
}
