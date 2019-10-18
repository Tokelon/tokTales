package com.tokelon.toktales.tools.core.sub.inject.modules;

import com.google.inject.AbstractModule;
import com.tokelon.toktales.tools.core.inject.IInjector;
import com.tokelon.toktales.tools.core.sub.inject.guice.GuiceInjector;

public class CoreInjectToolsModule extends AbstractModule {


	@Override
	protected void configure() {
		bind(IInjector.class).to(GuiceInjector.class);
	}
	
}
