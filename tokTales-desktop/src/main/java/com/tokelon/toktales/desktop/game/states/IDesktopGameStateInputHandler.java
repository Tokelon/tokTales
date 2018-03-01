package com.tokelon.toktales.desktop.game.states;

import com.tokelon.toktales.core.game.states.IGameStateInputHandler;
import com.tokelon.toktales.desktop.input.IDesktopInputRegistration.ICharInputCallback;
import com.tokelon.toktales.desktop.input.IDesktopInputRegistration.ICursorMoveCallback;
import com.tokelon.toktales.desktop.input.IDesktopInputRegistration.IKeyInputCallback;
import com.tokelon.toktales.desktop.input.IDesktopInputRegistration.IMouseButtonCallback;

public interface IDesktopGameStateInputHandler extends IGameStateInputHandler, IMouseButtonCallback, ICursorMoveCallback, IKeyInputCallback, ICharInputCallback {

	
	@Override
	public default boolean invokeCharInput(int codepoint) {	return false; }

	@Override
	public default boolean invokeKeyInput(int vk, int action) {	return false; }

	@Override
	public default boolean invokeCursorMove(double xpos, double ypos) {	return false; }

	@Override
	public default boolean invokeMouseButton(int vb, int action) { return false; }

}
