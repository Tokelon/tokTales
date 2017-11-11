package com.tokelon.toktales.desktop.lwjgl.data;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import org.lwjgl.stb.STBImage;

import com.tokelon.toktales.desktop.lwjgl.LWJGLException;
import com.tokelon.toktales.desktop.lwjgl.render.IImageTexture;

public class STBStandardImage implements IImageTexture {
	
	/* TODO: This shouldn't really implement IImageTexture !
	 * Did this as a quick fix so we do not need a wrapper on each call
	 * 
	 */
	
	
	private ByteBuffer data;
	private int width;
	private int height;
	private int numComp;
	
	
	private STBStandardImage() {
		// Empty constructor
	}
	
	// Make this public?
	private STBStandardImage(ByteBuffer data, int width, int height, int numComp) {
		this.data = data;
		this.width = width;
		this.height = height;
		this.numComp = numComp;
	}
	
	
	public void initializeImage(ByteBuffer buffer) throws LWJGLException {
		
		IntBuffer w = LWJGLBufferUtils.getWrapper().createIntBuffer(1);
		IntBuffer h = LWJGLBufferUtils.getWrapper().createIntBuffer(1);
		IntBuffer comp = LWJGLBufferUtils.getWrapper().createIntBuffer(1);
		
		ByteBuffer image = STBImage.stbi_load_from_memory(buffer, w, h, comp, 4);
		if(image == null) {
			throw new LWJGLException("Failed to load graphic: " +STBImage.stbi_failure_reason());
		}

		this.data = image;
		this.width = w.get(0);
		this.height = h.get(0);
		this.numComp = comp.get(0);
	}
	
	
	public ByteBuffer getData() {
		return data;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public int getNumComponents() {
		return numComp;
	}
	
	
	public void free() {
		STBImage.stbi_image_free(data);
	}

	
	
	public static STBStandardImage initFrom(ByteBuffer buffer) throws LWJGLException {
		STBStandardImage tex = new STBStandardImage();
		tex.initializeImage(buffer);
		return tex;
	}
	
	public static STBStandardImage create(ByteBuffer data, int width, int height, int numComp) {
		return new STBStandardImage(data, width, height, numComp);
	}
	
	
	@Override
	public STBStandardImage getImage() {
		return this;
	}
	
}
