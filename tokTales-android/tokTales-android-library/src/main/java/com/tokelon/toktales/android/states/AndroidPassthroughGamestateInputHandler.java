package com.tokelon.toktales.android.states;

import com.tokelon.toktales.android.input.IAndroidInputRegistration.IScreenButtonCallback;
import com.tokelon.toktales.android.input.IAndroidInputRegistration.IScreenPointerCallback;
import com.tokelon.toktales.android.input.IAndroidInputRegistration.IScreenPressCallback;
import com.tokelon.toktales.core.game.states.IGameState;
import com.tokelon.toktales.core.game.states.IGameStateInputHandler;

public class AndroidPassthroughGamestateInputHandler implements IGameStateInputHandler, IScreenButtonCallback, IScreenPressCallback, IScreenPointerCallback {

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
		stateInput.registerScreenButtonCallback(this);
		stateInput.registerScreenPressCallback(this);
		stateInput.registerScreenPointerCallback(this);
	}
	
	public void unregister(IAndroidGameStateInput stateInput) {
		stateInput.unregisterScreenButtonCallback(this);
		stateInput.unregisterScreenPressCallback(this);
		stateInput.unregisterScreenPointerCallback(this);
	}
	
	
	@Override
	public boolean invokeScreenPointer(int pointerId, int action, double xpos, double ypos) {
		return gamestate.getStateControlHandler().handleAction(TARGET_SCREEN_POINTER, ACTION_SCREEN_POINTER, pointerId, action, xpos, ypos);
	}

	@Override
	public boolean invokeScreenPress(double xpos, double ypos) {
		return gamestate.getStateControlHandler().handleAction(TARGET_SCREEN_PRESS, ACTION_SCREEN_PRESS, xpos, ypos);
	}

	@Override
	public boolean invokeScreenButton(int vb, int action) {
		return gamestate.getStateControlHandler().handleAction(TARGET_SCREEN_BUTTON, ACTION_SCREEN_BUTTON, vb, action);
	}

	
}
