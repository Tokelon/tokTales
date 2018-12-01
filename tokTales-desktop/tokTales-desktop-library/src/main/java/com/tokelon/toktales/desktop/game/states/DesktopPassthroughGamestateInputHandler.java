package com.tokelon.toktales.desktop.game.states;

import com.tokelon.toktales.core.engine.input.IInputCallback;
import com.tokelon.toktales.core.engine.input.IInputEvent;
import com.tokelon.toktales.core.game.states.IGameState;
import com.tokelon.toktales.core.game.states.IGameStateInputHandler;
import com.tokelon.toktales.desktop.input.dispatch.IDesktopInputRegistration.ICharInputCallback;
import com.tokelon.toktales.desktop.input.dispatch.IDesktopInputRegistration.ICursorEnterCallback;
import com.tokelon.toktales.desktop.input.dispatch.IDesktopInputRegistration.ICursorPosCallback;
import com.tokelon.toktales.desktop.input.dispatch.IDesktopInputRegistration.IKeyInputCallback;
import com.tokelon.toktales.desktop.input.dispatch.IDesktopInputRegistration.IMouseButtonCallback;
import com.tokelon.toktales.desktop.input.events.ICharInputEvent;
import com.tokelon.toktales.desktop.input.events.ICursorEnterInputEvent;
import com.tokelon.toktales.desktop.input.events.ICursorPosInputEvent;
import com.tokelon.toktales.desktop.input.events.IKeyInputEvent;
import com.tokelon.toktales.desktop.input.events.IMouseButtonInputEvent;

public class DesktopPassthroughGamestateInputHandler implements IGameStateInputHandler,
	IInputCallback, IMouseButtonCallback, ICursorEnterCallback, ICursorPosCallback, IKeyInputCallback, ICharInputCallback {

	private static final String ACTION_GENERIC = "desktop_passthrough_action_generic";
	private static final String TARGET_GENERIC = "desktop_passthrough_target_generic";
	
	private static final String ACTION_CHAR_INPUT = "desktop_passthrough_action_char_input";
	private static final String TARGET_CHAR_INPUT = "desktop_passthrough_target_char_input";

	private static final String ACTION_KEY_INPUT = "desktop_passthrough_action_key_input";
	private static final String TARGET_KEY_INPUT = "desktop_passthrough_target_key_input";

	private static final String ACTION_CURSOR_ENTER = "desktop_passthrough_action_cursor_enter";
	private static final String TARGET_CURSOR_ENTER = "desktop_passthrough_target_cursor_enter";

	private static final String ACTION_CURSOR_MOVE = "desktop_passthrough_action_cursor_move";
	private static final String TARGET_CURSOR_MOVE = "desktop_passthrough_target_cursor_move";

	private static final String ACTION_MOUSE_BUTTON = "desktop_passthrough_action_mouse_button";
	private static final String TARGET_MOUSE_BUTTON = "desktop_passthrough_target_mouse_button";
	
	
	private final IGameState gamestate;

	public DesktopPassthroughGamestateInputHandler(IGameState gamestate) {
		this.gamestate = gamestate;
	}
	
	

	public void register(IDesktopGameStateInput stateInput) {
		stateInput.registerInputCallback(this);
		stateInput.registerInputCallback(this, IInputCallback.class);
	}
	
	public void unregister(IDesktopGameStateInput stateInput) {
		stateInput.unregisterInputCallback(this);
		stateInput.unregisterInputCallback(this, IInputCallback.class);
	}


	@Override
	public boolean handle(IInputEvent event) {
		return gamestate.getStateControlHandler().handleAction(TARGET_GENERIC, ACTION_GENERIC, event);
	}

	@Override
	public boolean handleCharInput(ICharInputEvent event) {
		return gamestate.getStateControlHandler().handleAction(TARGET_CHAR_INPUT, ACTION_CHAR_INPUT, event.getCodepoint());
	}

	@Override
	public boolean handleKeyInput(IKeyInputEvent event) {
		return gamestate.getStateControlHandler().handleAction(TARGET_KEY_INPUT, ACTION_KEY_INPUT, event.getKey(), event.getAction());
	}

	@Override
	public boolean handleCursorPosInput(ICursorPosInputEvent event) {
		return gamestate.getStateControlHandler().handleAction(TARGET_CURSOR_MOVE, ACTION_CURSOR_MOVE, event.getXPos(), event.getYPos());
	}

	@Override
	public boolean handleCursorEnterInput(ICursorEnterInputEvent event) {
		return gamestate.getStateControlHandler().handleAction(TARGET_CURSOR_ENTER, ACTION_CURSOR_ENTER, event.getEntered());
	}

	@Override
	public boolean handleMouseButtonInput(IMouseButtonInputEvent event) {
		return gamestate.getStateControlHandler().handleAction(TARGET_MOUSE_BUTTON, ACTION_MOUSE_BUTTON, event.getButton(), event.getAction());
	}
	
}
