package com.tokelon.toktales.core.engine.inject;

import com.tokelon.toktales.core.engine.inject.annotation.DebugInjectModuleIsDev;
import com.tokelon.toktales.core.render.DebugRenderingEnabled;
import com.tokelon.toktales.core.render.opengl.GLErrorCheckingEnabled;

public class CoreProdInjectModule extends AbstractInjectModule {

	
	@Override
	protected void configure() {
		bind(Boolean.class).annotatedWith(DebugInjectModuleIsDev.class).toInstance(false);
		bind(Boolean.class).annotatedWith(GLErrorCheckingEnabled.class).toInstance(false);
		bind(Boolean.class).annotatedWith(DebugRenderingEnabled.class).toInstance(false);
	}

}
