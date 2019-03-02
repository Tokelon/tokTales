package com.tokelon.toktales.android.data;

import com.tokelon.toktales.core.content.graphics.IBitmap;

import android.graphics.Bitmap;

/** Wraps around an Android Bitmap.
 */
public interface IAndroidBitmap extends IBitmap {

	
	/**
	 * @return The Android Bitmap.
	 */
	public Bitmap getBitmap();
	
}
