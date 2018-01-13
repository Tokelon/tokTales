package com.tokelon.toktales.core.engine.inject;

public class CoreInjectConfig extends HierarchicalInjectConfig {

    public CoreInjectConfig() {
        extend(new CoreInjectModule());
    }
    
}
