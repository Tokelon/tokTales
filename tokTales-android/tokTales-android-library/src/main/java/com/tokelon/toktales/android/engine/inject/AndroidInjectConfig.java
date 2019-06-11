package com.tokelon.toktales.android.engine.inject;

import com.tokelon.toktales.android.data.AndroidParentIdentifiersInjectModule;
import com.tokelon.toktales.android.render.opengl.AndroidRenderDriversInjectModule;
import com.tokelon.toktales.core.engine.inject.HierarchicalInjectConfig;

public class AndroidInjectConfig extends HierarchicalInjectConfig {


    @Override
    protected void configure() {
    	super.configure();
    	
        extend(new AndroidInjectModule());
        
        extend(new AndroidParentIdentifiersInjectModule());
        
        extend(new AndroidRenderDriversInjectModule());
    }
    
}
