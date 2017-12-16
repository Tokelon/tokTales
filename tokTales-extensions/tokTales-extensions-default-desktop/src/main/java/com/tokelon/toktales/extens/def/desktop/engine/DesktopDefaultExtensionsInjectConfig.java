package com.tokelon.toktales.extens.def.desktop.engine;

import com.tokelon.toktales.core.engine.AdvancedInjectConfig;
import com.tokelon.toktales.core.engine.CoreInjectModule;
import com.tokelon.toktales.desktop.engine.DesktopInjectModule;
import com.tokelon.toktales.extens.def.core.engine.CoreDefaultExtensionsInjectModule;

public class DesktopDefaultExtensionsInjectConfig extends AdvancedInjectConfig {

    public DesktopDefaultExtensionsInjectConfig() {        
        override(new CoreInjectModule());

        override(new DesktopInjectModule(), new CoreDefaultExtensionsInjectModule());
        
        override(new DesktopDefaultExtensionsInjectModule());
    }
    
}
