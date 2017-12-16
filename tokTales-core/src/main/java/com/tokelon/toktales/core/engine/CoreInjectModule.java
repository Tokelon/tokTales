package com.tokelon.toktales.core.engine;

import com.google.inject.AbstractModule;

public class CoreInjectModule extends AbstractModule {

    @Override
    protected void configure() {
        // use untargeted bindings so that the injector may prepare dependencies eagerly
        
        //bind(IEngine.class).to(Engine.class);
    }

}
