package com.tokelon.toktales.android.engine.inject;

import com.tokelon.toktales.core.engine.inject.CoreInjectModule;
import com.tokelon.toktales.core.engine.inject.HierarchicalInjectConfig;

public class AndroidInjectConfig extends HierarchicalInjectConfig {

	
    public AndroidInjectConfig() {
    	// Core - base
        extend(new CoreInjectModule());

        // Android - override first then extend
        override(new AndroidOverrideInjectModule());
        extend(new AndroidInjectModule());
    }
    
}
