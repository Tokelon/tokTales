package com.tokelon.toktales.desktop.engine;

import com.tokelon.toktales.core.engine.inject.AbstractInjectModule;
import com.tokelon.toktales.core.game.IGameAdapter;

public class DesktopSetupInjectModule extends AbstractInjectModule {

    private final IGameAdapter gameAdapter;
    
    public DesktopSetupInjectModule(IGameAdapter gameAdapter) {
        this.gameAdapter = gameAdapter;
    }

    
    @Override
    protected void configure() {
        bind(IGameAdapter.class).toInstance(gameAdapter);
    }

}
