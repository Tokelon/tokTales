package com.tokelon.toktales.core.engine.inject;

import com.tokelon.toktales.core.content.manage.AssetManagersInjectModule;
import com.tokelon.toktales.core.content.manage.files.AssetReadersInjectModule;
import com.tokelon.toktales.core.engine.ServiceExtensionsInjectModule;
import com.tokelon.toktales.core.engine.log.SLF4JLoggingInjectModule;
import com.tokelon.toktales.core.render.opengl.CoreGLInjectModule;

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
		
		extend(new SLF4JLoggingInjectModule());
	}
    
}
