package com.tokelon.toktales.core.values;

import com.tokelon.toktales.core.storage.IStoragePlace;

enum ApplicationPlace implements IStoragePlace {
	
	//TODO: This should be in tokelon project?!
	
	/* External */
	LOC_APDR_EXT("Tokelon"),
	LOC_APDR_EXT_MAPS("maps"),
	
	LOC_APDR_EXT_ASSETS("assets"),
	LOC_APDR_EXT_ASSETS_GRAPHICS("graphics"),
	LOC_APDR_EXT_ASSETS_GRAPHICS_SPRITES("sprites"),
	
	
	/* Internal */
	LOC_APDR_INT_ASSETS(""),
	LOC_APDR_INT_ASSETS_GRAPHICS("graphics"),
	LOC_APDR_INT_ASSETS_GRAPHICS_SPRITES("sprites"),
	LOC_APDR_INT_ASSETS_GRAPHICS_SPECIAL("special"),
	LOC_APDR_INT_ASSETS_GRAPHICS_PLAYER("player"),
	
	;
	
	private final String loc;
	
	private ApplicationPlace(String location) {
		loc = location;
	}
	
	public String getLocation() {
		return loc;
	}
	
}
