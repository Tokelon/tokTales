package com.tokelon.toktales.extens.def.android.engine.inject;

import com.tokelon.toktales.android.engine.inject.AndroidInjectModule;
import com.tokelon.toktales.android.engine.inject.AndroidOverrideInjectModule;
import com.tokelon.toktales.core.engine.inject.CoreInjectModule;
import com.tokelon.toktales.core.engine.inject.HierarchicalInjectConfig;
import com.tokelon.toktales.extens.def.core.engine.inject.CoreDefExtensInjectModule;
import com.tokelon.toktales.extens.def.core.engine.inject.CoreDefExtensOverrideInjectModule;

public class AndroidDefExtensInjectConfig extends HierarchicalInjectConfig {

	
    public AndroidDefExtensInjectConfig() {
    	// Core
        extend(new CoreInjectModule());

        // Android
        override(new AndroidOverrideInjectModule());
        extend(new AndroidInjectModule());
        
        // Core DefaultExtensions
        override(new CoreDefExtensOverrideInjectModule());
        extend(new CoreDefExtensInjectModule());
        
        // Android DefaultExtensions
        override(new AndroidDefExtensOverrideInjectModule());
        extend(new AndroidDefExtensInjectModule());
    }
    
}
