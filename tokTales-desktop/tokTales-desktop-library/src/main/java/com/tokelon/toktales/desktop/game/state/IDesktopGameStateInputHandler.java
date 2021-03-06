package com.tokelon.toktales.desktop.game.state;

import com.tokelon.toktales.core.engine.input.IInputEvent;
import com.tokelon.toktales.core.game.state.IGameStateInputHandler;
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

public interface IDesktopGameStateInputHandler extends IGameStateInputHandler,
	IMouseButtonCallback, ICursorEnterCallback, ICursorPosCallback, IKeyInputCallback, ICharInputCallback {

	
	@Override
	public default boolean handle(IInputEvent event) { return false; }
	
	@Override
	public default boolean handleCharInput(ICharInputEvent event) {	return false; }
	
	@Override
	public default boolean handleCursorEnterInput(ICursorEnterInputEvent event) { return false; }
	
	@Override
	public default boolean handleCursorPosInput(ICursorPosInputEvent event) { return false; }
	
	@Override
	public default boolean handleKeyInput(IKeyInputEvent event) { return false; }
	
	@Override
	public default boolean handleMouseButtonInput(IMouseButtonInputEvent event) { return false; }
	
}
