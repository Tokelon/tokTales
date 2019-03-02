package com.tokelon.toktales.android.data;

import com.tokelon.toktales.core.content.graphics.IBitmap;

import android.graphics.Bitmap;

public class AndroidCodepointBitmap extends AndroidBitmap {

	
	public AndroidCodepointBitmap(Bitmap bitmap) {
		super(bitmap);
	}
	
	
	@Override
	public int getFormat() {
		return IBitmap.FORMAT_ALPHA_8;
	}

}
