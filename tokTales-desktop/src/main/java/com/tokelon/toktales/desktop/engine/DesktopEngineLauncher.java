package com.tokelon.toktales.desktop.engine;

import com.tokelon.toktales.core.engine.BaseEngineLauncher;
import com.tokelon.toktales.core.engine.IEngineLooper;
import com.tokelon.toktales.core.engine.inject.IHierarchicalInjectConfig;

public class DesktopEngineLauncher extends BaseEngineLauncher {

	
	/** Constructor with an inject config.
	 * <p>
	 * A no-op looper will be used.
	 * 
	 * @param injectConfig
	 */
	protected DesktopEngineLauncher(IHierarchicalInjectConfig injectConfig) {
		super(injectConfig);
	}
	
	/** Constructor with an inject config and a looper.
	 * 
	 * @param injectConfig
	 * @param looper
	 */
	public DesktopEngineLauncher(IHierarchicalInjectConfig injectConfig, IEngineLooper looper) {
		super(injectConfig, looper);
	}
	
}
