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

	/** Constructor with a logger factory and and inject config.
	 * <p>
	 * A no-op looper will be used.
	 * 
	 * @param loggerFactory
	 * @param injectConfig
	 * 
	 * @see MasterDesktopInjectConfig
	 */
	public DesktopEngineLauncher(ILoggerFactory loggerFactory, IHierarchicalInjectConfig injectConfig) {
		super(loggerFactory, injectConfig);
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

	/** Constructor with a logger factory, an inject config and a looper.
	 * 
	 * @param loggerFactory
	 * @param injectConfig
	 * @param looper
	 * 
	 * @see MasterDesktopInjectConfig
	 */
	public DesktopEngineLauncher(ILoggerFactory loggerFactory, IHierarchicalInjectConfig injectConfig, IEngineLooper looper) {
		super(loggerFactory, injectConfig, looper);
	}
	
}
