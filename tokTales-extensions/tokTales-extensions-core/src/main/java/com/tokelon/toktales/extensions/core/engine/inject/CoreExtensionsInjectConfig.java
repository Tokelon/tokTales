package com.tokelon.toktales.extensions.core.engine.inject;

import com.tokelon.toktales.tools.core.sub.inject.config.HierarchicalInjectConfig;

public class CoreExtensionsInjectConfig extends HierarchicalInjectConfig {


    @Override
    protected void configure() {
    	super.configure();
    	
    	extend(new CoreExtensionsInjectModule());
    	
    	extend(new CoreExtensionsGlobalRegistriesInjectModule());
    }
    
}
