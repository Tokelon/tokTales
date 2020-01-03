package com.tokelon.toktales.android.engine.inject;

import com.tokelon.toktales.core.engine.inject.AbstractInjectModule;

import android.content.Context;

public class AndroidSetupInjectModule extends AbstractInjectModule {


	private final Context appContext;
    
	/**
	 * @param applicationContext
	 * @throws NullPointerException If applicationContext or gameAdapter is null.
	 */
	public AndroidSetupInjectModule(Context applicationContext) {
		if(applicationContext == null) {
			throw new NullPointerException();
		}

		this.appContext = applicationContext;
	}


	@Override
	protected void configure() {
		bind(Context.class).toInstance(appContext);
	}
    
}
