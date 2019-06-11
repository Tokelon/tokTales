package com.tokelon.toktales.desktop.engine.inject;

import com.tokelon.toktales.core.content.manage.files.PathAssetReadersInjectModule;
import com.tokelon.toktales.core.engine.inject.HierarchicalInjectConfig;
import com.tokelon.toktales.desktop.content.DesktopParentIdentifiersInjectModule;
import com.tokelon.toktales.desktop.lwjgl.LWJGLInjectModule;
import com.tokelon.toktales.desktop.lwjgl.render.LWJGLRenderDriversInjectModule;

public class DesktopInjectConfig extends HierarchicalInjectConfig {

    
    @Override
    public void configure() {
    	super.configure();
    	
    	extend(new DesktopInjectModule());
    	
    	extend(new DesktopParentIdentifiersInjectModule());
    	extend(new PathAssetReadersInjectModule());
    	
    	extend(new LWJGLInjectModule());
    	extend(new LWJGLRenderDriversInjectModule());
    }
    
}
