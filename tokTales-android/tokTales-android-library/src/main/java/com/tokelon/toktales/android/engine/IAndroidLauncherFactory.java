package com.tokelon.toktales.android.engine;

import com.tokelon.toktales.core.engine.log.ILoggerFactory;
import com.tokelon.toktales.tools.core.sub.inject.config.IHierarchicalInjectConfig;

import android.content.Context;

/** Creates engine launcher instances and engine launcher builders for the Android platform.
 */
public interface IAndroidLauncherFactory {


	/** Creates an engine launcher with the default properties of this factory.
	 *
	 * @param applicationContext
	 * @return A new default engine launcher.
	 */
	public IAndroidEngineLauncher createDefaultLauncher(Context applicationContext);

	/** Creates an engine launcher builder with the default properties of this factory.
	 *
	 * @param applicationContext
	 * @return A new default engine launcher builder.
	 */
	public IAndroidEngineLauncherBuilder createDefaultLauncherBuilder(Context applicationContext);


	/** Builds an android engine launcher with given or default properties.
	 */
	public interface IAndroidEngineLauncherBuilder {


		/** Creates a new android engine launcher.
		 *
		 * @return A new launcher.
		 */
		public IAndroidEngineLauncher build();


		/**
		 * @param injectConfig
		 * @return This builder.
		 */
		public IAndroidEngineLauncherBuilder withInjectConfig(IHierarchicalInjectConfig injectConfig);

		/**
		 * @param applicationContext
		 * @return This builder.
		 */
		public IAndroidEngineLauncherBuilder withContext(Context applicationContext);

		/**
		 * @param loggerFactory
		 * @return This builder.
		 */
		public IAndroidEngineLauncherBuilder withLoggerFactory(ILoggerFactory loggerFactory);
	}

}
