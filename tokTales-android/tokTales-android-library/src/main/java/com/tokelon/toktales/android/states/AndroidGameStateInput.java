package com.tokelon.toktales.android.states;

import java.util.Set;

import com.tokelon.toktales.android.input.AndroidInputRegistration;
import com.tokelon.toktales.android.input.IAndroidInputRegistration.IScreenButtonCallback;
import com.tokelon.toktales.android.input.IAndroidInputRegistration.IScreenPointerCallback;
import com.tokelon.toktales.android.input.IAndroidInputRegistration.IScreenPressCallback;

public class AndroidGameStateInput extends AndroidInputRegistration implements IAndroidGameStateInput, IScreenButtonCallback, IScreenPressCallback, IScreenPointerCallback {

	
	@Override
	public boolean invokeScreenButton(int vb, int action) {
		boolean wasHandled = false;
		
		Set<IScreenButtonCallback> screenButtonCallbackSet = getScreenButtonCallbackSet(); 
		synchronized (screenButtonCallbackSet) {
			for(IScreenButtonCallback callback: screenButtonCallbackSet) {
				wasHandled = callback.invokeScreenButton(vb, action) || wasHandled;
			}
		}
		
		return wasHandled;
	}

	@Override
	public boolean invokeScreenPress(double xpos, double ypos) {
		boolean wasHandled = false;
		
		Set<IScreenPressCallback> screenPressCallbackSet = getScreenPressCallbackSet();
		synchronized (screenPressCallbackSet) {
			for(IScreenPressCallback callback: screenPressCallbackSet) {
				wasHandled = callback.invokeScreenPress(xpos, ypos) || wasHandled;
			}
		}
		
		return wasHandled;
	}
	
	@Override
	public boolean invokeScreenPointer(int pointerId, int action, double xpos, double ypos) {
		boolean wasHandled = false;
		
		Set<IScreenPointerCallback> screenPointerCallbackSet = getScreenPointerCallbackSet();
		synchronized (screenPointerCallbackSet) {
			for(IScreenPointerCallback callback: screenPointerCallbackSet) {
				wasHandled = callback.invokeScreenPointer(pointerId, action, xpos, ypos) || wasHandled;
			}
		}
		
		return wasHandled;
	}
	

	@Override
	public IScreenButtonCallback getMasterScreenButtonCallback() {
		return this;
	}
	
	@Override
	public IScreenPressCallback getMasterScreenPressCallback() {
		return this;
	}
	
	@Override
	public IScreenPointerCallback getMasterScreenPointerCallback() {
		return this;
	}
	
	
}
