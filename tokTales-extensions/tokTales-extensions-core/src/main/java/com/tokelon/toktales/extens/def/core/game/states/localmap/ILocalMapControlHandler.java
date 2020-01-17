package com.tokelon.toktales.extens.def.core.game.states.localmap;

import com.tokelon.toktales.core.game.states.IControlHandler;

public interface ILocalMapControlHandler extends IControlHandler {

	
	public static final String MOVE_LEFT = "local_map_move_left";
	public static final String MOVE_UP = "local_map_move_up";
	public static final String MOVE_RIGHT = "local_map_move_right";
	public static final String MOVE_DOWN = "local_map_move_down";
	
	public static final String CAMERA_LEFT = "local_map_camera_left";
	public static final String CAMERA_UP = "local_map_camera_up";
	public static final String CAMERA_RIGHT = "local_map_camera_right";
	public static final String CAMERA_DOWN = "local_map_camera_down";
	
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

	
	public default boolean handleJump() { return false; }
	public default boolean handleInteract() { return false; }
	
	public default boolean handleMove(int direction) { return false; }
	public default boolean handleStopMove() { return false; }
	
	public default boolean handleCameraMove(int direction) { return false; }
	public default boolean handleCameraStopMove() { return false; }
	
	public default boolean handleDebugOpen() { return false; }
	
	
	public interface ILocalMapControlHandlerFactory {
		
		public ILocalMapControlHandler create(ILocalMapGamestate gamestate);
	}

	
	public class EmptyLocalMapControlHandler extends EmptyControlHandler implements ILocalMapControlHandler {

	}
	
}
