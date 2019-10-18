package com.tokelon.toktales.core.engine.inject;

import com.tokelon.toktales.core.content.manage.AssetManagersInjectModule;
import com.tokelon.toktales.core.content.manage.AssetReadersInjectModule;
import com.tokelon.toktales.core.engine.ServiceExtensionsInjectModule;
import com.tokelon.toktales.core.engine.log.LoggingInjectModule;
import com.tokelon.toktales.core.render.opengl.CoreGLInjectModule;
import com.tokelon.toktales.tools.core.sub.inject.config.HierarchicalInjectConfig;
import com.tokelon.toktales.tools.core.sub.inject.modules.CoreInjectToolsModule;
import com.tokelon.toktales.tools.core.sub.inject.modules.CoreToolsModule;

public class CoreInjectConfig extends HierarchicalInjectConfig {


	@Override
    public void configure() {
		super.configure();

		// Add regular modules here
		extend(new CoreInjectModule());

		extend(new CoreValuesInjectModule());

		extend(new CoreDebugInjectModule());

		extend(new ServiceExtensionsInjectModule());

		extend(new GlobalRegistriesInjectModule());

		extend(new AssetReadersInjectModule());
		extend(new AssetManagersInjectModule());

		extend(new CoreGLInjectModule());
		
		extend(new LoggingInjectModule());
		
		
		extend(new CoreToolsInjectModule());
		extend(new AssetToolsInjectModule());
		
		extend(new CoreToolsModule());
		extend(new CoreInjectToolsModule());
	}
    
}
