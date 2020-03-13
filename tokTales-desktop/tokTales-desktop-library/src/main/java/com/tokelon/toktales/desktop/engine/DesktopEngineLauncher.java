package com.tokelon.toktales.desktop.engine;

import com.tokelon.toktales.core.engine.BaseEngineLauncher;
import com.tokelon.toktales.core.engine.IEngineLooper;
import com.tokelon.toktales.core.engine.log.ILoggerFactory;
import com.tokelon.toktales.core.engine.setup.IEngineSetup;
import com.tokelon.toktales.desktop.engine.inject.MasterDesktopInjectConfig;
import com.tokelon.toktales.desktop.engine.setup.DefaultDesktopEngineSetup;
import com.tokelon.toktales.tools.core.sub.inject.config.IHierarchicalInjectConfig;

public class DesktopEngineLauncher extends BaseEngineLauncher implements IDesktopEngineLauncher {


	/** Constructor with an inject config.
	 * <p>
	 * A no-op looper will be used.
	 *
	 * @param injectConfig
	 *
	 * @see MasterDesktopInjectConfig
	 */
	protected DesktopEngineLauncher(IHierarchicalInjectConfig injectConfig) {
		super(injectConfig);
	}

	/** Constructor with an inject config and a logger factory.
	 * <p>
	 * A no-op looper will be used.
	 *
	 * @param injectConfig
	 * @param loggerFactory
	 *
	 * @see MasterDesktopInjectConfig
	 */
	protected DesktopEngineLauncher(IHierarchicalInjectConfig injectConfig, ILoggerFactory loggerFactory) {
		super(injectConfig, loggerFactory);
	}

	/** Constructor with an inject config and a looper.
	 *
	 * @param injectConfig
	 * @param defaultLooper
	 *
	 * @see MasterDesktopInjectConfig
	 */
	public DesktopEngineLauncher(IHierarchicalInjectConfig injectConfig, IEngineLooper defaultLooper) {
		super(injectConfig, defaultLooper);
	}

	/** Constructor with an inject config, a logger factory, and a looper.
	 *
	 * @param injectConfig
	 * @param defaultLooper
	 * @param loggerFactory
	 *
	 * @see MasterDesktopInjectConfig
	 */
	public DesktopEngineLauncher(IHierarchicalInjectConfig injectConfig, IEngineLooper defaultLooper, ILoggerFactory loggerFactory) {
		super(injectConfig, defaultLooper, loggerFactory);
	}


	@Override
	protected IEngineSetup createDefaultSetup() {
		return new DefaultDesktopEngineSetup();
	}

}
