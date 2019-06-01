package com.tokelon.toktales.desktop.engine.inject;

import com.tokelon.toktales.core.engine.inject.CoreInjectConfig;
import com.tokelon.toktales.core.engine.inject.CoreOverrideInjectConfig;
import com.tokelon.toktales.core.engine.inject.MasterInjectConfig;

public class MasterDesktopInjectConfig extends MasterInjectConfig {

	
	@Override
	protected void configure() {
		super.configure();
		
		// Core
		overrideWithFilters(new CoreOverrideInjectConfig());
		extendWithFilters(new CoreInjectConfig());
		
		// Desktop
        overrideWithFilters(new DesktopOverrideInjectConfig());
        extendWithFilters(new DesktopInjectConfig());
	}
	
}
