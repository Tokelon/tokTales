package com.tokelon.toktales.desktop.input.dispatch;

import java.util.Set;

import com.tokelon.toktales.core.engine.input.IInputCallback;
import com.tokelon.toktales.core.engine.input.IInputEvent;
import com.tokelon.toktales.desktop.input.DesktopInputRegistration;
import com.tokelon.toktales.desktop.input.events.ICharInputEvent;
import com.tokelon.toktales.desktop.input.events.ICursorEnterInputEvent;
import com.tokelon.toktales.desktop.input.events.ICursorPosInputEvent;
import com.tokelon.toktales.desktop.input.events.IKeyInputEvent;
import com.tokelon.toktales.desktop.input.events.IMouseButtonInputEvent;

public class DesktopInputConsumer extends DesktopInputRegistration implements IDesktopInputConsumer {
	// TODO: Set event handle = true if any handle() call returns true ?
	
	
	@Override
	public boolean handle(IInputEvent event) {
		boolean wasHandled = false;

		Set<IInputCallback> generalCallbackSet = getGeneralInputCallbackSet();
		synchronized (generalCallbackSet) {
			for(IInputCallback callback: generalCallbackSet) {
				wasHandled = callback.handle(event) || wasHandled;
			}
		}
		
		return wasHandled;
	}

	@Override
	public boolean handleCursorEnterInput(ICursorEnterInputEvent event) {
		boolean wasHandled = false;
		
		Set<ICursorEnterCallback> cursorEnterCallbackSet = getCursorEnterCallbackSet();
		synchronized (cursorEnterCallbackSet) {
			for(ICursorEnterCallback callback: cursorEnterCallbackSet) {
				wasHandled = callback.handleCursorEnterInput(event) || wasHandled;
			}
		}
		
		return wasHandled;
	}

	@Override
	public boolean handleCharInput(ICharInputEvent event) {
		boolean wasHandled = false;
		
		Set<ICharInputCallback> charInputCallbackSet = getCharInputCallbackSet();
		synchronized (charInputCallbackSet) {
			for(ICharInputCallback callback: charInputCallbackSet) {
				wasHandled = callback.handleCharInput(event) || wasHandled;
			}
		}
		
		return wasHandled;
	}

	@Override
	public boolean handleKeyInput(IKeyInputEvent event) {
		boolean wasHandled = false;
		
		Set<IKeyInputCallback> keyInputCallbackSet = getKeyInputCallbackSet();
		synchronized (keyInputCallbackSet) {
			for(IKeyInputCallback callback: keyInputCallbackSet) {
				wasHandled = callback.handleKeyInput(event) || wasHandled;
			}
		}
		
		return wasHandled;
	}

	@Override
	public boolean handleCursorPosInput(ICursorPosInputEvent event) {
		boolean wasHandled = false;
		
		Set<ICursorPosCallback> cursorPosCallbackSet = getCursorPosCallbackSet();
		synchronized (cursorPosCallbackSet) {
			for(ICursorPosCallback callback: cursorPosCallbackSet) {
				wasHandled = callback.handleCursorPosInput(event) || wasHandled;
			}
		}
		
		return wasHandled;
	}

	@Override
	public boolean handleMouseButtonInput(IMouseButtonInputEvent event) {
		boolean wasHandled = false;
		
		Set<IMouseButtonCallback> mouseButtonCallbackSet = getMouseButtonCallbackSet();
		synchronized (mouseButtonCallbackSet) {
			for(IMouseButtonCallback callback: mouseButtonCallbackSet) {
				wasHandled = callback.handleMouseButtonInput(event) || wasHandled;
			}
		}
		
		return wasHandled;
	}
	
	
	
	public static class DesktopInputConsumerFactory implements IDesktopInputConsumerFactory {

		@Override
		public IDesktopInputConsumer create(IDesktopInputDispatch dispatch) {
			return new DesktopInputConsumer();
		}
	}

}
