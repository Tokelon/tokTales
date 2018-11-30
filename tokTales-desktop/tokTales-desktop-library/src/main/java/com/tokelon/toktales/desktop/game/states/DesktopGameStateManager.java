package com.tokelon.toktales.desktop.game.states;

import javax.inject.Inject;

import com.tokelon.toktales.core.engine.input.IInputCallback;
import com.tokelon.toktales.core.engine.input.IInputEvent;
import com.tokelon.toktales.core.engine.log.ILogger;
import com.tokelon.toktales.core.game.states.GameStateControl;
import com.tokelon.toktales.core.game.states.IGameStateInput;
import com.tokelon.toktales.desktop.input.IDesktopInputRegistration.ICharInputCallback;
import com.tokelon.toktales.desktop.input.IDesktopInputRegistration.ICursorEnterCallback;
import com.tokelon.toktales.desktop.input.IDesktopInputRegistration.ICursorPosCallback;
import com.tokelon.toktales.desktop.input.IDesktopInputRegistration.IKeyInputCallback;
import com.tokelon.toktales.desktop.input.IDesktopInputRegistration.IMouseButtonCallback;
import com.tokelon.toktales.desktop.input.IDesktopInputService;
import com.tokelon.toktales.desktop.input.events.ICharInputEvent;
import com.tokelon.toktales.desktop.input.events.ICursorEnterInputEvent;
import com.tokelon.toktales.desktop.input.events.ICursorPosInputEvent;
import com.tokelon.toktales.desktop.input.events.IKeyInputEvent;
import com.tokelon.toktales.desktop.input.events.IMouseButtonInputEvent;

public class DesktopGameStateManager extends GameStateControl { //TODO: Rename to DesktopGameStateControl

	
	private final DesktopGamestateInputForwarder inputForwarder;
	
	@Inject
	public DesktopGameStateManager(ILogger logger, IDesktopInputService inputService) {
		super(logger);
	
		this.inputForwarder = new DesktopGamestateInputForwarder();
		inputService.getMainInputDispatch().getInputConsumer().registerInputCallback(inputForwarder);
		inputService.getMainInputDispatch().getInputConsumer().registerInputCallback(inputForwarder, IInputCallback.class);
	}
	
	
	
	protected class DesktopGamestateInputForwarder implements
		IInputCallback, IMouseButtonCallback, ICursorEnterCallback, ICursorPosCallback, IKeyInputCallback, ICharInputCallback {

		@Override
		public boolean handle(IInputEvent event) {
			IGameStateInput currentStateInput = getActiveState().getStateInput();
			
			if(currentStateInput instanceof IDesktopGameStateInput) {
				IDesktopGameStateInput desktopStateInput = (IDesktopGameStateInput) currentStateInput;
				return desktopStateInput.handle(event);
			}
			else {
				currentStateInput.handle(event);
			}
			
			return false;
		}

		@Override
		public boolean handleMouseButtonInput(IMouseButtonInputEvent event) {
			IGameStateInput currentStateInput = getActiveState().getStateInput();
			
			if(currentStateInput instanceof IDesktopGameStateInput) {
				IDesktopGameStateInput desktopStateInput = (IDesktopGameStateInput) currentStateInput;
				return desktopStateInput.handleMouseButtonInput(event);
			}
			else {
				currentStateInput.handle(event);
			}
			
			return false;
		}

		@Override
		public boolean handleCursorEnterInput(ICursorEnterInputEvent event) {
			IGameStateInput currentStateInput = getActiveState().getStateInput();
			
			if(currentStateInput instanceof IDesktopGameStateInput) {
				IDesktopGameStateInput desktopStateInput = (IDesktopGameStateInput) currentStateInput;
				return desktopStateInput.handleCursorEnterInput(event);
			}
			else {
				currentStateInput.handle(event);
			}
			
			return false;
		}

		@Override
		public boolean handleCursorPosInput(ICursorPosInputEvent event) {
			IGameStateInput currentStateInput = getActiveState().getStateInput();
			
			if(currentStateInput instanceof IDesktopGameStateInput) {
				IDesktopGameStateInput desktopStateInput = (IDesktopGameStateInput) currentStateInput;
				return desktopStateInput.handleCursorPosInput(event);
			}
			else {
				currentStateInput.handle(event);
			}
			
			return false;
		}

		@Override
		public boolean handleKeyInput(IKeyInputEvent event) {
			IGameStateInput currentStateInput = getActiveState().getStateInput();
			
			if(currentStateInput instanceof IDesktopGameStateInput) {
				IDesktopGameStateInput desktopStateInput = (IDesktopGameStateInput) currentStateInput;
				return desktopStateInput.handleKeyInput(event);
			}
			else {
				currentStateInput.handle(event);
			}
			
			return false;
		}

		@Override
		public boolean handleCharInput(ICharInputEvent event) {
			IGameStateInput currentStateInput = getActiveState().getStateInput();
			
			if(currentStateInput instanceof IDesktopGameStateInput) {
				IDesktopGameStateInput desktopStateInput = (IDesktopGameStateInput) currentStateInput;
				return desktopStateInput.handleCharInput(event);
			}
			else {
				currentStateInput.handle(event);
			}
			
			return false;
		}
	}
	
}
