package com.tokelon.toktales.desktop.engine.inject;

import com.google.inject.Scopes;
import com.tokelon.toktales.core.engine.inject.AbstractInjectModule;
import com.tokelon.toktales.core.game.states.IGameStateControl;
import com.tokelon.toktales.core.render.opengl.IGLBufferUtils;
import com.tokelon.toktales.desktop.game.states.DesktopGameStateControl;
import com.tokelon.toktales.desktop.render.opengl.DesktopGLBufferUtils;

public class DesktopOverrideInjectModule extends AbstractInjectModule {

	
	@Override
	protected void configure() {
		// Game bindings
		bindInGameScopeAndForNotScoped(IGameStateControl.class, DesktopGameStateControl.class);
		
		bind(IGLBufferUtils.class).to(DesktopGLBufferUtils.class).in(Scopes.SINGLETON);
	}

}
