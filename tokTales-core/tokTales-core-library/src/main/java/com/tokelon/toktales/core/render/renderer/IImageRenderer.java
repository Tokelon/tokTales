package com.tokelon.toktales.core.render.renderer;

import com.tokelon.toktales.core.render.texture.ITexture;

public interface IImageRenderer extends IBatchRenderer {


	//public void drawImage(IRenderTexture renderTexture, WRectangle target);
	
	public void drawImage(ITexture renderTexture, float x1, float y1, float x2, float y2, float x3, float y3, float x4, float y4);
	
}
