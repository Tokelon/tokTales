package com.tokelon.toktales.android.states;

import javax.inject.Inject;

import com.tokelon.toktales.android.input.IAndroidInputService;
import com.tokelon.toktales.android.input.dispatch.IAndroidInputRegistration.IScreenButtonCallback;
import com.tokelon.toktales.android.input.dispatch.IAndroidInputRegistration.IScreenPointerCallback;
import com.tokelon.toktales.android.input.dispatch.IAndroidInputRegistration.IScreenPressCallback;
import com.tokelon.toktales.android.input.events.IScreenButtonInputEvent;
import com.tokelon.toktales.android.input.events.IScreenPointerInputEvent;
import com.tokelon.toktales.android.input.events.IScreenPressInputEvent;
import com.tokelon.toktales.core.engine.input.IInputCallback;
import com.tokelon.toktales.core.engine.input.IInputEvent;
import com.tokelon.toktales.core.engine.log.ILogger;
import com.tokelon.toktales.core.game.states.GameStateControl;
import com.tokelon.toktales.core.game.states.IGameStateInput;

public class AndroidGameStateControl extends GameStateControl {
	
	
	private final AndroidGamestateInputForwarder inputForwarder;
	
	@Inject
	public AndroidGameStateControl(ILogger logger, IAndroidInputService inputService) {
		super(logger);
		
		this.inputForwarder = new AndroidGamestateInputForwarder();
		inputService.getMainInputDispatch().getInputConsumer().registerInputCallback(inputForwarder);
		inputService.getMainInputDispatch().getInputConsumer().registerInputCallback(inputForwarder, IInputCallback.class);
	}

	
	
	protected class AndroidGamestateInputForwarder implements
		IInputCallback, IScreenButtonCallback, IScreenPressCallback, IScreenPointerCallback {


		@Override
		public boolean handle(IInputEvent event) {
			IGameStateInput currentStateInput = getActiveState().getStateInput();
			
			if(currentStateInput instanceof IAndroidGameStateInput) {
				IAndroidGameStateInput androidStateInput = (IAndroidGameStateInput) currentStateInput;
				return androidStateInput.handle(event);
			}
			else {
				currentStateInput.handle(event);
			}
			
			return false;
		}

		@Override
		public boolean handleScreenButtonInput(IScreenButtonInputEvent event) {
			IGameStateInput currentStateInput = getActiveState().getStateInput();
			
			if(currentStateInput instanceof IAndroidGameStateInput) {
				IAndroidGameStateInput androidStateInput = (IAndroidGameStateInput) currentStateInput;
				return androidStateInput.handleScreenButtonInput(event);
			}
			else {
				currentStateInput.handle(event);
			}
			
			return false;
		}

		@Override
		public boolean handleScreenPressInput(IScreenPressInputEvent event) {
			IGameStateInput currentStateInput = getActiveState().getStateInput();
			
			if(currentStateInput instanceof IAndroidGameStateInput) {
				IAndroidGameStateInput androidStateInput = (IAndroidGameStateInput) currentStateInput;
				return androidStateInput.handleScreenPressInput(event);
			}
			else {
				currentStateInput.handle(event);
			}
			
			return false;
		}

		@Override
		public boolean handleScreenPointerInput(IScreenPointerInputEvent event) {
			IGameStateInput currentStateInput = getActiveState().getStateInput();
			
			if(currentStateInput instanceof IAndroidGameStateInput) {
				IAndroidGameStateInput androidStateInput = (IAndroidGameStateInput) currentStateInput;
				return androidStateInput.handleScreenPointerInput(event);
			}
			else {
				currentStateInput.handle(event);
			}
			
			return false;
		}
	}

}
