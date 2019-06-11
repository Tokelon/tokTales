package com.tokelon.toktales.core.render.opengl;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;
import com.tokelon.toktales.core.render.ITextureDriver;
import com.tokelon.toktales.core.render.opengl.gl20.GLErrorUtils;
import com.tokelon.toktales.core.render.opengl.gl20.GLTextureDriver;
import com.tokelon.toktales.core.render.opengl.gl20.facade.GLFactory;
import com.tokelon.toktales.core.render.opengl.gl20.facade.GLProgram;
import com.tokelon.toktales.core.render.opengl.gl20.facade.GLShader;
import com.tokelon.toktales.core.render.opengl.gl20.facade.IGLFactory;
import com.tokelon.toktales.core.render.opengl.gl20.facade.IGLProgram;
import com.tokelon.toktales.core.render.opengl.gl20.facade.IGLShader;

public class CoreGLInjectModule extends AbstractModule {


	@Override
	protected void configure() {
		// GL Stuff
		bind(IGLErrorUtils.class).to(GLErrorUtils.class); // Could bind this to no-op implementation
		bind(IGLBufferUtils.class).to(GLBufferUtils.class).in(Scopes.SINGLETON);
		bind(IGLFactory.class).to(GLFactory.class);
		bind(IGLProgram.class).to(GLProgram.class);
		bind(IGLShader.class).to(GLShader.class);
		bind(ITextureDriver.class).to(GLTextureDriver.class);
	}

}
