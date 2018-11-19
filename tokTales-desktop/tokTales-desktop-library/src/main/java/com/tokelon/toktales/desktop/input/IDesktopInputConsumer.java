package com.tokelon.toktales.desktop.input;

import com.tokelon.toktales.core.engine.input.IInputConsumer;

public interface IDesktopInputConsumer extends IInputConsumer, IDesktopInputRegistration {

	
	public int getKeyState(int vk);
	
	public boolean isKeyPressed(int vk);
	
}
