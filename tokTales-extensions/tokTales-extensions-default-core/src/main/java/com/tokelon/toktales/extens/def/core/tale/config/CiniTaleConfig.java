package com.tokelon.toktales.extens.def.core.tale.config;

import com.tokelon.toktales.core.config.CiniFileConfig;
import com.tokelon.toktales.tools.config.ConfigDataException;
import com.tokelon.toktales.tools.config.ICiniConfig.IMutableCiniConfig;

public class CiniTaleConfig extends CiniFileConfig implements ITaleConfig {
	
	
	public static final String VALUE_CONFIG_TYPE_TALE = "Tale";
	public static final String VALUE_CONFIG_VERSION__01 = "0.1";
	
	public static final String DEFAULT_VALUE_RESOURCES_MAP_DIRECTORY = "maps/";
	public static final String DEFAULT_VALUE_RESOURCES_ANIMATION_DIRECTORY = "assets/animations/";
	public static final String DEFAULT_VALUE_RESOURCES_G_TILESET_DIRECTORY = "assets/graphics/tilesets";
	public static final String DEFAULT_VALUE_RESOURCES_G_SPRITE_DIRECTORY = "assets/graphics/sprites";
	public static final String DEFAULT_VALUE_RESOURCES_G_PLAYER_DIRECTORY = "assets/graphics/player";

	public static final float DEFAULT_VALUE_PLAYER_WALK_SPEED_UNITS = 80.0f;
	//public static final int DEFAULT_VALUE_PLAYER_MOVE_ONE_BLOCK_DURATION = 400;	// Set to 0 ?
	//public static final int DEFAULT_VALUE_PLAYER_MOVE_MIN_BLOCK_DISTANCE = 1;
	
	
	
	public CiniTaleConfig(IMutableCiniConfig mutableCiniConfig) throws ConfigDataException {
		super(mutableCiniConfig);
		
		checkConfigHeader(mutableCiniConfig, VALUE_CONFIG_TYPE_TALE, VALUE_CONFIG_VERSION__01);
	}




	@Override
	public String getConfigResourcesMapDirectory() {
		return getString(CATEGORY_RESOURCES, KEY_RESOURCES_MAP_DIRECTORY, DEFAULT_VALUE_RESOURCES_MAP_DIRECTORY);
	}

	@Override
	public String getConfigResourcesAnimationDirectory() {
		return getString(CATEGORY_RESOURCES, KEY_RESOURCES_ANIMATION_DIRECTORY, DEFAULT_VALUE_RESOURCES_ANIMATION_DIRECTORY);
	}
	
	@Override
	public String getConfigResourcesGTilesetDirectory() {
		return getString(CATEGORY_RESOURCES, KEY_RESOURCES_G_TILESET_DIRECTORY, DEFAULT_VALUE_RESOURCES_G_TILESET_DIRECTORY);
	}

	@Override
	public String getConfigResourcesGSpriteDirectory() {
		return getString(CATEGORY_RESOURCES, KEY_RESOURCES_G_SPRITE_DIRECTORY, DEFAULT_VALUE_RESOURCES_G_SPRITE_DIRECTORY);
	}


	@Override
	public String getConfigResourcesGPlayerDirectory() {
		return getString(CATEGORY_RESOURCES, KEY_RESOURCES_G_PLAYER_DIRECTORY, DEFAULT_VALUE_RESOURCES_G_PLAYER_DIRECTORY);
	}

	
	@Override
	public String getConfigTaleInitialMapfileName() {
		return getString(CATEGORY_TALE, KEY_TALE_INITIAL_MAPFILE_NAME, "");
	}

	@Override
	public String getConfigTaleTaleTitle() {
		return getString(CATEGORY_TALE, KEY_TALE_TALE_TITLE, "");
	}
	
	@Override
	public String getConfigTaleInitialSceneCodename() {
		return getString(CATEGORY_TALE, KEY_TALE_INITIAL_SCENE_CODENAME, "");
	}






	@Override
	public float getConfigPlayerWalkSpeedUnits() {
		return getFloat(CATEGORY_PLAYER, KEY_PLAYER_WALK_SPEED_UNITS, DEFAULT_VALUE_PLAYER_WALK_SPEED_UNITS);
	}

	@Override
	public String getConfigPlayerGraphicIdleLeft() {
		return getString(CATEGORY_PLAYER, KEY_PLAYER_GRAPHIC_IDLE_LEFT, "");
	}

	@Override
	public String getConfigPlayerGraphicIdleUp() {
		return getString(CATEGORY_PLAYER, KEY_PLAYER_GRAPHIC_IDLE_UP, "");
	}
	
	@Override
	public String getConfigPlayerGraphicIdleRight() {
		return getString(CATEGORY_PLAYER, KEY_PLAYER_GRAPHIC_IDLE_RIGHT, "");
	}
	
	@Override
	public String getConfigPlayerGraphicIdleDown() {
		return getString(CATEGORY_PLAYER, KEY_PLAYER_GRAPHIC_IDLE_DOWN, "");
	}



	@Override
	public String getConfigPlayerAnimationWalkLeft() {
		return getString(CATEGORY_PLAYER, KEY_PLAYER_ANIMATION_WALK_LEFT, "");
	}

	@Override
	public String getConfigPlayerAnimationWalkUp() {
		return getString(CATEGORY_PLAYER, KEY_PLAYER_ANIMATION_WALK_UP, "");
	}
	
	@Override
	public String getConfigPlayerAnimationWalkRight() {
		return getString(CATEGORY_PLAYER, KEY_PLAYER_ANIMATION_WALK_RIGHT, "");
	}
	
	@Override
	public String getConfigPlayerAnimationWalkDown() {
		return getString(CATEGORY_PLAYER, KEY_PLAYER_ANIMATION_WALK_DOWN, "");
	}




}
