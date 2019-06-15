package com.tokelon.toktales.core.content.manage;

import java.util.Map;

import javax.inject.Inject;
import javax.inject.Provider;

import com.tokelon.toktales.core.engine.inject.RegistryChildProvider;
import com.tokelon.toktales.core.engine.inject.annotation.GlobalAssetKeyAliases;
import com.tokelon.toktales.core.engine.inject.annotation.GlobalAssetKeyEntries;
import com.tokelon.toktales.core.engine.inject.annotation.GlobalRegistry;
import com.tokelon.toktales.tools.registry.IBasicRegistry;

public class GlobalAssetKeyChildRegistryProvider extends RegistryChildProvider implements Provider<IBasicRegistry> {


	@Inject
	public GlobalAssetKeyChildRegistryProvider(
			@GlobalRegistry IBasicRegistry parentRegistry,
			@GlobalAssetKeyEntries Map<Object, Object> globalKeys,
			@GlobalAssetKeyAliases Map<Object, Object> globalKeyAliases
	) {
		super(parentRegistry, globalKeys, globalKeyAliases);
	}

}
