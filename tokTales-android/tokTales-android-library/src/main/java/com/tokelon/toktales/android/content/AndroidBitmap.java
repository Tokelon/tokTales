package com.tokelon.toktales.android.content;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import com.tokelon.toktales.core.content.graphics.IBitmap;

import android.graphics.Bitmap;

public class AndroidBitmap implements IAndroidBitmap {

	
	private boolean disposed = false;

	private ByteBuffer dataBuffer;
	
	private final Bitmap bitmap;

	public AndroidBitmap(Bitmap bitmap) {
		this.bitmap = bitmap;
	}
	

	@Override
	public Bitmap getBitmap() {
		return bitmap;
	}

	
	@Override
	public int getWidth() {
		return bitmap.getWidth();
	}

	@Override
	public int getHeight() {
		return bitmap.getHeight();
	}

	@Override
	public int getFormat() {
		switch (bitmap.getConfig()) {
		case ALPHA_8:
			return IBitmap.FORMAT_ALPHA_8;
		case ARGB_8888:
			return IBitmap.FORMAT_RGBA_8888;
		default:
			return IBitmap.FORMAT_OTHER;
		}
	}


	@Override
	public synchronized ByteBuffer getData() {
		if(dataBuffer == null) {
			dataBuffer = createBuffer();
		}
		
		return dataBuffer;
	}
	
	private ByteBuffer createBuffer() {
		//int capacity = getBytesPerPixel() * bitmap.getWidth() * bitmap.getHeight();
		int capacity = bitmap.getByteCount();
		ByteBuffer buffer = ByteBuffer.allocateDirect(capacity).order(ByteOrder.nativeOrder());

		bitmap.copyPixelsToBuffer(buffer);
		buffer.position(0);

		return buffer;
	}

	
	@Override
	public void getData(ByteBuffer buffer) {
		bitmap.copyPixelsToBuffer(buffer);
	}
	
	
	@Override
	public int getByteCount() {
		return bitmap.getByteCount();
	}
	
	private int getBytesPerPixel() {
		switch (bitmap.getConfig()) {
		case ALPHA_8:
			return 1;
		case RGB_565:
			return 2;
		case ARGB_8888:
			return 4;
		case RGBA_F16:
			return 8;
		default:
			return 0;
		}
	}
	
	
	@Override
	public void dispose() {
		if(!disposed) {
			disposed = true;

			bitmap.recycle();
			dataBuffer = null;	
		}
	}

}
