package com.tokelon.toktales.extens.def.core.game.states.localmap;

import com.tokelon.toktales.extens.def.core.game.states.consover.IConsoleOverlayControlHandler;

public interface ILocalMapControlHandler extends IConsoleOverlayControlHandler {

	public static final String MOVE_LEFT = "local_map_move_left";
	public static final String MOVE_UP = "local_map_move_up";
	public static final String MOVE_RIGHT = "local_map_move_right";
	public static final String MOVE_DOWN = "local_map_move_down";
	
	public static final String JUMP = "local_map_jump";
	public static final String INTERACT = "local_map_interact";
	public static final String DEBUG_OPEN = "local_map_debug_open";
	
	
	/*
	public static final int BLOCK = 2;
	public static final int ENTER = 3;
	public static final int INTERACT_MENU = 5;
	
	
	public void jump();
	
	public void block();
	
	public void enter();
	
	//etc.
	 */

	
	public boolean handleJump();
	public boolean handleInteract();
	public boolean handleMove(int direction);
	public boolean handleStopMove();
	public boolean handleDebugOpen();
	
	
	public class EmptyLocalMapControlHandler extends EmptyConsoleOverlayControlHandler implements ILocalMapControlHandler {
		@Override
		public boolean handleJump() { return false; }

		@Override
		public boolean handleInteract() { return false; }

		@Override
		public boolean handleMove(int direction) { return false; }

		@Override
		public boolean handleStopMove() { return false; }

		@Override
		public boolean handleDebugOpen() { return false; }
	}
	
}
