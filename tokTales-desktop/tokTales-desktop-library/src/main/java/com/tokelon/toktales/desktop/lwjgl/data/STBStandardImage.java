package com.tokelon.toktales.desktop.lwjgl.data;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import org.lwjgl.stb.STBImage;

import com.tokelon.toktales.desktop.lwjgl.LWJGLException;

public class STBStandardImage implements ISTBBitmap { // TODO: Rename to STBBitmap?

	
	private boolean initialized = false;
	private boolean disposed = false;
	
	private ByteBuffer data;
	private int width;
	private int height;
	private int channels;
	private int sourceChannels;
	
	protected STBStandardImage() {
		// Empty constructor
	}
	
	protected STBStandardImage(ByteBuffer data, int width, int height, int channels, int sourceChannels) {
		this.data = data;
		this.width = width;
		this.height = height;
		this.channels = channels;
		this.sourceChannels = sourceChannels;
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
			
			STBImage.stbi_image_free(data);
		}
	}
	
	

	public static STBStandardImage create(ByteBuffer data, int width, int height, int channels, int sourceChannels) {
		return new STBStandardImage(data, width, height, channels, sourceChannels);
	}
	
	public static STBStandardImage createFromBuffer(ByteBuffer buffer) throws LWJGLException {
		STBStandardImage tex = new STBStandardImage();
		initializeImage(tex, buffer);
		return tex;
	}
	
	public static void initializeImage(STBStandardImage image, ByteBuffer buffer) throws LWJGLException {
		if(image.initialized) {
			throw new IllegalStateException("Image was already initialized");
		}
		image.initialized = true;
		
		IntBuffer w = LWJGLBufferUtils.getWrapper().createIntBuffer(1);
		IntBuffer h = LWJGLBufferUtils.getWrapper().createIntBuffer(1);
		IntBuffer imageChannels = LWJGLBufferUtils.getWrapper().createIntBuffer(1);
		
		int desiredChannels = 4; // Always load as RGBA
		ByteBuffer imageBuffer = STBImage.stbi_load_from_memory(buffer, w, h, imageChannels, desiredChannels);
		if(imageBuffer == null) {
			throw new LWJGLException("Failed to load graphic: " + STBImage.stbi_failure_reason());
		}

		image.data = imageBuffer;
		image.width = w.get(0);
		image.height = h.get(0);
		image.sourceChannels = imageChannels.get(0);
		image.channels = desiredChannels;
	}
	
}
