package com.tokelon.toktales.extens.def.desktop.engine;

import com.tokelon.toktales.core.engine.inject.AbstractInjectModule;

public class DesktopDefaultExtensionsInjectModule extends AbstractInjectModule {

    @Override
    protected void configure() {
        // ex. cannot bind duplicate without overriding
        //bind(IInjectConfig.class).to(SimpleInjectConfig.class);
    }

}
