package com.tokelon.toktales.extens.def.desktop.engine.inject;

import com.tokelon.toktales.core.engine.inject.CoreInjectModule;
import com.tokelon.toktales.core.engine.inject.HierarchicalInjectConfig;
import com.tokelon.toktales.desktop.engine.inject.DesktopInjectModule;
import com.tokelon.toktales.desktop.engine.inject.DesktopOverrideInjectModule;
import com.tokelon.toktales.extens.def.core.engine.inject.CoreDefExtensInjectModule;
import com.tokelon.toktales.extens.def.core.engine.inject.CoreDefExtensOverrideInjectModule;

public class DesktopDefExtensInjectConfig extends HierarchicalInjectConfig {

	
    public DesktopDefExtensInjectConfig() {
    	// Core
        extend(new CoreInjectModule());

        // Desktop
        override(new DesktopOverrideInjectModule());
        extend(new DesktopInjectModule());
        
        // Core DefaultExtensions
        override(new CoreDefExtensOverrideInjectModule());
        extend(new CoreDefExtensInjectModule());
        
        // Desktop DefaultExtensions
        override(new DesktopDefExtensOverrideInjectModule());
        extend(new DesktopDefExtensInjectModule());
    }
    
}
