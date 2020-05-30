package com.tokelon.toktales.android.engine;

import com.tokelon.toktales.android.engine.inject.MasterAndroidInjectConfig;
import com.tokelon.toktales.core.engine.log.ILoggerFactory;
import com.tokelon.toktales.core.engine.log.LoggingManager;
import com.tokelon.toktales.tools.core.sub.inject.config.IHierarchicalInjectConfig;

import android.content.Context;

/** Implementation of {@link IAndroidLauncherFactory}.
 */
public class AndroidLauncherFactory implements IAndroidLauncherFactory {


	@Override
	public IAndroidEngineLauncher createDefaultLauncher(Context applicationContext) {
		return new AndroidEngineLauncherBuilder(applicationContext).build();
	}

	@Override
	public IAndroidEngineLauncherBuilder createDefaultLauncherBuilder(Context applicationContext) {
		return new AndroidEngineLauncherBuilder(applicationContext);
	}


	/** Implementation of {@link IAndroidEngineLauncherBuilder}.
	 */
	public static class AndroidEngineLauncherBuilder implements IAndroidEngineLauncherBuilder {
		private IHierarchicalInjectConfig injectConfig;
		private Context applicationContext;
		private ILoggerFactory loggerFactory;

		/** Constructor with an application context and default properties.
		 *
		 * @param applicationContext
		 */
		public AndroidEngineLauncherBuilder(Context applicationContext) {
			this(
				new MasterAndroidInjectConfig(),
				applicationContext,
				LoggingManager.getLoggerFactory()
			);
		}

		/** Constructor with an application context and android engine launcher properties.
		 *
		 * @param injectConfig
		 * @param applicationContext
		 * @param loggerFactory
		 */
		public AndroidEngineLauncherBuilder(
				IHierarchicalInjectConfig injectConfig,
				Context applicationContext,
				ILoggerFactory loggerFactory
		) {
			this.injectConfig = injectConfig;
			this.applicationContext = applicationContext;
			this.loggerFactory = loggerFactory;
		}


		@Override
		public IAndroidEngineLauncher build() {
			return new AndroidEngineLauncher(injectConfig, applicationContext, loggerFactory);
		}

		@Override
		public IAndroidEngineLauncherBuilder withInjectConfig(IHierarchicalInjectConfig injectConfig) {
			this.injectConfig = injectConfig;
			return this;
		}

		@Override
		public IAndroidEngineLauncherBuilder withContext(Context applicationContext) {
			this.applicationContext = applicationContext;
			return this;
		}

		@Override
		public IAndroidEngineLauncherBuilder withLoggerFactory(ILoggerFactory loggerFactory) {
			this.loggerFactory = loggerFactory;
			return this;
		}
	}

}
