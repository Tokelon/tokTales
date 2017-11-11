package com.tokelon.toktales.desktop.content;

import java.nio.ByteBuffer;

import com.tokelon.toktales.core.render.IRenderTexture;

public interface IBitmapTexture extends IRenderTexture {

	public int getWidth();
	public int getHeight();
	
	public int getInternalFormat();
	
	public int getFormat();
	
	public int getUnpackAlignment();
	
	public ByteBuffer getData();
	
	
	// Other params
	// Texture scaling filter
	// Texture wrap
	
}
