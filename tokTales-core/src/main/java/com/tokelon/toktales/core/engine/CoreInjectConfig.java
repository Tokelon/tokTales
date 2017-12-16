package com.tokelon.toktales.core.engine;

public class CoreInjectConfig extends AdvancedInjectConfig {

    public CoreInjectConfig() {
        extend(new CoreInjectModule());
    }
    
}
