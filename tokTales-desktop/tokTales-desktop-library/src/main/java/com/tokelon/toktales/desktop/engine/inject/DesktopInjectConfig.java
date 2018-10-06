package com.tokelon.toktales.desktop.engine.inject;

import com.tokelon.toktales.core.engine.inject.CoreInjectModule;
import com.tokelon.toktales.core.engine.inject.HierarchicalInjectConfig;

public class DesktopInjectConfig extends HierarchicalInjectConfig {

	
    public DesktopInjectConfig() {
    	// Core - base
        extend(new CoreInjectModule());

        // Desktop - override first then extend
        override(new DesktopOverrideInjectModule());
        extend(new DesktopInjectModule());
    }
    
}
