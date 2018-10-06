package com.tokelon.toktales.core.engine.inject;

import com.google.inject.Scopes;
import com.google.inject.TypeLiteral;
import com.google.inject.matcher.Matchers;
import com.google.inject.spi.InjectionListener;
import com.google.inject.spi.TypeEncounter;
import com.google.inject.spi.TypeListener;
import com.tokelon.toktales.core.render.DebugRenderingEnabled;
import com.tokelon.toktales.core.render.opengl.GLErrorCheckingEnabled;

public class CoreDevInjectModule extends AbstractInjectModule {

	
	@Override
	protected void configure() {
		bind(Boolean.class).annotatedWith(DebugInjectModuleIsDev.class).toInstance(true);
		bind(Boolean.class).annotatedWith(GLErrorCheckingEnabled.class).toInstance(true);
		bind(Boolean.class).annotatedWith(DebugRenderingEnabled.class).toInstance(true);
		
		// find circular proxies
		printOutCircularProxies();
	}
	

	private void printOutCircularProxies() {
		bindListener(Matchers.any(), new TypeListener() {
			@Override
			public <I> void hear(TypeLiteral<I> typeLiteral, TypeEncounter<I> typeEncounter) {
				typeEncounter.register((InjectionListener<I>) target -> {
					if (Scopes.isCircularProxy(target)) {
						System.out.println("CP: " + typeLiteral.getRawType().getName());
					}
				});
			}
		});
	}

}
