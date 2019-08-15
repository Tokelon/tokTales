package com.tokelon.toktales.core.values;

import com.tokelon.toktales.core.storage.IApplicationLocation;
import com.tokelon.toktales.core.storage.ILocation;

public final class LocationsAndPlaces {
	// TODO: Use this or remove?
	// Maybe rename to StorageLocations?

	private LocationsAndPlaces() {}
	
	public static final IApplicationLocation LOCATION_INTERNAL_ASSETS_SPRITES = ApplicationLocation.APP_DIR_INT_ASSETS_GRAPHICS_SPRITES;
	
	public static final ILocation LOCATION_EXTERNAL = ApplicationLocation.APP_DIR_EXT;
	public static final IApplicationLocation LOCATION_EXTERNAL_MAPS = ApplicationLocation.APP_DIR_EXT_MAPS;
	public static final IApplicationLocation LOCATION_EXTERNAL_ASSETS_SPRITES = ApplicationLocation.APP_DIR_EXT_ASSETS_GRAPHICS_SPRITES;
	
}
