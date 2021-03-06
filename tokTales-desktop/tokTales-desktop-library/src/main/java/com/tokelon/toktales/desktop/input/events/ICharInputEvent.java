package com.tokelon.toktales.desktop.input.events;

import com.tokelon.toktales.core.engine.input.BaseInputEvent;
import com.tokelon.toktales.core.engine.input.IInputEvent;

public interface ICharInputEvent extends IInputEvent {

	
	public long getWindow();
	public int getCodepoint();
	
	
	public static class CharInputEvent extends BaseInputEvent implements ICharInputEvent {
		private long window;
		private int codepoint;

		public CharInputEvent set(long window, int codepoint) {
			this.window = window;
			this.codepoint = codepoint;

			resetHandled();
			return this;
		}
		
		
		@Override
		public long getWindow() {
			return window;
		}

		@Override
		public int getCodepoint() {
			return codepoint;
		}
	}
	
}
