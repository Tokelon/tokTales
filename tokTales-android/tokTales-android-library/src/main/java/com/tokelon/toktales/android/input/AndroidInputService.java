package com.tokelon.toktales.android.input;

import java.util.Set;

import com.tokelon.toktales.android.input.IAndroidInputDriver.InputScreenButtonCallback;
import com.tokelon.toktales.android.input.IAndroidInputDriver.InputScreenPointerCallback;
import com.tokelon.toktales.android.input.IAndroidInputDriver.InputScreenPressCallback;
import com.tokelon.toktales.android.input.IAndroidInputRegistration.IScreenButtonCallback;
import com.tokelon.toktales.android.input.IAndroidInputRegistration.IScreenPointerCallback;
import com.tokelon.toktales.android.input.IAndroidInputRegistration.IScreenPressCallback;
import com.tokelon.toktales.core.engine.AbstractEngineService;

public class AndroidInputService extends AbstractEngineService implements IAndroidInputService {

	private final MasterScreenButtonCallback masterScreenButtonCallback = new MasterScreenButtonCallback();
	private final MasterScreenPressCallback masterScreenPressCallback = new MasterScreenPressCallback();
	private final MasterScreenPointerCallback masterScreenPointerCallback = new MasterScreenPointerCallback();

	
	private final AndroidInputPoster inputPoster;
	private final AndroidInputDispatcher inputDispatcher;
	
	public AndroidInputService() {
		this.inputPoster = new AndroidInputPoster();
		this.inputDispatcher = new AndroidInputDispatcher();
	}
	
	
	
	@Override
	public IAndroidInputPoster getInputPoster() {
		return inputPoster;
	}

	@Override
	public IAndroidInputDispatcher getInputDispatcher() {
		return inputDispatcher;
	}

	@Override
	public void setInputDriver(IAndroidInputDriver inputDriver) {
		inputDriver.setScreenButtonCallback(masterScreenButtonCallback);
		inputDriver.setScreenPressCallback(masterScreenPressCallback);
		inputDriver.setScreenPointerCallback(masterScreenPointerCallback);
	}
	
	

	// Consume event if one callback has it handled? | invoke() return value
	
	private class MasterScreenButtonCallback implements InputScreenButtonCallback {

		@Override
		public void invoke(int vb, int action) {
			//TokTales.getLog().d(TAG, String.format("Button %s went down", TokelonTypeAInputs.inputToString(buttonCode)));

			Set<IScreenButtonCallback> screenButtonCallbackSet = inputDispatcher.getScreenButtonCallbackSet();
			synchronized (screenButtonCallbackSet) {
				
				for(IScreenButtonCallback callback: screenButtonCallbackSet) {
					callback.invokeScreenButton(vb, action);
				}
			}
		}
	}
	
	private class MasterScreenPressCallback implements InputScreenPressCallback {

		@Override
		public void invoke(double xpos, double ypos) {
			
			Set<IScreenPressCallback> screenPressCallbackSet = inputDispatcher.getScreenPressCallbackSet();
			synchronized (screenPressCallbackSet) {
				
				for(IScreenPressCallback callback: screenPressCallbackSet) {
					callback.invokeScreenPress(xpos, ypos);
				}
			}
		}
	}

	private class MasterScreenPointerCallback implements InputScreenPointerCallback {

		@Override
		public void invoke(int pointerId, int action, double xpos, double ypos) {
			
			Set<IScreenPointerCallback> screenPointerCallbackSet = inputDispatcher.getScreenPointerCallbackSet();
			synchronized (screenPointerCallbackSet) {
				
				for(IScreenPointerCallback callback: screenPointerCallbackSet) {
					callback.invokeScreenPointer(pointerId, action, xpos, ypos);
				}
			}
		}
	}
	
	
	
	
	private class AndroidInputPoster implements IAndroidInputPoster {

		@Override
		public InputScreenButtonCallback getScreenButtonInput() {
			return masterScreenButtonCallback;
		}
		
		@Override
		public InputScreenPressCallback getScreenPressInput() {
			return masterScreenPressCallback;
		}

		@Override
		public InputScreenPointerCallback getScreenPointerInput() {
			return masterScreenPointerCallback;
		}
	}
	
	
	private class AndroidInputDispatcher extends AndroidInputRegistration implements IAndroidInputDispatcher {
		
	}
	
	
	

	/* Implement regulation?
	 * 
	 */
	
	// Different regulate intervals for controls and buttons
	private static final int TIME_INTERVAL_CONTROL = 30;	//100
	private static final int TIME_INTERVAL_BUTTON = 100;
	
	private long lastTimeButton;
	
	private boolean regulateButton() {
		long now = System.currentTimeMillis();
		if(now - lastTimeButton >= TIME_INTERVAL_BUTTON) {
			lastTimeButton = now;
			return false;
		}
		else {
			return true;
		}
	}


}
