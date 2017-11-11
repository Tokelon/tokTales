package com.tokelon.toktales.core.values;

import com.tokelon.toktales.core.storage.IApplicationLocation;
import com.tokelon.toktales.core.storage.utils.IConformedPath;
import com.tokelon.toktales.core.storage.utils.MutablePathImpl;


enum ApplicationLocation implements IApplicationLocation {
	
	/* External */
	APP_DIR_EXT(ApplicationPlace.LOC_APDR_EXT),
	
	APP_DIR_EXT_MAPS(ApplicationPlace.LOC_APDR_EXT_MAPS),
	
	APP_DIR_EXT_ASSETS(ApplicationPlace.LOC_APDR_EXT_ASSETS),
	APP_DIR_EXT_ASSETS_GRAPHICS(
			ApplicationPlace.LOC_APDR_EXT_ASSETS,
			ApplicationPlace.LOC_APDR_EXT_ASSETS_GRAPHICS),
	APP_DIR_EXT_ASSETS_GRAPHICS_SPRITES(
			ApplicationPlace.LOC_APDR_EXT_ASSETS,
			ApplicationPlace.LOC_APDR_EXT_ASSETS_GRAPHICS,
			ApplicationPlace.LOC_APDR_EXT_ASSETS_GRAPHICS_SPRITES),
	
	/* Internal */
	APP_DIR_INT_ASSETS(
			ApplicationPlace.LOC_APDR_INT_ASSETS),
			
	APP_DIR_INT_ASSETS_GRAPHICS(
			ApplicationPlace.LOC_APDR_INT_ASSETS_GRAPHICS),
	APP_DIR_INT_ASSETS_GRAPHICS_SPRITES(
			ApplicationPlace.LOC_APDR_INT_ASSETS_GRAPHICS,
			ApplicationPlace.LOC_APDR_INT_ASSETS_GRAPHICS_SPRITES),
	APP_DIR_INT_ASSETS_GRAPHICS_SPECIAL(
			ApplicationPlace.LOC_APDR_INT_ASSETS_GRAPHICS,
			ApplicationPlace.LOC_APDR_INT_ASSETS_GRAPHICS_SPECIAL),
	APP_DIR_INT_ASSETS_GRAPHICS_PLAYER(
			ApplicationPlace.LOC_APDR_INT_ASSETS_GRAPHICS,
			ApplicationPlace.LOC_APDR_INT_ASSETS_GRAPHICS_PLAYER),
			
	;


	
	private final MutablePathImpl path = new MutablePathImpl();
	
	
	private ApplicationLocation(ApplicationPlace... locations) {
		for(ApplicationPlace loc: locations) {
			path.append(loc.getLocation());
		}
	}

	@Override
	public IConformedPath getLocationPath() {
		return path;
	}


}
