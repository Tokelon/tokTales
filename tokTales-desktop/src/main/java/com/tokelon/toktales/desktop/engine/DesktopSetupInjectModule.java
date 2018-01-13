package com.tokelon.toktales.desktop.engine;

import com.tokelon.toktales.core.engine.inject.AbstractInjectModule;
import com.tokelon.toktales.core.game.IGameAdapter;

public class DesktopSetupInjectModule extends AbstractInjectModule {

    private final IGameAdapter gameAdapter;
    
    /**
     * @param gameAdapter
     * @throws NullPointerException If gameAdapter is null.
     */
    public DesktopSetupInjectModule(IGameAdapter gameAdapter) {
    	if(gameAdapter == null) {
    		throw new NullPointerException();
    	}
    	
        this.gameAdapter = gameAdapter;
    }

    
    @Override
    protected void configure() {
        bind(IGameAdapter.class).toInstance(gameAdapter);
    }

}
