package com.tokelon.toktales.desktop.game.state;

import javax.inject.Inject;

import com.tokelon.toktales.core.engine.input.IInputCallback;
import com.tokelon.toktales.core.engine.input.IInputEvent;
import com.tokelon.toktales.core.engine.log.ILogging;
import com.tokelon.toktales.core.game.state.GameStateControl;
import com.tokelon.toktales.core.game.state.IGameStateInput;
import com.tokelon.toktales.desktop.input.IDesktopInputService;
import com.tokelon.toktales.desktop.input.dispatch.IDesktopInputRegistration.ICharInputCallback;
import com.tokelon.toktales.desktop.input.dispatch.IDesktopInputRegistration.ICursorEnterCallback;
import com.tokelon.toktales.desktop.input.dispatch.IDesktopInputRegistration.ICursorPosCallback;
import com.tokelon.toktales.desktop.input.dispatch.IDesktopInputRegistration.IKeyInputCallback;
import com.tokelon.toktales.desktop.input.dispatch.IDesktopInputRegistration.IMouseButtonCallback;
import com.tokelon.toktales.desktop.input.events.ICharInputEvent;
import com.tokelon.toktales.desktop.input.events.ICursorEnterInputEvent;
import com.tokelon.toktales.desktop.input.events.ICursorPosInputEvent;
import com.tokelon.toktales.desktop.input.events.IKeyInputEvent;
import com.tokelon.toktales.desktop.input.events.IMouseButtonInputEvent;

public class DesktopGameStateControl extends GameStateControl {


	private final DesktopGamestateInputForwarder inputForwarder;

	@Inject
	public DesktopGameStateControl(ILogging logging, IDesktopInputService inputService) {
		super(logging);

		this.inputForwarder = new DesktopGamestateInputForwarder();
		inputService.getMainInputDispatch().getInputConsumer().registerInputCallback(inputForwarder);
		inputService.getMainInputDispatch().getInputConsumer().registerInputCallback(inputForwarder, IInputCallback.class);
	}


	protected class DesktopGamestateInputForwarder implements IInputCallback,
			IMouseButtonCallback,
			ICursorEnterCallback,
			ICursorPosCallback,
			IKeyInputCallback,
			ICharInputCallback {

		@Override
		public boolean handle(IInputEvent event) {
			IGameStateInput currentStateInput = getActiveState().getStateInput();

			if(currentStateInput instanceof IDesktopGameStateInput) {
				IDesktopGameStateInput desktopStateInput = (IDesktopGameStateInput) currentStateInput;
				return desktopStateInput.getMasterInputCallback().handle(event);
			}
			else {
				currentStateInput.getMasterInputCallback().handle(event);
			}

			return false;
		}

		@Override
		public boolean handleMouseButtonInput(IMouseButtonInputEvent event) {
			IGameStateInput currentStateInput = getActiveState().getStateInput();

			if(currentStateInput instanceof IDesktopGameStateInput) {
				IDesktopGameStateInput desktopStateInput = (IDesktopGameStateInput) currentStateInput;
				return desktopStateInput.getMasterMouseButtonCallback().handleMouseButtonInput(event);
			}
			else {
				currentStateInput.getMasterInputCallback().handle(event);
			}

			return false;
		}

		@Override
		public boolean handleCursorEnterInput(ICursorEnterInputEvent event) {
			IGameStateInput currentStateInput = getActiveState().getStateInput();

			if(currentStateInput instanceof IDesktopGameStateInput) {
				IDesktopGameStateInput desktopStateInput = (IDesktopGameStateInput) currentStateInput;
				return desktopStateInput.getMasterCursorEnterCallback().handleCursorEnterInput(event);
			}
			else {
				currentStateInput.getMasterInputCallback().handle(event);
			}

			return false;
		}

		@Override
		public boolean handleCursorPosInput(ICursorPosInputEvent event) {
			IGameStateInput currentStateInput = getActiveState().getStateInput();

			if(currentStateInput instanceof IDesktopGameStateInput) {
				IDesktopGameStateInput desktopStateInput = (IDesktopGameStateInput) currentStateInput;
				return desktopStateInput.getMasterCursorPosCallback().handleCursorPosInput(event);
			}
			else {
				currentStateInput.getMasterInputCallback().handle(event);
			}

			return false;
		}

		@Override
		public boolean handleKeyInput(IKeyInputEvent event) {
			IGameStateInput currentStateInput = getActiveState().getStateInput();

			if(currentStateInput instanceof IDesktopGameStateInput) {
				IDesktopGameStateInput desktopStateInput = (IDesktopGameStateInput) currentStateInput;
				return desktopStateInput.getMasterKeyInputCallback().handleKeyInput(event);
			}
			else {
				currentStateInput.getMasterInputCallback().handle(event);
			}

			return false;
		}

		@Override
		public boolean handleCharInput(ICharInputEvent event) {
			IGameStateInput currentStateInput = getActiveState().getStateInput();

			if(currentStateInput instanceof IDesktopGameStateInput) {
				IDesktopGameStateInput desktopStateInput = (IDesktopGameStateInput) currentStateInput;
				return desktopStateInput.getMasterCharInputCallback().handleCharInput(event);
			}
			else {
				currentStateInput.getMasterInputCallback().handle(event);
			}

			return false;
		}
	}

}
