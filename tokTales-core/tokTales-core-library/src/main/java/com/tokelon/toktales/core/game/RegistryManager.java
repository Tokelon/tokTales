package com.tokelon.toktales.core.game;

import javax.inject.Inject;

import com.tokelon.toktales.core.engine.inject.annotation.GlobalAssetKeyRegistry;
import com.tokelon.toktales.core.engine.inject.annotation.GlobalRegistry;
import com.tokelon.toktales.tools.registry.IBasicRegistry;

public class RegistryManager implements IRegistryManager {


	private final IBasicRegistry globalRegistry;
	private final IBasicRegistry globalAssetKeyRegistry;

	@Inject
	public RegistryManager(@GlobalRegistry IBasicRegistry globalRegistry, @GlobalAssetKeyRegistry IBasicRegistry globalAssetKeyRegistry) {
		this.globalRegistry = globalRegistry;
		this.globalAssetKeyRegistry = globalAssetKeyRegistry;
	}
	
	
	@Override
	public IBasicRegistry getRegistry() {
		return globalRegistry;
	}

	@Override
	public IBasicRegistry getAssetKeyRegistry() {
		return globalAssetKeyRegistry;
	}

}
