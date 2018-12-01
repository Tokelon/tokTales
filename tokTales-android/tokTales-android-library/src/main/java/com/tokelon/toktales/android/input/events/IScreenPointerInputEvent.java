package com.tokelon.toktales.android.input.events;

import com.tokelon.toktales.core.engine.input.BaseInputEvent;
import com.tokelon.toktales.core.engine.input.IInputEvent;

public interface IScreenPointerInputEvent extends IInputEvent {

	
	public int getPointerId();
	public int getAction();
	public double getXPos();
	public double getYPos();
	
	
	public static class ScreenPointerInputEvent extends BaseInputEvent implements IScreenPointerInputEvent {
		private int pointerId;
		private int action;
		private double xpos;
		private double ypos;

		public ScreenPointerInputEvent set(int pointerId, int action, double xpos, double ypos) {
			this.pointerId = pointerId;
			this.action = action;
			this.xpos = xpos;
			this.ypos = ypos;
			
			resetHandled();
			return this;
		}
		
		
		@Override
		public int getPointerId() {
			return pointerId;
		}

		@Override
		public int getAction() {
			return action;
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
