package com.tokelon.toktales.extens.def.desktop.engine.inject;

import com.tokelon.toktales.core.engine.inject.CoreInjectConfig;
import com.tokelon.toktales.core.engine.inject.CoreOverrideInjectConfig;
import com.tokelon.toktales.core.engine.inject.MasterInjectConfig;
import com.tokelon.toktales.desktop.engine.inject.DesktopInjectConfig;
import com.tokelon.toktales.desktop.engine.inject.DesktopOverrideInjectConfig;
import com.tokelon.toktales.extens.def.core.engine.inject.CoreDefExtensInjectConfig;
import com.tokelon.toktales.extens.def.core.engine.inject.CoreDefExtensOverrideInjectConfig;

public class MasterDesktopDefExtensInjectConfig extends MasterInjectConfig {


	@Override
	protected void configure() {
		super.configure();
		
		// Core
		overrideWithFilters(new CoreOverrideInjectConfig());
		extendWithFilters(new CoreInjectConfig());
		
		// Desktop
        overrideWithFilters(new DesktopOverrideInjectConfig());
        extendWithFilters(new DesktopInjectConfig());
        
        // Core DefaultExtensions
        overrideWithFilters(new CoreDefExtensOverrideInjectConfig());
        extendWithFilters(new CoreDefExtensInjectConfig());
        
        // Desktop DefaultExtensions
        overrideWithFilters(new DesktopDefExtensOverrideInjectConfig());
        extendWithFilters(new DesktopDefExtensInjectConfig());
	}
	
}
