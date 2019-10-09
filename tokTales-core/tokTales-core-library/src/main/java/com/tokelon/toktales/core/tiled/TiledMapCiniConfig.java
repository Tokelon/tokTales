package com.tokelon.toktales.core.tiled;

import com.tokelon.toktales.tools.config.AbstractConfig;
import com.tokelon.toktales.tools.config.ConfigDataException;
import com.tokelon.toktales.tools.config.ICiniConfig;
import com.tokelon.toktales.tools.config.MutableCiniConfig;

public class TiledMapCiniConfig extends AbstractConfig implements ITiledMapConfig {
	
	// Extend CiniFileConfig ?
	
	// Implement this?
	//public void setIgnoreIdentifierCase(boolean ignore);	threat all as lowercase

	
	
	public static final String SECTION_CONFIG = "Config";
	public static final String SECTION_MAP = "Map";
	public static final String SECTION_TILESET_LOCATIONS = "TilesetLocations";
	
	public static final String PROP_CONFIG_TYPE = "Type";
	public static final String PROP_CONFIG_VERSION = "Version";
	public static final String PROP_MAP_TITLE = "Title";
	public static final String PROP_MAP_SPAWNX = "SpawnX";
	public static final String PROP_MAP_SPAWNY = "SpawnY";
	
	public static final String VALUE_CONFIG_TYPE__MAP = "Map";
	public static final String VALUE_CONFIG_VERSION__03 = "0.3";		// Is this the config version or the map version ?

	
	private static final TiledMapCiniConfig defaultConfig;
	
	static {
		ICiniConfig ciniConfig = defaultCiniConfig();
		
		try {
			defaultConfig = new TiledMapCiniConfig(ciniConfig);
		} catch (ConfigDataException condatex) {
			// Must not happen
			throw new RuntimeException(condatex);
		}
	}
	
	
	
	
	
	private final ICiniConfig ciniConfig;
	
	public TiledMapCiniConfig(ICiniConfig ciniConfig) throws ConfigDataException {		// throws ConfigException ?
		checkConfigHeader(ciniConfig);
		
		this.ciniConfig = ciniConfig;
	}
	
	
	private void checkConfigHeader(ICiniConfig ciniConfig) throws ConfigDataException {
		
		String configTypeValue = ciniConfig.getPropertyValue(SECTION_CONFIG, PROP_CONFIG_TYPE);
		if(!(VALUE_CONFIG_TYPE__MAP.equals(configTypeValue))) {
			throw new ConfigDataException(String.format("Config Type \"%s\" is not supported by this class", configTypeValue));
		}
		
		String configVersionValue = ciniConfig.getPropertyValue(SECTION_CONFIG, PROP_CONFIG_VERSION);
		if(!(VALUE_CONFIG_VERSION__03.equals(configVersionValue))) {
			throw new ConfigDataException(String.format("Config Version \"%s\" is not supported by this class", configVersionValue));
		}
	}
	
	/*
	private void checkConfigSections(ICiniConfig ciniConfig) {
		if(!ciniConfig.hasSection(SECTION_CONFIG)) {
			throw new ConfigDataException(String.format("Unsupported config. Missing section: ", SECTION_CONFIG));
		}
	}
	*/
	
//	private void checkConfigData()


	
	@Override
	public String getConfigMapTitle() {
		return parseString(ciniConfig.getPropertyValue(SECTION_MAP, PROP_MAP_TITLE), "");
	}

	@Override
	public int getConfigMapSpawnX() {
		return parseInt(ciniConfig.getPropertyValue(SECTION_MAP, PROP_MAP_SPAWNX), 0);
	}

	@Override
	public int getConfigMapSpawnY() {
		return parseInt(ciniConfig.getPropertyValue(SECTION_MAP, PROP_MAP_SPAWNY), 0);
	}


	
	private static ICiniConfig defaultCiniConfig() {
		MutableCiniConfig cini = new MutableCiniConfig();

		cini.addSection(SECTION_CONFIG)
			.addProperty(SECTION_CONFIG, PROP_CONFIG_TYPE, VALUE_CONFIG_TYPE__MAP)
			.addProperty(SECTION_CONFIG, PROP_CONFIG_VERSION, VALUE_CONFIG_VERSION__03)
			.addSection(SECTION_MAP)
			.addProperty(SECTION_MAP, PROP_MAP_TITLE, "")
			.addProperty(SECTION_MAP, PROP_MAP_SPAWNX, "0")
			.addProperty(SECTION_MAP, PROP_MAP_SPAWNY, "0");

		return cini;
	}
	
	public static TiledMapCiniConfig defaultConfig() {
		return defaultConfig;
	}
	
	
}
