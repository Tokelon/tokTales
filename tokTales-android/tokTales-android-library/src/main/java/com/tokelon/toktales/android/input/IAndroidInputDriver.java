package com.tokelon.toktales.android.input;

import com.tokelon.toktales.core.engine.input.IInputDriver;

public interface IAndroidInputDriver extends IInputDriver {
	
	
	//public int getButtonState(int vb);
	
	
	public void setScreenButtonCallback(InputScreenButtonCallback callback);
	
	public void setScreenPressCallback(InputScreenPressCallback callback);
	
	public void setScreenPointerCallback(InputScreenPointerCallback callback);
	
	
	public interface InputScreenButtonCallback {
		
		public void invoke(int vb, int action);	// could pass more parameters here
	}
	
	public interface InputScreenPressCallback {
		
		public void invoke(double xpos, double ypos);
	}
	
	public interface InputScreenPointerCallback {
		
		public void invoke(int pointerId, int action, double xpos, double ypos);
	}
	
	
}
