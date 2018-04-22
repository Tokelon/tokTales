package com.tokelon.toktales.core.content;

import java.nio.ByteBuffer;

public class BitmapImpl implements IBitmap {

	
	private final ByteBuffer data;
	private final int width;
	private final int height;
	private final int format;
	
	public BitmapImpl(ByteBuffer data, int width, int height, int format) {
		this.data = data;
		this.width = width;
		this.height = height;
		this.format = format;
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
	public int getFormat() {
		return format;
	}

	@Override
	public ByteBuffer getData() {
		return data;
	}

	@Override
	public void getData(ByteBuffer buffer) {
		data.position(0);
		buffer.put(data);
		data.position(0);
	}
	
	@Override
	public int getByteCount() {
		return data.capacity();
	}

	
	@Override
	public void dispose() {
		// Free or null the bitmap?
	}

}
