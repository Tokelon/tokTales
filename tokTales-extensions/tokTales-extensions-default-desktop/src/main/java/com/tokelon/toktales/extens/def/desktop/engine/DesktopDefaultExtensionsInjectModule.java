package com.tokelon.toktales.extens.def.desktop.engine;

import com.google.inject.AbstractModule;

public class DesktopDefaultExtensionsInjectModule extends AbstractModule {

    @Override
    protected void configure() {
        // ex. cannot bind duplicate without overriding
        //bind(IInjectConfig.class).to(SimpleInjectConfig.class);
    }

}
