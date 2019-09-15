package com.tokelon.toktales.core.config;

import com.tokelon.toktales.tools.config.ConfigDataException;
import com.tokelon.toktales.tools.config.ICiniConfig.IMutableCiniConfig;
import com.tokelon.toktales.tools.config.MutableCiniConfig;

public class CiniMainConfig extends CiniFileConfig implements IMainConfig {


	public static final String VALUE_CONFIG_TYPE_MAIN = "Main";
	public static final String VALUE_CONFIG_VERSION__02 = "0.2";
	
	public static final String DEFAULT_VALUE_PROP_DISPLAY_CAMERA_CREATION_TYPE = VALUE_DISPLAY_CAMERA_CREATION_TYPE_FIXED_UNITS;
	public static final int DEFAULT_VALUE_PROP_DISPLAY_CAMERA_SIZE_UNITS_X = 256;
	public static final int DEFAULT_VALUE_PROP_DISPLAY_CAMERA_SIZE_UNITS_Y = 256;
	
	
	
	public CiniMainConfig() {
		super(defaultCiniConfig());
	}

	public CiniMainConfig(IMutableCiniConfig mutableCiniConfig) throws ConfigDataException {
		super(mutableCiniConfig);
		
		checkConfigHeader(mutableCiniConfig, VALUE_CONFIG_TYPE_MAIN, VALUE_CONFIG_VERSION__02);
	}
	
	
	
	

	@Override
	public int getConfigDisplayCameraCreationType() {
		String valueString = getString(CATEGORY_DISPLAY, KEY_DISPLAY_CAMERA_CREATION_TYPE, DEFAULT_VALUE_PROP_DISPLAY_CAMERA_CREATION_TYPE);
		
		int res = parseCameraCreationType(valueString);
		if(res < 0) {
			getLogger().warn("Config value {} is not a valid value. Fallback to default value: {}", valueString, DEFAULT_VALUE_PROP_DISPLAY_CAMERA_CREATION_TYPE);
			res = parseCameraCreationType(DEFAULT_VALUE_PROP_DISPLAY_CAMERA_CREATION_TYPE);
		}
		return res;
	}

	@Override
	public float getConfigCameraSizeUnitsX() {
		float res = getFloat(CATEGORY_DISPLAY, KEY_DISPLAY_CAMERA_SIZE_UNITS_X, DEFAULT_VALUE_PROP_DISPLAY_CAMERA_SIZE_UNITS_X);
		return res;
	}
	
	@Override
	public float getConfigCameraSizeUnitsY() {
		float res = getFloat(CATEGORY_DISPLAY, KEY_DISPLAY_CAMERA_SIZE_UNITS_Y, DEFAULT_VALUE_PROP_DISPLAY_CAMERA_SIZE_UNITS_Y);
		return res;
		// TODO: Implement checks for data
		// if(res <= 128.0) {
	}
	
	
	
	@Override
	public int parseCameraCreationType(String stringValue) {
		int result;
		if(VALUE_DISPLAY_CAMERA_CREATION_TYPE_FIXED_UNITS.equals(stringValue)) {
			result = CAMERA_CREATION_TYPE_FIXED_UNITS;
		}
		else if(VALUE_DISPLAY_CAMERA_CREATION_TYPE_ADJUST_TO_VIEWPORT.equals(stringValue)) {
			result = CAMERA_CREATION_TYPE_ADJUST_TO_VIEWPORT;
		}
		else {
			result = -1;
		}
		
		return result;
	}
	
	@Override
	public String unparseCameraCreationType(int value) {
		String result;
		if(value == CAMERA_CREATION_TYPE_FIXED_UNITS) {
			result = VALUE_DISPLAY_CAMERA_CREATION_TYPE_FIXED_UNITS;
		}
		else if(value == CAMERA_CREATION_TYPE_ADJUST_TO_VIEWPORT) {
			result = VALUE_DISPLAY_CAMERA_CREATION_TYPE_ADJUST_TO_VIEWPORT;
		}
		else {
			result = null;
		}
		
		return result;
	}
	
	
	

	public static MutableCiniConfig defaultCiniConfig() {
		MutableCiniConfig cini = new MutableCiniConfig();

		cini.addSection(CATEGORY_CONFIG)
			.addProperty(CATEGORY_CONFIG, KEY_CONFIG_TYPE, VALUE_CONFIG_TYPE_MAIN)
			.addProperty(CATEGORY_CONFIG, KEY_CONFIG_VERSION, VALUE_CONFIG_VERSION__02)
			.addSection(CATEGORY_DISPLAY)
			.addProperty(CATEGORY_DISPLAY, KEY_DISPLAY_CAMERA_CREATION_TYPE, DEFAULT_VALUE_PROP_DISPLAY_CAMERA_CREATION_TYPE)
			.addProperty(CATEGORY_DISPLAY, KEY_DISPLAY_CAMERA_SIZE_UNITS_X, Integer.toString(DEFAULT_VALUE_PROP_DISPLAY_CAMERA_SIZE_UNITS_X))
			.addProperty(CATEGORY_DISPLAY, KEY_DISPLAY_CAMERA_SIZE_UNITS_Y, Integer.toString(DEFAULT_VALUE_PROP_DISPLAY_CAMERA_SIZE_UNITS_Y));
		
		return cini;
	}
	
}
