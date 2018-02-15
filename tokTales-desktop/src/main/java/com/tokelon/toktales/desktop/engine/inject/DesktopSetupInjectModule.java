package com.tokelon.toktales.desktop.engine.inject;

import com.tokelon.toktales.core.engine.inject.AbstractInjectModule;
import com.tokelon.toktales.core.game.IGameAdapter;

public class DesktopSetupInjectModule extends AbstractInjectModule {

    private final Class<? extends IGameAdapter> adapterClass;
    
    /**
     * @param adapterClass
     * @throws NullPointerException If adapterClass is null.
     */
    public DesktopSetupInjectModule(Class<? extends IGameAdapter> adapterClass) {
    	if(adapterClass == null) {
    		throw new NullPointerException();
    	}
    	
        this.adapterClass = adapterClass;
    }

    
    @Override
    protected void configure() {
    	bindInGameScope(IGameAdapter.class, adapterClass);
    }

}
