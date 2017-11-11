package com.tokelon.toktales.android.render.opengl;

import android.graphics.Bitmap;

public class AndroidBitmapTexture implements IAndroidBitmapTexture {

	
	private final Bitmap bitmap;
	
	private int unpackAlignmentValue = 4;	// Default of 4 bytes
	
	public AndroidBitmapTexture(Bitmap bitmap) {
		this.bitmap = bitmap;
	}
	
	
	public void setUnpackAlignment(int value) {
		this.unpackAlignmentValue = value;
	}
	
	@Override
	public Bitmap getBitmap() {
		return bitmap;
	}
	
	@Override
	public int getUnpackAlignment() {
		return unpackAlignmentValue;
	}

	
}
