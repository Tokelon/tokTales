package com.tokelon.toktales.android.engine;

import com.tokelon.toktales.core.engine.HierarchicalInjectConfig;
import com.tokelon.toktales.core.engine.CoreInjectModule;

public class AndroidInjectConfig extends HierarchicalInjectConfig {

    public AndroidInjectConfig() {
        override(new CoreInjectModule());

        override(new AndroidInjectModule());
    }
    
}
