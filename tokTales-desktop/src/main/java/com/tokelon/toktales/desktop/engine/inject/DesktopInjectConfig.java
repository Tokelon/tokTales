package com.tokelon.toktales.desktop.engine.inject;

import com.tokelon.toktales.core.engine.inject.CoreInjectModule;
import com.tokelon.toktales.core.engine.inject.HierarchicalInjectConfig;

public class DesktopInjectConfig extends HierarchicalInjectConfig {

    public DesktopInjectConfig() {
        override(new CoreInjectModule());

        override(new DesktopInjectModule());
    }
    
}
