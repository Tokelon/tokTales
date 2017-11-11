package com.tokelon.toktales.desktop.input;

import com.tokelon.toktales.core.engine.input.IInputDispatcher;

public interface IDesktopInputDispatcher extends IInputDispatcher, IDesktopInputRegistration {

	
	public int getKeyState(int vk);
	
	public boolean isKeyPressed(int vk);
	
}
