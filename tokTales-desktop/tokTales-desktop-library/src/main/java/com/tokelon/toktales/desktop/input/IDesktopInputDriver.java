package com.tokelon.toktales.desktop.input;

import com.tokelon.toktales.core.engine.input.IInputDriver;
import com.tokelon.toktales.desktop.ui.window.IWindow;

public interface IDesktopInputDriver extends IInputDriver {


	public void register(IWindow window);
	
	public void unregister();

	
	public int getKeyState(int vk);

	public boolean isKeyPressed(int vk); // Is this really needed?
	
}
