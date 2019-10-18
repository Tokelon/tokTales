package com.tokelon.toktales.tools.core.sub.inject.modules;

import com.google.inject.AbstractModule;
import com.tokelon.toktales.tools.core.inject.parameter.IParameterInjector.IParameterInjectorFactory;
import com.tokelon.toktales.tools.core.inject.parameter.ParameterInjectorFactory;
import com.tokelon.toktales.tools.core.objects.pools.DefaultObjectPool;
import com.tokelon.toktales.tools.core.objects.pools.IObjectPool.IObjectPoolFactory;

public class CoreToolsModule extends AbstractModule {


	@Override
	protected void configure() {
		bind(IParameterInjectorFactory.class).to(ParameterInjectorFactory.class);
		
		bind(IObjectPoolFactory.class).to(DefaultObjectPool.DefaultObjectPoolFactory.class);
	}
	
}
