package com.tokelon.toktales.android.engine;

import com.tokelon.toktales.core.engine.inject.AbstractInjectModule;
import com.tokelon.toktales.core.game.IGameAdapter;

import android.content.Context;

public class AndroidSetupInjectModule extends AbstractInjectModule {

    private final Context appContext;
    private final IGameAdapter gameAdapter;
    
    /**
     * 
     * @param applicationContext
     * @throws NullPointerException If applicaionContext is null.
     */
    public AndroidSetupInjectModule(Context applicationContext) {
        this(applicationContext, null);
    }
    
    /**
     * 
     * @param applicationContext
     * @param gameAdapter
     * @throws NullPointerException If applicationContext is null.
     */
    public AndroidSetupInjectModule(Context applicationContext, IGameAdapter gameAdapter) {
        if(applicationContext == null) {
            throw new NullPointerException();
        }
        
        this.appContext = applicationContext;
        this.gameAdapter = gameAdapter;
    }

    
    @Override
    protected void configure() {
        bind(Context.class).toInstance(appContext);
        
        if(gameAdapter != null) {
            bind(IGameAdapter.class).toInstance(gameAdapter);
        }
    }

    
}
