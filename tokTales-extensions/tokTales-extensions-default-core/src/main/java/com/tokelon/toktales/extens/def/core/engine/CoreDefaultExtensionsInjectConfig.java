package com.tokelon.toktales.extens.def.core.engine;

import com.tokelon.toktales.core.engine.inject.CoreInjectModule;
import com.tokelon.toktales.core.engine.inject.HierarchicalInjectConfig;

public class CoreDefaultExtensionsInjectConfig extends HierarchicalInjectConfig {

    public CoreDefaultExtensionsInjectConfig() {
        override(new CoreInjectModule());

        override(new CoreDefaultExtensionsInjectModule());
    }
    
}
