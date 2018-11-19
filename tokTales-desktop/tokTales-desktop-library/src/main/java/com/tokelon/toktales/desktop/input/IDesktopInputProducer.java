package com.tokelon.toktales.desktop.input;

import com.tokelon.toktales.core.engine.input.IInputProducer;
import com.tokelon.toktales.desktop.input.IDesktopInputDriver.InputCharCallback;
import com.tokelon.toktales.desktop.input.IDesktopInputDriver.InputCursorMoveCallback;
import com.tokelon.toktales.desktop.input.IDesktopInputDriver.InputKeyCallback;
import com.tokelon.toktales.desktop.input.IDesktopInputDriver.InputMouseButtonCallback;

public interface IDesktopInputProducer extends IInputProducer {
	
	/* TODO:
	 * Move actions KEY_PRESS etc. into here?
	 * 
	 */
	
	// This is more dynamic/modular because you can add actions etc.
	//public void keyAction(int vk, int action);
	//public void keyAction(int vk, int action, int mods);
	
	
	/* Use ?
	public void onKeyPress(int vk);
	public void onKeyRelease(int vk);
	public void onKeyRepeat(int vk);
	*/

	//public void onMouseButtonAction(int vb, int action);
	//public void mouseButtonAction(int vb, int action);
	
	
	public InputMouseButtonCallback getMouseInput();
	
	public InputCursorMoveCallback getCursorInput();
	
	public InputKeyCallback getKeyInput();
	
	public InputCharCallback getCharInput();
	
}
