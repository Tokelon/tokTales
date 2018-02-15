package com.tokelon.toktales.android.engine.inject;

import com.tokelon.toktales.core.engine.inject.AbstractInjectModule;
import com.tokelon.toktales.core.game.IGameAdapter;

import android.content.Context;

public class AndroidSetupInjectModule extends AbstractInjectModule {

	
    private final Class<? extends IGameAdapter> adapterClass;
	private final Context appContext;
    
    /**
     * @param applicationContext
     * @param adapterClass
     * @throws NullPointerException If applicationContext or gameAdapter is null.
     */
    public AndroidSetupInjectModule(Class<? extends IGameAdapter> adapterClass, Context applicationContext) {
        if(applicationContext == null || adapterClass == null) {
            throw new NullPointerException();
        }
        
        this.adapterClass = adapterClass;
        this.appContext = applicationContext;
    }

    
    @Override
    protected void configure() {
        bindInGameScope(IGameAdapter.class, adapterClass);
    	bind(Context.class).toInstance(appContext);
    }

    
}
