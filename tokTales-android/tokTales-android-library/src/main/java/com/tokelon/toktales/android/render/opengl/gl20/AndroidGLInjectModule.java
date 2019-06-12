package com.tokelon.toktales.android.render.opengl.gl20;

import com.google.inject.Scopes;
import com.tokelon.toktales.core.engine.inject.AbstractInjectModule;
import com.tokelon.toktales.core.render.opengl.gl20.IGL11;
import com.tokelon.toktales.core.render.opengl.gl20.IGL13;
import com.tokelon.toktales.core.render.opengl.gl20.IGL14;
import com.tokelon.toktales.core.render.opengl.gl20.IGL15;
import com.tokelon.toktales.core.render.opengl.gl20.IGL20;

public class AndroidGLInjectModule extends AbstractInjectModule {


	@Override
	protected void configure() {
		bind(IGL11.class).to(AndroidGL11.class).in(Scopes.SINGLETON);
		bind(IGL13.class).to(AndroidGL13.class).in(Scopes.SINGLETON);
		bind(IGL14.class).to(AndroidGL14.class).in(Scopes.SINGLETON);
		bind(IGL15.class).to(AndroidGL15.class).in(Scopes.SINGLETON);
		bind(IGL20.class).to(AndroidGL20.class).in(Scopes.SINGLETON);
	}
	
}
