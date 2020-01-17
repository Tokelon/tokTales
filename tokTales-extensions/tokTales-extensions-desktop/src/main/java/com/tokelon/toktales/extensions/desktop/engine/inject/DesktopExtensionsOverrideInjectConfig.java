package com.tokelon.toktales.extensions.desktop.engine.inject;

import com.tokelon.toktales.tools.core.sub.inject.config.HierarchicalInjectConfig;

public class DesktopExtensionsOverrideInjectConfig extends HierarchicalInjectConfig {

	
	@Override
	protected void configure() {
		super.configure();
		
		extend(new DesktopExtensionsOverrideInjectModule());
	}
	
}
