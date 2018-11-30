package com.tokelon.toktales.desktop.input;

import com.tokelon.toktales.core.engine.input.IInputDriver;

public interface IDesktopInputDriver extends IInputDriver {
	// TODO: Make this window dependent
	
	public int getKeyState(int vk);

	public boolean isKeyPressed(int vk); // Is this really needed?
	
}
