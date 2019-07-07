package com.tokelon.toktales.desktop.lwjgl.data;

import java.nio.ByteBuffer;

import com.tokelon.toktales.core.content.IDisposer;

public class STBBitmap implements ISTBBitmap {


	private boolean disposed = false;
	
	private ByteBuffer data;
	private final int width;
	private final int height;
	private final int channels;
	private final int sourceChannels;
	private final IDisposer<STBBitmap> disposer;
	
	public STBBitmap(ByteBuffer data, int width, int height, int channels, int sourceChannels, IDisposer<STBBitmap> disposer) {
		this.data = data;
		this.width = width;
		this.height = height;
		this.channels = channels;
		this.sourceChannels = sourceChannels;
		this.disposer = disposer;
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
		switch (getChannels()) {
		case 1:
			return FORMAT_ALPHA_8;
		case 2:
			return FORMAT_GREY_ALPHA_88;
		case 3:
			return FORMAT_RGB_888;
		case 4:
			return FORMAT_RGBA_8888;
		default:
			return FORMAT_UNKNOWN;
		}
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

	
	/** Returns the number of channels of this image.
	 * 
	 * @return The number of channels [1-4].
	 */
	@Override
	public int getChannels() {
		return channels;
	}

	/** Returns the number of channels of the source that was loaded.
	 * 
	 * @return The number of channels [1-4], or 0 if not specified.
	 */
	@Override
	public int getSourceChannels() {
		return sourceChannels;
	}
	
	@Override
	public void dispose() {
		if(!disposed) {
			disposed = true;
			
			disposer.dispose(this);
			
			data = null;
		}
	}
	
}
