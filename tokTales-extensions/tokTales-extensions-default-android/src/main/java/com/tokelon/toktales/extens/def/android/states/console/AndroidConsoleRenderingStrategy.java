package com.tokelon.toktales.extens.def.android.states.console;

import android.opengl.GLES20;

import com.tokelon.toktales.core.game.screen.IModularStateRender;
import com.tokelon.toktales.extens.def.core.game.states.console.ConsoleRenderingStrategy;

public class AndroidConsoleRenderingStrategy extends ConsoleRenderingStrategy {

	
	@Override
	public void renderFrame(IModularStateRender baseRenderer) {
		
		// TODO: Do conditional clear or cap frames at 30 ?
		
		GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);
		
		super.renderFrame(baseRenderer);
		
	}
	
}
