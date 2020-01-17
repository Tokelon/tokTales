package com.tokelon.toktales.extensions.android.engine.inject;

import com.tokelon.toktales.tools.core.sub.inject.config.HierarchicalInjectConfig;

public class AndroidDefExtensInjectConfig extends HierarchicalInjectConfig {


    @Override
    protected void configure() {
    	super.configure();
    	
    	extend(new AndroidDefExtensInjectModule());
    }
    
}
