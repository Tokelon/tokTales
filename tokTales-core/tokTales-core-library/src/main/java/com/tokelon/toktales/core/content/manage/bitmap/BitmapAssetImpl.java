package com.tokelon.toktales.core.content.manage.bitmap;

import com.tokelon.toktales.core.content.graphics.IBitmap;

public class BitmapAssetImpl implements IBitmapAsset {

	
	private final IBitmap bitmap;

	public BitmapAssetImpl(IBitmap bitmap) {
		this.bitmap = bitmap;
	}

	
	@Override
	public IBitmap getBitmap() {
		return bitmap;
	}
	
	@Override
	public void dispose() {
		bitmap.dispose();
	}

}
