package com.tokelon.toktales.desktop.render.opengl.gl20;

import com.google.inject.Scopes;
import com.tokelon.toktales.core.engine.inject.AbstractInjectModule;
import com.tokelon.toktales.core.render.opengl.gl20.IGL11;
import com.tokelon.toktales.core.render.opengl.gl20.IGL13;
import com.tokelon.toktales.core.render.opengl.gl20.IGL14;
import com.tokelon.toktales.core.render.opengl.gl20.IGL15;
import com.tokelon.toktales.core.render.opengl.gl20.IGL20;

public class DesktopGLInjectModule extends AbstractInjectModule {
	// TODO: Move all these types into LWJGL package
	
	
	@Override
	protected void configure() {
		bind(IGL11.class).to(DesktopGL11.class).in(Scopes.SINGLETON);
		bind(IGL13.class).to(DesktopGL13.class).in(Scopes.SINGLETON);
		bind(IGL14.class).to(DesktopGL14.class).in(Scopes.SINGLETON);
		bind(IGL15.class).to(DesktopGL15.class).in(Scopes.SINGLETON);
		bind(IGL20.class).to(DesktopGL20.class).in(Scopes.SINGLETON);
	}
	
}
