package com.tokelon.toktales.extens.def.desktop.engine;

import com.tokelon.toktales.core.engine.inject.CoreInjectModule;
import com.tokelon.toktales.core.engine.inject.HierarchicalInjectConfig;
import com.tokelon.toktales.desktop.engine.inject.DesktopInjectModule;
import com.tokelon.toktales.desktop.engine.inject.DesktopOverrideInjectModule;
import com.tokelon.toktales.extens.def.core.engine.inject.CoreDefaultExtensionsInjectModule;
import com.tokelon.toktales.extens.def.core.engine.inject.CoreDefaultExtensionsOverrideInjectModule;

public class DesktopDefaultExtensionsInjectConfig extends HierarchicalInjectConfig {

	
    public DesktopDefaultExtensionsInjectConfig() {
    	// Core
        extend(new CoreInjectModule());

        // Desktop
        override(new DesktopOverrideInjectModule());
        extend(new DesktopInjectModule());
        
        // Core DefaultExtensions
        override(new CoreDefaultExtensionsOverrideInjectModule());
        extend(new CoreDefaultExtensionsInjectModule());
        
        // Desktop DefaultExtensions
        override(new DesktopDefaultExtensionsOverrideInjectModule());
        extend(new DesktopDefaultExtensionsInjectModule());
    }
    
}
