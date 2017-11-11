package com.tokelon.toktales.android.render.opengl;

import com.tokelon.toktales.core.render.IRenderTexture;

import android.graphics.Bitmap;

public interface IAndroidBitmapTexture extends IRenderTexture {

	public Bitmap getBitmap();
	
	public int getUnpackAlignment();
	
}
