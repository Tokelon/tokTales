package com.tokelon.toktales.desktop.input.events;

import com.tokelon.toktales.core.engine.input.IInputEvent;

public interface IKeyInputEvent extends IInputEvent {

	
	public long getWindow();
	public int getKey();
	public int getScancode();
	public int getAction();
	public int getMods();
	
	
	public static class KeyInputEvent implements IKeyInputEvent {
		private long window;
		private int key;
		private int scancode;
		private int action;
		private int mods;

		public KeyInputEvent set(long window, int key, int scancode, int action, int mods) {
			this.window = window;
			this.key = key;
			this.scancode = scancode;
			this.action = action;
			this.mods = mods;
			
			return this;
		}

		
		@Override
		public long getWindow() {
			return window;
		}

		@Override
		public int getKey() {
			return key;
		}

		@Override
		public int getScancode() {
			return scancode;
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
