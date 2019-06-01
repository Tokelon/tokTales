package com.tokelon.toktales.extens.def.core.engine.inject;

import com.tokelon.toktales.core.engine.inject.HierarchicalInjectConfig;

public class CoreDefExtensInjectConfig extends HierarchicalInjectConfig {


    @Override
    protected void configure() {
    	super.configure();
    	
    	extend(new CoreDefExtensInjectModule());
    }
    
}
