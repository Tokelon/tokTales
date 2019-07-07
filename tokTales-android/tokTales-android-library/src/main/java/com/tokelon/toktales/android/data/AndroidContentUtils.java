package com.tokelon.toktales.android.data;

import com.tokelon.toktales.core.content.ContentUtils;
import com.tokelon.toktales.core.content.graphics.IBitmap;
import com.tokelon.toktales.core.game.model.IRectangle2i;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;

public class AndroidContentUtils extends ContentUtils {


	@Override
	public IBitmap cropBitmap(IBitmap bitmap, IRectangle2i bounds) {
		IBitmap result;
		if(bitmap instanceof IAndroidBitmap) {
			IAndroidBitmap androidWrapper = (IAndroidBitmap) bitmap;
			result = cropAndroidBitmap(androidWrapper, bounds);
		}
		else {
			result = super.cropBitmap(bitmap, bounds);
		}
		
		return result;
	}
	
	private static IAndroidBitmap cropAndroidBitmap(IAndroidBitmap bitmap, IRectangle2i bounds) {
		Bitmap androidBitmap = bitmap.getBitmap();
		
		Bitmap croppedBitmap = cropBitmapNative(androidBitmap, bounds);
		return new AndroidBitmap(croppedBitmap);
	}
	
	
	private static Bitmap cropBitmapNative(Bitmap bitmap, IRectangle2i bounds) {
		Bitmap croppedBitmap = Bitmap.createBitmap(bitmap, 0, 0, bounds.getWidth(), bounds.getHeight());
		return croppedBitmap;
	}
	
	@SuppressWarnings("unused")
	private static Bitmap cropBitmapManual(Bitmap bitmap, IRectangle2i bounds) {
		int[] pixels = new int[bounds.getWidth() * bounds.getHeight()];
		Bitmap croppedBitmap = Bitmap.createBitmap(bounds.getWidth(), bounds.getHeight(), Config.ARGB_8888);
		
		bitmap.getPixels(pixels, 0, bounds.width(),
				bounds.left(),
				bounds.top(),
				bounds.width(),
				bounds.height());

		croppedBitmap.setPixels(pixels, 0, bounds.width(),
				0,
				0,
				bounds.width(),
				bounds.height());
		
		return croppedBitmap;
	}
	
}
