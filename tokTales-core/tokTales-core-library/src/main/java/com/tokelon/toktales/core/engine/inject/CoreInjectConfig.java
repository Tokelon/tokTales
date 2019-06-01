package com.tokelon.toktales.core.engine.inject;

public class CoreInjectConfig extends HierarchicalInjectConfig {

    
	@Override
    public void configure() {
		super.configure();
		
		// Add regular modules here
    	extend(new CoreInjectModule());
    }
    
}
