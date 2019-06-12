package com.tokelon.toktales.core.engine;

import com.google.inject.multibindings.MapBinder;
import com.tokelon.toktales.core.engine.IEngineService.IServiceExtension;
import com.tokelon.toktales.core.engine.inject.AbstractInjectModule;
import com.tokelon.toktales.core.engine.inject.annotation.services.ContentServiceExtensions;
import com.tokelon.toktales.core.engine.inject.annotation.services.InputServiceExtensions;
import com.tokelon.toktales.core.engine.inject.annotation.services.LogServiceExtensions;
import com.tokelon.toktales.core.engine.inject.annotation.services.RenderServiceExtensions;
import com.tokelon.toktales.core.engine.inject.annotation.services.StorageServiceExtensions;
import com.tokelon.toktales.core.engine.inject.annotation.services.UIServiceExtensions;

public class ServiceExtensionsInjectModule extends AbstractInjectModule {


	@Override
	protected void configure() {
		MapBinder.newMapBinder(binder(), String.class, IServiceExtension.class, ContentServiceExtensions.class);
		MapBinder.newMapBinder(binder(), String.class, IServiceExtension.class, InputServiceExtensions.class);
		MapBinder.newMapBinder(binder(), String.class, IServiceExtension.class, LogServiceExtensions.class);
		MapBinder.newMapBinder(binder(), String.class, IServiceExtension.class, RenderServiceExtensions.class);
		MapBinder.newMapBinder(binder(), String.class, IServiceExtension.class, StorageServiceExtensions.class);
		MapBinder.newMapBinder(binder(), String.class, IServiceExtension.class, UIServiceExtensions.class);
	}
	
}
