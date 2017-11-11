package com.tokelon.toktales.desktop.lwjgl.render;

import com.tokelon.toktales.core.render.IRenderTexture;
import com.tokelon.toktales.desktop.lwjgl.data.STBStandardImage;

public interface IImageTexture extends IRenderTexture {

	public STBStandardImage getImage();
	
}
