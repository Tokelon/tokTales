package com.tokelon.toktales.desktop.input;

import com.tokelon.toktales.core.engine.input.IInputDriver;

public interface IDesktopInputDriver extends IInputDriver {
	
	
	public int getKeyState(int vk);
	
	
	
	public void setMouseButtonCallback(InputMouseButtonCallback callback);
	
	public void setCursorMoveCallback(InputCursorMoveCallback callback);
	
	public void setKeyCallback(InputKeyCallback callback);
	
	public void setCharCallback(InputCharCallback callback);
	
	
	
	// Add boolean return for handled value?
	
	public interface InputMouseButtonCallback {
		
		public void invoke(int vb, int action, int mods);
	}
	
	public interface InputCursorMoveCallback {
		
		public void invoke(double xpos, double ypos);
	}
	
	public interface InputKeyCallback {
		
		public void invoke(int vk, int action, int scancode, int mods);
	}
	
	public interface InputCharCallback {
		
		public void invoke(int codepoint);
	}
	

}
