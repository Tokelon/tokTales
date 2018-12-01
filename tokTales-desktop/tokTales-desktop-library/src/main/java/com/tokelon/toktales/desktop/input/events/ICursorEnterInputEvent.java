package com.tokelon.toktales.desktop.input.events;

import com.tokelon.toktales.core.engine.input.BaseInputEvent;
import com.tokelon.toktales.core.engine.input.IInputEvent;

public interface ICursorEnterInputEvent extends IInputEvent {

	
	public long getWindow();
	public boolean getEntered();
	
	
	public static class CursorEnterInputEvent extends BaseInputEvent implements ICursorEnterInputEvent {
		private long window;
		private boolean entered;

		public CursorEnterInputEvent set(long window, boolean entered) {
			this.window = window;
			this.entered = entered;

			resetHandled();
			return this;
		}
		
		
		@Override
		public long getWindow() {
			return window;
		}

		@Override
		public boolean getEntered() {
			return entered;
		}
	}
	
}
