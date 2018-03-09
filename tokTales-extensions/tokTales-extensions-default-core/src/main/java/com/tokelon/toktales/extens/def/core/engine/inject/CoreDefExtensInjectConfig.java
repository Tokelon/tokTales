package com.tokelon.toktales.extens.def.core.engine.inject;

import com.tokelon.toktales.core.engine.inject.CoreInjectModule;
import com.tokelon.toktales.core.engine.inject.HierarchicalInjectConfig;

public class CoreDefExtensInjectConfig extends HierarchicalInjectConfig {

    public CoreDefExtensInjectConfig() {
    	// Core - base
        extend(new CoreInjectModule());

        // DefaultExtensions - override first then extend
        override(new CoreDefExtensOverrideInjectModule());
        extend(new CoreDefExtensInjectModule());
    }
    
}
