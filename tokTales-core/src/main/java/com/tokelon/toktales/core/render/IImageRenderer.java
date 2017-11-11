package com.tokelon.toktales.core.render;


public interface IImageRenderer extends IChunkRenderer {

	
	//public void drawImage(IRenderTexture renderTexture, WRectangle target);
	
	public void drawImage(IRenderTexture renderTexture, float x1, float y1, float x2, float y2, float x3, float y3, float x4, float y4);
	
	
	
}
