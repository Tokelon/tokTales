package com.tokelon.toktales.extens.def.android.engine;

import com.tokelon.toktales.android.engine.inject.AndroidInjectModule;
import com.tokelon.toktales.core.engine.inject.CoreInjectModule;
import com.tokelon.toktales.core.engine.inject.HierarchicalInjectConfig;
import com.tokelon.toktales.extens.def.core.engine.CoreDefaultExtensionsInjectModule;

public class AndroidDefaultExtensionsInjectConfig extends HierarchicalInjectConfig {

    public AndroidDefaultExtensionsInjectConfig() {
        override(new CoreInjectModule());

        override(new AndroidInjectModule(), new CoreDefaultExtensionsInjectModule());
        
        override(new AndroidDefaultExtensionsInjectModule());
    }
    
}
