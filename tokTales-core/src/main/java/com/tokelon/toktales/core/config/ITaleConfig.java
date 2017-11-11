package com.tokelon.toktales.core.config;

public interface ITaleConfig extends IFileConfig {

	
	/* Categories and Keys */

	public static final String CATEGORY_TALE = "Tale";
	public static final String CATEGORY_RESOURCES = "Resources";
	public static final String CATEGORY_PLAYER = "Player";
	
	public static final String KEY_TALE_INITIAL_MAPFILE_NAME = "InitialMapfileName";
	public static final String KEY_TALE_TALE_TITLE = "TaleTitle";
	public static final String KEY_TALE_INITIAL_SCENE_CODENAME = "InitialSceneCodename";

	public static final String KEY_RESOURCES_MAP_DIRECTORY = "MapDirectory";
	public static final String KEY_RESOURCES_ANIMATION_DIRECTORY = "AnimationDirectory";
	public static final String KEY_RESOURCES_G_TILESET_DIRECTORY = "GTilesetDirectory";
	public static final String KEY_RESOURCES_G_SPRITE_DIRECTORY = "GSpriteDirectory";
	public static final String KEY_RESOURCES_G_PLAYER_DIRECTORY = "GPlayerDirectory";
	
	
	public static final String KEY_PLAYER_WALK_SPEED_UNITS = "WalkSpeedUnits";
	//public static final String KEY_PLAYER_MOVE_ONE_BLOCK_DURATION = "MoveOneBlockDuration";
	//public static final String KEY_PLAYER_MOVE_MIN_BLOCK_DISTANCE = "MoveMinBlockDistance";
	
	public static final String KEY_PLAYER_GRAPHIC_IDLE_LEFT = "GraphicIdleLeft";
	public static final String KEY_PLAYER_GRAPHIC_IDLE_UP = "GraphicIdleUp";
	public static final String KEY_PLAYER_GRAPHIC_IDLE_RIGHT = "GraphicIdleRight";
	public static final String KEY_PLAYER_GRAPHIC_IDLE_DOWN = "GraphicIdleDown";
	
	public static final String KEY_PLAYER_ANIMATION_WALK_LEFT = "AnimationWalkLeft";
	public static final String KEY_PLAYER_ANIMATION_WALK_UP = "AnimationWalkUp";
	public static final String KEY_PLAYER_ANIMATION_WALK_RIGHT = "AnimationWalkRight";
	public static final String KEY_PLAYER_ANIMATION_WALK_DOWN = "AnimationWalkDown";
	
	
	public String getConfigResourcesMapDirectory();
	public String getConfigResourcesAnimationDirectory();
	public String getConfigResourcesGTilesetDirectory();
	public String getConfigResourcesGSpriteDirectory();
	public String getConfigResourcesGPlayerDirectory();
	
	
	public String getConfigTaleInitialMapfileName();
	public String getConfigTaleTaleTitle();
	public String getConfigTaleInitialSceneCodename();

	
	public float getConfigPlayerWalkSpeedUnits();
	//public int getConfigPlayerMoveOneBlockDuration();
	//public int getConfigPlayerMoveMinBlockDistance();
	
	public String getConfigPlayerGraphicIdleLeft();
	public String getConfigPlayerGraphicIdleUp();
	public String getConfigPlayerGraphicIdleRight();
	public String getConfigPlayerGraphicIdleDown();
	
	public String getConfigPlayerAnimationWalkLeft();
	public String getConfigPlayerAnimationWalkUp();
	public String getConfigPlayerAnimationWalkRight();
	public String getConfigPlayerAnimationWalkDown();
	
}
