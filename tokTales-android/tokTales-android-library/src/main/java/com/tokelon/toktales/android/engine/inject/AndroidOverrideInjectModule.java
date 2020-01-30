package com.tokelon.toktales.android.engine.inject;

import com.tokelon.toktales.android.game.state.AndroidGameStateControl;
import com.tokelon.toktales.android.render.opengl.gl20.AndroidGLTextureDriver;
import com.tokelon.toktales.core.engine.inject.AbstractInjectModule;
import com.tokelon.toktales.core.game.state.IGameStateControl;
import com.tokelon.toktales.core.render.texture.ITextureDriver;

public class AndroidOverrideInjectModule extends AbstractInjectModule {

	
	@Override
	protected void configure() {
		// Game bindings
		bindInGameScopeAndForNotScoped(IGameStateControl.class, AndroidGameStateControl.class);
		
		bind(ITextureDriver.class).to(AndroidGLTextureDriver.class);
	}

}
