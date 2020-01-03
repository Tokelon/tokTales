package com.tokelon.toktales.android.engine.inject;

import javax.inject.Inject;
import javax.inject.Provider;

import com.tokelon.toktales.core.engine.inject.AbstractInjectModule;
import com.tokelon.toktales.core.engine.inject.annotation.StorageRoot;

import android.content.Context;

public class AndroidValuesInjectModule extends AbstractInjectModule {


	@Override
	protected void configure() {
		bind(String.class).annotatedWith(StorageRoot.class).toProvider(StorageRootProvider.class);
	}
	
	
	public static class StorageRootProvider implements Provider<String> {
		private final Context applicationContext;
		
		@Inject
		public StorageRootProvider(Context applicationContext) {
			this.applicationContext = applicationContext;
		}
		
		@Override
		public String get() {
			return applicationContext.getExternalFilesDir(null).getPath();
		}
	}
	
}
