package com.tokelon.toktales.extens.def.desktop.desktop.game.screen;

import org.lwjgl.opengl.GL11;

import com.tokelon.toktales.core.game.screen.IModularStateRender;
import com.tokelon.toktales.extens.def.core.game.screen.ConsoleRenderingStrategy;

public class DesktopConsoleRenderingStrategy extends ConsoleRenderingStrategy {

	
	@Override
	public void renderFrame(IModularStateRender baseRenderer) {
		
		GL11.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

		
		super.renderFrame(baseRenderer);
	}
	
}
