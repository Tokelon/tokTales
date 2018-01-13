package com.tokelon.toktales.desktop.engine;

import com.tokelon.toktales.core.engine.HierarchicalInjectConfig;
import com.tokelon.toktales.core.engine.CoreInjectModule;

public class DesktopInjectConfig extends HierarchicalInjectConfig {

    public DesktopInjectConfig() {
        override(new CoreInjectModule());

        override(new DesktopInjectModule());
    }
    
}
