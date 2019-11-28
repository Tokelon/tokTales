package com.tokelon.toktales.desktop.engine;

import com.tokelon.toktales.core.engine.BaseEngineLauncher;
import com.tokelon.toktales.core.engine.IEngineLooper;
import com.tokelon.toktales.core.engine.log.ILoggerFactory;
import com.tokelon.toktales.desktop.engine.inject.MasterDesktopInjectConfig;
import com.tokelon.toktales.tools.core.sub.inject.config.IHierarchicalInjectConfig;

public class DesktopEngineLauncher extends BaseEngineLauncher {


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

	/** Constructor with an inject config and a looper.
	 * 
	 * @param injectConfig
	 * @param looper
	 * 
	 * @see MasterDesktopInjectConfig
	 */
	public DesktopEngineLauncher(IHierarchicalInjectConfig injectConfig, IEngineLooper looper) {
		super(injectConfig, looper);
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
	public DesktopEngineLauncher(IHierarchicalInjectConfig injectConfig, ILoggerFactory loggerFactory) {
		super(injectConfig, loggerFactory);
	}

	/** Constructor with an inject config, a logger factory, and a looper.
	 * 
	 * @param injectConfig
	 * @param looper
	 * @param loggerFactory
	 * 
	 * @see MasterDesktopInjectConfig
	 */
	public DesktopEngineLauncher(IHierarchicalInjectConfig injectConfig, IEngineLooper looper, ILoggerFactory loggerFactory) {
		super(injectConfig, looper, loggerFactory);
	}
	
}
