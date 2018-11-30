package com.tokelon.toktales.desktop.input.events;

import com.tokelon.toktales.core.engine.input.IInputEvent;

public interface ICursorPosInputEvent extends IInputEvent {

	
	public long getWindow();
	public double getXPos();
	public double getYPos();
	
	
	public static class CursorPosInputEvent implements ICursorPosInputEvent {
		private long window;
		private double xpos;
		private double ypos;

		public CursorPosInputEvent set(long window, double xpos, double ypos) {
			this.window = window;
			this.xpos = xpos;
			this.ypos = ypos;
			
			return this;
		}
		

		@Override
		public long getWindow() {
			return window;
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
