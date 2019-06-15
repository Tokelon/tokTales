package com.tokelon.toktales.core.engine.inject;

import com.google.inject.multibindings.MapBinder;
import com.tokelon.toktales.core.content.manage.GlobalAssetKeyChildRegistryProvider;
import com.tokelon.toktales.core.engine.inject.annotation.GlobalAssetKeyAliases;
import com.tokelon.toktales.core.engine.inject.annotation.GlobalAssetKeyEntries;
import com.tokelon.toktales.core.engine.inject.annotation.GlobalAssetKeyRegistry;
import com.tokelon.toktales.core.engine.inject.annotation.GlobalRegistry;
import com.tokelon.toktales.core.engine.inject.annotation.GlobalRegistryAliases;
import com.tokelon.toktales.core.engine.inject.annotation.GlobalRegistryEntries;
import com.tokelon.toktales.tools.registry.IBasicRegistry;

public class GlobalRegistriesInjectModule extends AbstractInjectModule {


	@Override
	protected void configure() {
		bind(IBasicRegistry.class).annotatedWith(GlobalRegistry.class).toProvider(GlobalRegistryProvider.class).in(GameScoped.class);
		MapBinder.newMapBinder(binder(), Object.class, Object.class, GlobalRegistryEntries.class);
		MapBinder.newMapBinder(binder(), Object.class, Object.class, GlobalRegistryAliases.class);
		
		bind(IBasicRegistry.class).annotatedWith(GlobalAssetKeyRegistry.class).toProvider(GlobalAssetKeyChildRegistryProvider.class).in(GameScoped.class);
		MapBinder.newMapBinder(binder(), Object.class, Object.class, GlobalAssetKeyEntries.class);
		MapBinder.newMapBinder(binder(), Object.class, Object.class, GlobalAssetKeyAliases.class);
	}
	
}
