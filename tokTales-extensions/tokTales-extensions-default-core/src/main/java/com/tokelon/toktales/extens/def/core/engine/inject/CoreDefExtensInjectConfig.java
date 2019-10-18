package com.tokelon.toktales.extens.def.core.engine.inject;

import com.tokelon.toktales.tools.core.sub.inject.config.HierarchicalInjectConfig;

public class CoreDefExtensInjectConfig extends HierarchicalInjectConfig {


    @Override
    protected void configure() {
    	super.configure();
    	
    	extend(new CoreDefExtensInjectModule());
    	
    	extend(new CoreDefExtensGlobalRegistriesInjectModule());
    }
    
}
