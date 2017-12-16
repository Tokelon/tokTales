package com.tokelon.toktales.extens.def.android.engine;

import com.tokelon.toktales.android.engine.AndroidInjectModule;
import com.tokelon.toktales.core.engine.AdvancedInjectConfig;
import com.tokelon.toktales.core.engine.CoreInjectModule;
import com.tokelon.toktales.extens.def.core.engine.CoreDefaultExtensionsInjectModule;

public class AndroidDefaultExtensionsInjectConfig extends AdvancedInjectConfig {

    public AndroidDefaultExtensionsInjectConfig() {
        override(new CoreInjectModule());

        override(new AndroidInjectModule(), new CoreDefaultExtensionsInjectModule());
        
        override(new AndroidDefaultExtensionsInjectModule());
    }
    
}
