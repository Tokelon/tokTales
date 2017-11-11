package com.tokelon.toktales.extens.def.core.game.states.consover;

import com.tokelon.toktales.core.game.states.IControlHandler;

public interface IConsoleOverlayControlHandler extends IControlHandler {

	public static final String CONSOLE_ENTER = "console_overlay_enter";
	public static final String CONSOLE_DELETE = "console_overlay_delete";
	public static final String CONSOLE_CLEAR = "console_overlay_clear";
	public static final String CONSOLE_TOGGLE = "console_overlay_toggle";
	
	public static final String CONSOLE_INPUT = "console_overlay_input";
	
	
	//public boolean shouldHandleAction(String action);
	
	public boolean isConsoleOpen();
	
	public boolean handleConsoleEnter();
	public boolean handleConsoleDelete();
	public boolean handleConsoleClear();
	public boolean handleConsoleToggle();
	
	public boolean handleConsoleInput(int codepoint);
	
	
	public class EmptyConsoleOverlayControlHandler implements IConsoleOverlayControlHandler {
		@Override
		public boolean isConsoleOpen() { return false; }

		@Override
		public boolean handleConsoleEnter() { return false; }

		@Override
		public boolean handleConsoleDelete() { return false; }

		@Override
		public boolean handleConsoleClear() { return false; }

		@Override
		public boolean handleConsoleToggle() { return false;}

		@Override
		public boolean handleConsoleInput(int codepoint) { return false; }

		@Override
		public boolean handleAction(String target, String action, Object... params) { return false;	}
	}
	
}
