package com.tokelon.toktales.android.states;

import com.tokelon.toktales.android.input.IAndroidInputRegistration.IScreenButtonCallback;
import com.tokelon.toktales.android.input.IAndroidInputRegistration.IScreenPointerCallback;
import com.tokelon.toktales.android.input.IAndroidInputRegistration.IScreenPressCallback;
import com.tokelon.toktales.core.game.states.GameStateControl;
import com.tokelon.toktales.core.game.states.IGameStateInput;
import com.tokelon.toktales.android.input.IAndroidInputService;

public class AndroidGameStateManager extends GameStateControl {
	
	
	public AndroidGameStateManager(IAndroidInputService inputService) {
		inputService.getInputDispatcher().registerScreenButtonCallback(new GamestateControlScreenButtonCallback());
		inputService.getInputDispatcher().registerScreenPressCallback(new GamestateControlScreenPressCallback());
		inputService.getInputDispatcher().registerScreenPointerCallback(new GamestateControlScreenPointerCallback());
	}

	
	
	private class GamestateControlScreenButtonCallback implements IScreenButtonCallback {

		@Override
		public boolean invokeScreenButton(int vb, int action) {
			IGameStateInput currentStateInput = getActiveState().getStateInput();
			
			if(currentStateInput instanceof IAndroidGameStateInput) {
				IAndroidGameStateInput androidStateInput = (IAndroidGameStateInput) currentStateInput;
				return androidStateInput.getMasterScreenButtonCallback().invokeScreenButton(vb, action);
			}
			
			return false;
		}
	}
	
	private class GamestateControlScreenPressCallback implements IScreenPressCallback {

		@Override
		public boolean invokeScreenPress(double xpos, double ypos) {
			IGameStateInput currentStateInput = getActiveState().getStateInput();
			
			if(currentStateInput instanceof IAndroidGameStateInput) {
				IAndroidGameStateInput androidStateInput = (IAndroidGameStateInput) currentStateInput;
				return androidStateInput.getMasterScreenPressCallback().invokeScreenPress(xpos, ypos);
			}
			
			return false;
		}
	}
	
	private class GamestateControlScreenPointerCallback implements IScreenPointerCallback {

		@Override
		public boolean invokeScreenPointer(int pointerId, int action, double xpos, double ypos) {
			IGameStateInput currentStateInput = getActiveState().getStateInput();
			
			if(currentStateInput instanceof IAndroidGameStateInput) {
				IAndroidGameStateInput androidStateInput = (IAndroidGameStateInput) currentStateInput;
				return androidStateInput.getMasterScreenPointerCallback().invokeScreenPointer(pointerId, action, xpos, ypos);
			}
			
			return false;
		}
	}
	

}
