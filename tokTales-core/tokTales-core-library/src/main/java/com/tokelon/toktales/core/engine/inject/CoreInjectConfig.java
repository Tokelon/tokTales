package com.tokelon.toktales.core.engine.inject;

import com.tokelon.toktales.core.content.manage.files.AssetReadersInjectModule;
import com.tokelon.toktales.core.engine.ServiceExtensionsInjectModule;
import com.tokelon.toktales.core.render.opengl.CoreGLInjectModule;

public class CoreInjectConfig extends HierarchicalInjectConfig {


	@Override
    public void configure() {
		super.configure();
		
		// Add regular modules here
    	extend(new CoreInjectModule());
    	
    	extend(new CoreDebugInjectModule());
    	
    	extend(new ServiceExtensionsInjectModule());
    	
    	extend(new AssetReadersInjectModule());
    	
    	extend(new CoreGLInjectModule());
    }
    
}
