package com.tokelon.toktales.android.engine;

import com.tokelon.toktales.core.engine.AdvancedInjectConfig;
import com.tokelon.toktales.core.engine.CoreInjectModule;

public class AndroidInjectConfig extends AdvancedInjectConfig {

    public AndroidInjectConfig() {
        override(new CoreInjectModule());

        override(new AndroidInjectModule());
    }
    
}
