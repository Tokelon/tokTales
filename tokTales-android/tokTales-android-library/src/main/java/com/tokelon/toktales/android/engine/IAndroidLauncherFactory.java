package com.tokelon.toktales.android.engine;

import com.tokelon.toktales.core.engine.log.ILoggerFactory;
import com.tokelon.toktales.tools.core.sub.inject.config.IHierarchicalInjectConfig;

import android.content.Context;

public interface IAndroidLauncherFactory {


	public IAndroidEngineLauncher createDefaultLauncher(Context applicationContext);
	public IAndroidEngineLauncherBuilder createDefaultLauncherBuilder(Context applicationContext);
	
	
	public interface IAndroidEngineLauncherBuilder {
		public IAndroidEngineLauncher build();
		
		public IAndroidEngineLauncherBuilder withInjectConfig(IHierarchicalInjectConfig injectConfig);
		public IAndroidEngineLauncherBuilder withContext(Context applicationContext);
		public IAndroidEngineLauncherBuilder withLoggerFactory(ILoggerFactory loggerFactory);
	}
	
}
