package com.tokelon.toktales.android.input.events;

import com.tokelon.toktales.core.engine.input.BaseInputEvent;
import com.tokelon.toktales.core.engine.input.IInputEvent;

public interface IScreenPressInputEvent extends IInputEvent {

	
	public double getXPos();
	public double getYPos();
	
	
	public static class ScreenPressInputEvent extends BaseInputEvent implements IScreenPressInputEvent {
		private double xpos;
		private double ypos;

		public ScreenPressInputEvent set(double xpos, double ypos) {
			this.xpos = xpos;
			this.ypos = ypos;

			resetHandled();
			return this;
		}
		
		
		@Override
		public double getXPos() {
			return xpos;
		}

		@Override
		public double getYPos() {
			return ypos;
		}
	}
	
}
