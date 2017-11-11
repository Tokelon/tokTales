package com.tokelon.toktales.desktop.lwjgl.data;

import java.nio.ByteBuffer;

import org.lwjgl.opengl.GL11;

import com.tokelon.toktales.desktop.content.IBitmapTexture;


public class CodepointTexture implements IBitmapTexture {

	private final ByteBuffer bitmap;
	private final int width;
	private final int height;
	private final int originOffsetX;
	private final int originOffsetY;
	
	public CodepointTexture(ByteBuffer bitmap, int width, int height, int originOffsetX, int originOffsetY) {
		this.bitmap = bitmap;
		this.width = width;
		this.height = height;
		this.originOffsetX = originOffsetX;
		this.originOffsetY = originOffsetY;
	}
	

	
	public int getOriginOffsetX() {
		return originOffsetX;
	}
	
	public int getOriginOffsetY() {
		return originOffsetY;
	}

	

	@Override
	public int getWidth() {
		return width;
	}
	
	@Override
	public int getHeight() {
		return height;
	}

	@Override
	public int getInternalFormat() {
		return GL11.GL_ALPHA;
	}

	@Override
	public int getFormat() {
		return GL11.GL_ALPHA;
	}

	@Override
	public int getUnpackAlignment() {
		return 1;
	}
	
	@Override
	public ByteBuffer getData() {
		return bitmap;
	}
	
	
	// Do this how ?
	private void markFreed() {
		//bitmap = null;
	}
	
	
}
