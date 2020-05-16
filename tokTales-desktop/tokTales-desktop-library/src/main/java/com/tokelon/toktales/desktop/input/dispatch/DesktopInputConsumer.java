package com.tokelon.toktales.desktop.input.dispatch;

import java.util.Set;

import com.tokelon.toktales.core.engine.input.IInputCallback;
import com.tokelon.toktales.core.engine.input.IInputEvent;
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

public class DesktopInputConsumer extends DesktopInputRegistration implements IDesktopInputConsumer,
		IMouseButtonCallback,
		ICursorEnterCallback,
		ICursorPosCallback,
		IKeyInputCallback,
		ICharInputCallback {
	// TODO: Maybe convert methods to lambdas


	@Override
	public IMouseButtonCallback getMasterMouseButtonCallback() {
		return this;
	}

	@Override
	public ICursorEnterCallback getMasterCursorEnterCallback() {
		return this;
	}

	@Override
	public ICursorPosCallback getMasterCursorPosCallback() {
		return this;
	}

	@Override
	public IKeyInputCallback getMasterKeyInputCallback() {
		return this;
	}

	@Override
	public ICharInputCallback getMasterCharInputCallback() {
		return this;
	}


	@Override
	public boolean handle(IInputEvent event) {
		boolean handledHere = false;

		Set<IInputCallback> generalCallbackSet = getGeneralInputCallbackSet();
		synchronized (generalCallbackSet) {
			for(IInputCallback callback: generalCallbackSet) {
				boolean callbackHandled = callback.handle(event);
				handledHere = callbackHandled || handledHere;
				event.markHandledIf(callbackHandled);
			}
		}

		return handledHere;
	}

	@Override
	public boolean handleCursorEnterInput(ICursorEnterInputEvent event) {
		boolean handledHere = false;

		Set<ICursorEnterCallback> cursorEnterCallbackSet = getCursorEnterCallbackSet();
		synchronized (cursorEnterCallbackSet) {
			for(ICursorEnterCallback callback: cursorEnterCallbackSet) {
				boolean callbackHandled = callback.handleCursorEnterInput(event);
				handledHere = callbackHandled || handledHere;
				event.markHandledIf(callbackHandled);
			}
		}

		return handledHere;
	}

	@Override
	public boolean handleCharInput(ICharInputEvent event) {
		boolean handledHere = false;

		Set<ICharInputCallback> charInputCallbackSet = getCharInputCallbackSet();
		synchronized (charInputCallbackSet) {
			for(ICharInputCallback callback: charInputCallbackSet) {
				boolean callbackHandled = callback.handleCharInput(event);
				handledHere = callbackHandled || handledHere;
				event.markHandledIf(callbackHandled);
			}
		}

		return handledHere;
	}

	@Override
	public boolean handleKeyInput(IKeyInputEvent event) {
		boolean handledHere = false;

		Set<IKeyInputCallback> keyInputCallbackSet = getKeyInputCallbackSet();
		synchronized (keyInputCallbackSet) {
			for(IKeyInputCallback callback: keyInputCallbackSet) {
				boolean callbackHandled = callback.handleKeyInput(event);
				handledHere = callbackHandled || handledHere;
				event.markHandledIf(callbackHandled);
			}
		}

		return handledHere;
	}

	@Override
	public boolean handleCursorPosInput(ICursorPosInputEvent event) {
		boolean handledHere = false;

		Set<ICursorPosCallback> cursorPosCallbackSet = getCursorPosCallbackSet();
		synchronized (cursorPosCallbackSet) {
			for(ICursorPosCallback callback: cursorPosCallbackSet) {
				boolean callbackHandled = callback.handleCursorPosInput(event);
				handledHere = callbackHandled || handledHere;
				event.markHandledIf(callbackHandled);
			}
		}

		return handledHere;
	}

	@Override
	public boolean handleMouseButtonInput(IMouseButtonInputEvent event) {
		boolean handledHere = false;

		Set<IMouseButtonCallback> mouseButtonCallbackSet = getMouseButtonCallbackSet();
		synchronized (mouseButtonCallbackSet) {
			for(IMouseButtonCallback callback: mouseButtonCallbackSet) {
				boolean callbackHandled = callback.handleMouseButtonInput(event);
				handledHere = callbackHandled || handledHere;
				event.markHandledIf(callbackHandled);
			}
		}

		return handledHere;
	}



	public static class DesktopInputConsumerFactory implements IDesktopInputConsumerFactory {

		@Override
		public IDesktopInputConsumer create(IDesktopInputDispatch dispatch) {
			return new DesktopInputConsumer();
		}
	}

}
