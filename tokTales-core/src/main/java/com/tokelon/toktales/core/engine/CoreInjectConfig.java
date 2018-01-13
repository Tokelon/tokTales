package com.tokelon.toktales.core.engine;

public class CoreInjectConfig extends HierarchicalInjectConfig {

    public CoreInjectConfig() {
        extend(new CoreInjectModule());
    }
    
}
