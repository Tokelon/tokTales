package com.tokelon.toktales.core.config;

public interface IMainConfig extends IFileConfig {
	
	
	/* Config values */
	
	public static final int CAMERA_CREATION_TYPE_FIXED_UNITS = 1;
	public static final int CAMERA_CREATION_TYPE_ADJUST_TO_VIEWPORT = 2;
	
	
	
	/* Categories and Keys */

	
	public static final String CATEGORY_DISPLAY = "Display";
	
	public static final String KEY_DISPLAY_CAMERA_CREATION_TYPE = "CameraCreationType";
	public static final String KEY_DISPLAY_CAMERA_SIZE_UNITS_X = "CameraSizeUnitsX";
	public static final String KEY_DISPLAY_CAMERA_SIZE_UNITS_Y = "CameraSizeUnitsY";
	//public static final String KEY_DISPLAY_CAMERA_SIZE_BLOCKS_X = "CameraSizeBlocksX";
	//public static final String KEY_DISPLAY_CAMERA_SIZE_BLOCKS_Y = "CameraSizeBlocksY";
	

	
	/* Known file Values */
	
	public static final String VALUE_DISPLAY_CAMERA_CREATION_TYPE_FIXED_UNITS = "Fixed_Units";
	public static final String VALUE_DISPLAY_CAMERA_CREATION_TYPE_ADJUST_TO_VIEWPORT = "Adjust_To_Viewport";
	
	
	
	
	public int getConfigDisplayCameraCreationType();
	public int parseCameraCreationType(String stringValue);
	public String unparseCameraCreationType(int value);
	
	
	public float getConfigCameraSizeUnitsX();
	public float getConfigCameraSizeUnitsY();
	
	
}
