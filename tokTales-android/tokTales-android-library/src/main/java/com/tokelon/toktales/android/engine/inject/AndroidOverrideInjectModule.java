package com.tokelon.toktales.android.engine.inject;

import com.tokelon.toktales.android.render.opengl.gl20.AndroidGLTextureDriver;
import com.tokelon.toktales.android.states.AndroidGameStateManager;
import com.tokelon.toktales.core.engine.inject.AbstractInjectModule;
import com.tokelon.toktales.core.game.states.IGameStateControl;
import com.tokelon.toktales.core.render.ITextureDriver;

public class AndroidOverrideInjectModule extends AbstractInjectModule {

	
	@Override
	protected void configure() {
		// Game bindings
		bindInGameScopeAndForNotScoped(IGameStateControl.class, AndroidGameStateManager.class);
		
		bind(ITextureDriver.class).to(AndroidGLTextureDriver.class);
	}

}
