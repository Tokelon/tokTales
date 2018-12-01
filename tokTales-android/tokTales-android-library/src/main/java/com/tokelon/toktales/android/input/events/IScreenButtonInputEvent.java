package com.tokelon.toktales.android.input.events;

import com.tokelon.toktales.core.engine.input.BaseInputEvent;
import com.tokelon.toktales.core.engine.input.IInputEvent;

public interface IScreenButtonInputEvent extends IInputEvent {

	
	public int getButton(); // TODO: Replace with String?
	public int getAction();

	
	public static class ScreenButtonInputEvent extends BaseInputEvent implements IScreenButtonInputEvent {
		private int button;
		private int action;
		
		public ScreenButtonInputEvent set(int button, int action) {
			this.button = button;
			this.action = action;
			
			resetHandled();
			return this;
		}
		
		
		@Override
		public int getButton() {
			return button;
		}

		@Override
		public int getAction() {
			return action;
		}
	}
	
}
