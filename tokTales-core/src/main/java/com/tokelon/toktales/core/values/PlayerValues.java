package com.tokelon.toktales.core.values;

public final class PlayerValues {

	private PlayerValues() {}
	

	// TODO: Remove the .png in the file names and implement so that contains() is used instead of equals()
	
	public static final String PLAYER_SPRITE_STATIC_LEFT = "Player_Static_left.png";
	public static final String PLAYER_SPRITE_STATIC_UP = "Player_Static_up.png";
	public static final String PLAYER_SPRITE_STATIC_RIGHT = "Player_Static_right.png";
	public static final String PLAYER_SPRITE_STATIC_DOWN = "Player_Static_down.png";
	
	
	public static final String PLAYER_SPRITE_ANIMATION_LEFT_01 = "Player_Animation_left-01.png";
	public static final String PLAYER_SPRITE_ANIMATION_LEFT_02 = "Player_Animation_left-02.png";
	public static final String PLAYER_SPRITE_ANIMATION_LEFT_03 = "Player_Animation_left-03.png";
	public static final String PLAYER_SPRITE_ANIMATION_LEFT_04 = "Player_Animation_left-04.png";
	
	public static final String PLAYER_SPRITE_ANIMATION_UP_01 = "Player_Animation_up-01.png";
	public static final String PLAYER_SPRITE_ANIMATION_UP_02 = "Player_Animation_up-02.png";
	public static final String PLAYER_SPRITE_ANIMATION_UP_03 = "Player_Animation_up-03.png";
	public static final String PLAYER_SPRITE_ANIMATION_UP_04 = "Player_Animation_up-04.png";
	
	public static final String PLAYER_SPRITE_ANIMATION_RIGHT_01 = "Player_Animation_right-01.png";
	public static final String PLAYER_SPRITE_ANIMATION_RIGHT_02 = "Player_Animation_right-02.png";
	public static final String PLAYER_SPRITE_ANIMATION_RIGHT_03 = "Player_Animation_right-03.png";
	public static final String PLAYER_SPRITE_ANIMATION_RIGHT_04 = "Player_Animation_right-04.png";
	
	public static final String PLAYER_SPRITE_ANIMATION_DOWN_01 = "Player_Animation_down-01.png";
	public static final String PLAYER_SPRITE_ANIMATION_DOWN_02 = "Player_Animation_down-02.png";
	public static final String PLAYER_SPRITE_ANIMATION_DOWN_03 = "Player_Animation_down-03.png";
	public static final String PLAYER_SPRITE_ANIMATION_DOWN_04 = "Player_Animation_down-04.png";
	

	
	
	/* Pokemon RED is ~260 ms/block
	 * 
	 */
	public static final int PLAYER_MOVE_DURATION = 400; //500;	//800;
	public static final int PLAYER_MOVE_BLOCK_DISTANCE = 1;
	//public static final double PLAYER_MOVE_SPEED_BLOCKS_PER_SECOND = (1000/(double)PLAYER_MOVE_DURATION);
	
	public static final float PLAYER_WALK_SPEED_UNITS = 80.0f;
	
}
