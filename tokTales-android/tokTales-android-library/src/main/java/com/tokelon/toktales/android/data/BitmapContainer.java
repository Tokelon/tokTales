package com.tokelon.toktales.android.data;

import com.tokelon.toktales.core.content.IAssetContainer;

import android.graphics.Bitmap;

public class BitmapContainer implements IAssetContainer<Bitmap> {

	private final Bitmap mBitmapAsset;
	
	public BitmapContainer(Bitmap bitmapAsset) {
		mBitmapAsset = bitmapAsset;
	}

	
	@Override
	public Bitmap getAsset() {
		return mBitmapAsset;
	}

	@Override
	public void freeAsset() {
		mBitmapAsset.recycle();		// Delete reference to bitmap as well ?
	}


}
