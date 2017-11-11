package com.tokelon.toktales.android.input;

import com.tokelon.toktales.android.render.tools.UIControl;

public class AndroidInputDriver implements IAndroidInputDriver {

	/** This essentially wraps the UIControl that does all the actual input driver work
	 * 
	 */
	
	private InputScreenButtonCallback inputScreenButtonCallback;
	private InputScreenPressCallback inputScreenPressCallback;
	private InputScreenPointerCallback inputScreenPointerCallback;

	private final UIControl uiControl;
	
	public AndroidInputDriver(UIControl uiControl) {
		this.uiControl = uiControl;
		
		uiControl.setScreenButtonInputCallback(new DriverScreenButtonCallback());
		uiControl.setScreenPressInputCallback(new DriverScreenPressCallback());
		uiControl.setScreenPointerInputCallback(new DriverScreenPointerCallback());
	}
	

	
	
	private class DriverScreenButtonCallback implements InputScreenButtonCallback {

		@Override
		public void invoke(int vb, int action) {
			if(inputScreenButtonCallback != null) {
				inputScreenButtonCallback.invoke(vb, action);	
			}
		}
	}
	
	private class DriverScreenPressCallback implements InputScreenPressCallback {

		@Override
		public void invoke(double xpos, double ypos) {
			if(inputScreenPressCallback != null) {
				inputScreenPressCallback.invoke(xpos, ypos);
			}
		}
	}
	
	private class DriverScreenPointerCallback implements InputScreenPointerCallback {

		@Override
		public void invoke(int pointerId, int action, double xpos, double ypos) {
			if(inputScreenPointerCallback != null) {
				inputScreenPointerCallback.invoke(pointerId, action, xpos, ypos);
			}
		}
	}
	
	
	@Override
	public void setScreenButtonCallback(InputScreenButtonCallback callback) {
		this.inputScreenButtonCallback = callback;
	}

	@Override
	public void setScreenPressCallback(InputScreenPressCallback callback) {
		this.inputScreenPressCallback = callback;
	}
	
	@Override
	public void setScreenPointerCallback(InputScreenPointerCallback callback) {
		this.inputScreenPointerCallback = callback;
	}

}
