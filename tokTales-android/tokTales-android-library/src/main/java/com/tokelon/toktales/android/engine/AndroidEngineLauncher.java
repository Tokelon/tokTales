package com.tokelon.toktales.android.engine;

import com.tokelon.toktales.android.engine.inject.AndroidSetupInjectModule;
import com.tokelon.toktales.android.engine.inject.MasterAndroidInjectConfig;
import com.tokelon.toktales.android.engine.setup.DefaultAndroidEngineSetup;
import com.tokelon.toktales.core.engine.BaseEngineLauncher;
import com.tokelon.toktales.core.engine.EngineException;
import com.tokelon.toktales.core.engine.IEngineContext;
import com.tokelon.toktales.core.engine.log.ILoggerFactory;
import com.tokelon.toktales.core.engine.setup.IEngineSetup;
import com.tokelon.toktales.core.game.IGameAdapter;
import com.tokelon.toktales.tools.core.sub.inject.config.IHierarchicalInjectConfig;

import android.content.Context;

public class AndroidEngineLauncher extends BaseEngineLauncher implements IAndroidEngineLauncher {


	private final Context appContext;

	/** Constructor with an inject config and an application context.
	 *
	 * @param injectConfig
	 * @param applicationContext
	 *
	 * @see MasterAndroidInjectConfig
	 */
	public AndroidEngineLauncher(IHierarchicalInjectConfig injectConfig, Context applicationContext) {
		super(injectConfig);

		this.appContext = applicationContext;
	}

	/** Constructor with an inject config, an application context and a logger factory.
	 *
	 * @param injectConfig
	 * @param applicationContext
	 * @param loggerFactory
	 *
	 * @see MasterAndroidInjectConfig
	 */
	public AndroidEngineLauncher(IHierarchicalInjectConfig injectConfig, Context applicationContext, ILoggerFactory loggerFactory) {
		super(injectConfig, loggerFactory);

		this.appContext = applicationContext;
	}


	@Override
	protected IEngineSetup createDefaultSetup() {
		return new DefaultAndroidEngineSetup();
	}


	/** {@inheritDoc} */
	@Override
	protected IEngineContext createEngine(Class<? extends IGameAdapter> adapter, IEngineSetup setup) throws EngineException {
		IHierarchicalInjectConfig injectConfig = getInjectConfig();

		// Inject game adapter and app context
		injectConfig.extend(new AndroidSetupInjectModule(appContext));

		return super.createEngine(adapter, setup);
	}


	/** The default implementation does nothing.
	 * <p>
	 * Because of the way Android works, {@link #loop}, as well as {@link #startupEngine} and {@link #shutdownEngine} will not be called.<br>
	 * Instead the main loop and the driver calls will be handled inside activities (integrations).
	 *
	 * @param engineContext
	 */
	@Override
	protected void runEngine(IEngineSetup setup, IEngineContext engineContext) throws EngineException {
		// Do NOT call super here
		// The Android lifecycle is a bit more dynamic, so we can not start anything here

		// We do need to build up the setup however
		setup.buildUp(engineContext);

		// TODO: Call setup.tearDown() somewhere ?
	}

}
