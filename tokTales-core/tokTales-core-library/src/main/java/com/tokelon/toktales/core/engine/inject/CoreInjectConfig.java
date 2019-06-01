package com.tokelon.toktales.core.engine.inject;

import com.tokelon.toktales.core.content.manage.files.AssetReadersInjectModule;

public class CoreInjectConfig extends HierarchicalInjectConfig {

    
	@Override
    public void configure() {
		super.configure();
		
		// Add regular modules here
    	extend(new CoreInjectModule());
    	extend(new AssetReadersInjectModule());
    }
    
}
