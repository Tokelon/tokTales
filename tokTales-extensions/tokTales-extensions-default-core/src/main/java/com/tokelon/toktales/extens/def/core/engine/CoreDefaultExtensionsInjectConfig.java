package com.tokelon.toktales.extens.def.core.engine;

import com.tokelon.toktales.core.engine.HierarchicalInjectConfig;
import com.tokelon.toktales.core.engine.CoreInjectModule;

public class CoreDefaultExtensionsInjectConfig extends HierarchicalInjectConfig {

    public CoreDefaultExtensionsInjectConfig() {
        override(new CoreInjectModule());

        override(new CoreDefaultExtensionsInjectModule());
    }
    
}
