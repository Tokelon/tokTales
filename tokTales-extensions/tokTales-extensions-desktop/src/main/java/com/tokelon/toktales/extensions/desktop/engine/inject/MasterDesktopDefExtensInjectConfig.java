package com.tokelon.toktales.extensions.desktop.engine.inject;

import com.tokelon.toktales.core.engine.inject.CoreInjectConfig;
import com.tokelon.toktales.core.engine.inject.CoreOverrideInjectConfig;
import com.tokelon.toktales.desktop.engine.inject.DesktopInjectConfig;
import com.tokelon.toktales.desktop.engine.inject.DesktopOverrideInjectConfig;
import com.tokelon.toktales.extensions.core.engine.inject.CoreDefExtensInjectConfig;
import com.tokelon.toktales.extensions.core.engine.inject.CoreDefExtensOverrideInjectConfig;
import com.tokelon.toktales.tools.core.sub.inject.config.MasterInjectConfig;

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
