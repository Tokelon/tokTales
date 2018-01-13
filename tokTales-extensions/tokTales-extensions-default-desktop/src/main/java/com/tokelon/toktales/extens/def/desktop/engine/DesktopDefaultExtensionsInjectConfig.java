package com.tokelon.toktales.extens.def.desktop.engine;

import com.tokelon.toktales.core.engine.inject.CoreInjectModule;
import com.tokelon.toktales.core.engine.inject.HierarchicalInjectConfig;
import com.tokelon.toktales.desktop.engine.inject.DesktopInjectModule;
import com.tokelon.toktales.extens.def.core.engine.CoreDefaultExtensionsInjectModule;

public class DesktopDefaultExtensionsInjectConfig extends HierarchicalInjectConfig {

    public DesktopDefaultExtensionsInjectConfig() {        
        override(new CoreInjectModule());

        override(new DesktopInjectModule(), new CoreDefaultExtensionsInjectModule());
        
        override(new DesktopDefaultExtensionsInjectModule());
    }
    
}
