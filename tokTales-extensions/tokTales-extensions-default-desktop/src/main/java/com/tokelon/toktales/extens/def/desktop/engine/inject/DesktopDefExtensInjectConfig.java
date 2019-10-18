package com.tokelon.toktales.extens.def.desktop.engine.inject;

import com.tokelon.toktales.tools.core.sub.inject.config.HierarchicalInjectConfig;

public class DesktopDefExtensInjectConfig extends HierarchicalInjectConfig {


	@Override
    protected void configure() {
    	super.configure();
    	
    	extend(new DesktopDefExtensInjectModule());
    }
    
}
