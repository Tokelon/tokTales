package com.tokelon.toktales.core.content.manage;

import java.util.Map;

import javax.inject.Inject;
import javax.inject.Provider;

import com.tokelon.toktales.core.engine.inject.RegistryProvider;
import com.tokelon.toktales.core.engine.inject.annotation.GlobalAssetKeyAliases;
import com.tokelon.toktales.core.engine.inject.annotation.GlobalAssetKeyEntries;
import com.tokelon.toktales.tools.registry.IBasicRegistry;

public class GlobalAssetKeyRegistryProvider extends RegistryProvider implements Provider<IBasicRegistry> {


	@Inject
	public GlobalAssetKeyRegistryProvider(
			@GlobalAssetKeyEntries Map<Object, Object> globalKeys,
			@GlobalAssetKeyAliases Map<Object, Object> globalKeyAliases
	) {
		super(globalKeys, globalKeyAliases);
	}

}
