package com.tokelon.toktales.desktop.lwjgl.render;

import org.lwjgl.opengl.GL11;

import com.tokelon.toktales.core.content.IRGBAColor;
import com.tokelon.toktales.core.render.IRenderToolkit;
import com.tokelon.toktales.core.util.IParams;

public class DesktopRenderToolkit implements IRenderToolkit {

	@Override
	public void clearSurface(IRGBAColor clearColor) {
		GL11.glClearColor(clearColor.getRed(), clearColor.getGreen(), clearColor.getBlue(), clearColor.getAlpha());
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
	}

	
	public static class DesktopRenderToolkitFactory implements IRenderToolkitFactory {

		@Override
		public IRenderToolkit newRenderToolkit(IParams params) {
			return new DesktopRenderToolkit();
		}
	}
	
}
