package com.tokelon.toktales.desktop.input.events;

import com.tokelon.toktales.core.engine.input.IInputEvent;

public interface IMouseButtonInputEvent extends IInputEvent {
	
	
	public long getWindow();
	public int getButton();
	public int getAction();
	public int getMods();
	
	
	public static class MouseButtonInputEvent implements IMouseButtonInputEvent {
		private long window;
		private int button;
		private int action;
		private int mods;
		
		public MouseButtonInputEvent set(long window, int button, int action, int mods) {
			this.window = window;
			this.button = button;
			this.action = action;
			this.mods = mods;
			
			return this;
		}
		
		
		@Override
		public long getWindow() {
			return window;
		}

		@Override
		public int getButton() {
			return button;
		}

		@Override
		public int getAction() {
			return action;
		}

		@Override
		public int getMods() {
			return mods;
		}
	}
	
}
