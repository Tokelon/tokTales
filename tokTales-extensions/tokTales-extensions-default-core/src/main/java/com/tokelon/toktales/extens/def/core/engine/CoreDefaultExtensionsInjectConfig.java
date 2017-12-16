package com.tokelon.toktales.extens.def.core.engine;

import com.tokelon.toktales.core.engine.AdvancedInjectConfig;
import com.tokelon.toktales.core.engine.CoreInjectModule;

public class CoreDefaultExtensionsInjectConfig extends AdvancedInjectConfig {

    public CoreDefaultExtensionsInjectConfig() {
        override(new CoreInjectModule());

        override(new CoreDefaultExtensionsInjectModule());
    }
    
}
