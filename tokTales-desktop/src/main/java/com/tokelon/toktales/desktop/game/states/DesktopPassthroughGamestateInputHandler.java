package com.tokelon.toktales.desktop.game.states;

import com.tokelon.toktales.core.game.states.IGameState;
import com.tokelon.toktales.core.game.states.IGameStateInputHandler;
import com.tokelon.toktales.desktop.input.IDesktopInputRegistration.ICharInputCallback;
import com.tokelon.toktales.desktop.input.IDesktopInputRegistration.ICursorMoveCallback;
import com.tokelon.toktales.desktop.input.IDesktopInputRegistration.IKeyInputCallback;
import com.tokelon.toktales.desktop.input.IDesktopInputRegistration.IMouseButtonCallback;

public class DesktopPassthroughGamestateInputHandler implements IGameStateInputHandler, IMouseButtonCallback, ICursorMoveCallback, IKeyInputCallback, ICharInputCallback {

	private static final String ACTION_CHAR_INPUT = "desktop_passthrough_action_char_input";
	private static final String TARGET_CHAR_INPUT = "desktop_passthrough_target_char_input";

	private static final String ACTION_KEY_INPUT = "desktop_passthrough_action_key_input";
	private static final String TARGET_KEY_INPUT = "desktop_passthrough_target_key_input";

	private static final String ACTION_CURSOR_MOVE = "desktop_passthrough_action_cursor_move";
	private static final String TARGET_CURSOR_MOVE = "desktop_passthrough_target_cursor_move";

	private static final String ACTION_MOUSE_BUTTON = "desktop_passthrough_action_mouse_button";
	private static final String TARGET_MOUSE_BUTTON = "desktop_passthrough_target_mouse_button";
	
	
	private final IGameState gamestate;

	public DesktopPassthroughGamestateInputHandler(IGameState gamestate) {
		this.gamestate = gamestate;
	}
	
	

	public void register(IDesktopGameStateInput stateInput) {
		stateInput.registerMouseButtonCallback(this);
		stateInput.registerCursorMoveCallback(this);
		stateInput.registerKeyInputCallback(this);
		stateInput.registerCharInputCallback(this);
	}
	
	public void unregister(IDesktopGameStateInput stateInput) {
		stateInput.unregisterMouseButtonCallback(this);
		stateInput.unregisterCursorMoveCallback(this);
		stateInput.unregisterKeyInputCallback(this);
		stateInput.unregisterCharInputCallback(this);
	}
	
	
	@Override
	public boolean invokeCharInput(int codepoint) {
		return gamestate.getStateControlHandler().handleAction(TARGET_CHAR_INPUT, ACTION_CHAR_INPUT, codepoint);
	}

	@Override
	public boolean invokeKeyInput(int vk, int action) {
		return gamestate.getStateControlHandler().handleAction(TARGET_KEY_INPUT, ACTION_KEY_INPUT, vk, action);
	}

	@Override
	public boolean invokeCursorMove(double xpos, double ypos) {
		return gamestate.getStateControlHandler().handleAction(TARGET_CURSOR_MOVE, ACTION_CURSOR_MOVE, xpos, ypos);
	}

	@Override
	public boolean invokeMouseButton(int vb, int action) {
		return gamestate.getStateControlHandler().handleAction(TARGET_MOUSE_BUTTON, ACTION_MOUSE_BUTTON, vb, action);
	}

	
}
