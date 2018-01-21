package com.tokelon.toktales.extens.def.android.engine;

import com.tokelon.toktales.android.engine.inject.AndroidInjectModule;
import com.tokelon.toktales.android.engine.inject.AndroidOverrideInjectModule;
import com.tokelon.toktales.core.engine.inject.CoreInjectModule;
import com.tokelon.toktales.core.engine.inject.HierarchicalInjectConfig;
import com.tokelon.toktales.extens.def.core.engine.CoreDefaultExtensionsInjectModule;
import com.tokelon.toktales.extens.def.core.engine.CoreDefaultExtensionsOverrideInjectModule;

public class AndroidDefaultExtensionsInjectConfig extends HierarchicalInjectConfig {

	
    public AndroidDefaultExtensionsInjectConfig() {
    	// Core
        extend(new CoreInjectModule());

        // Android
        override(new AndroidOverrideInjectModule());
        extend(new AndroidInjectModule());
        
        // Core DefaultExtensions
        override(new CoreDefaultExtensionsOverrideInjectModule());
        extend(new CoreDefaultExtensionsInjectModule());
        
        // Android DefaultExtensions
        override(new AndroidDefaultExtensionsOverrideInjectModule());
        extend(new AndroidDefaultExtensionsInjectModule());
    }
    
}
