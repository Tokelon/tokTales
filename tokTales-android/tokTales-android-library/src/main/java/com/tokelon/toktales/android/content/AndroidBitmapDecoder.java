package com.tokelon.toktales.android.content;

import java.io.InputStream;

import com.tokelon.toktales.core.content.manage.bitmap.BitmapAssetImpl;
import com.tokelon.toktales.core.content.manage.bitmap.IBitmapAsset;
import com.tokelon.toktales.core.content.manage.bitmap.IBitmapAssetDecoder;
import com.tokelon.toktales.core.content.manage.bitmap.IBitmapAssetKey;
import com.tokelon.toktales.core.engine.content.IGraphicLoadingOptions;
import com.tokelon.toktales.tools.assets.exception.AssetException;
import com.tokelon.toktales.tools.core.objects.options.IOptions;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;

public class AndroidBitmapDecoder implements IBitmapAssetDecoder {

	
	@Override
	public IBitmapAsset decode(InputStream inputstream, IBitmapAssetKey key, IOptions options) throws AssetException {
		Bitmap bitmap = BitmapFactory.decodeStream(inputstream);
		if(bitmap == null) {
			throw new AssetException("Bitmap could not be created for key: " + key);
		}
		
		if(options != null) {
			bitmap = postEditBitmap(bitmap, options);	
		}
		
		return new BitmapAssetImpl(new AndroidBitmap(bitmap));
	}
	
	
	// Maybe convert to BitmapPostEditor and pass in options
	protected Bitmap postEditBitmap(Bitmap original, IOptions options) throws AssetException {
		IGraphicLoadingOptions graphicLoadingOptions = options.getAs(OPTION_GRAPHIC_LOADING, IGraphicLoadingOptions.class);
		if(graphicLoadingOptions == null) {
			return original;
		}
		
		Bitmap editedBitmap = original;

		
		int newWidth = -1;
		int newHeight = -1;
		switch(graphicLoadingOptions.getScalingOption()) {
		case IGraphicLoadingOptions.SCALING_FIXED:
			newWidth = graphicLoadingOptions.getHorizontalScaling();
			newHeight = graphicLoadingOptions.getVerticalScaling();
			break;
		case IGraphicLoadingOptions.SCALING_MULTIPLIER:
			newWidth = editedBitmap.getWidth() * graphicLoadingOptions.getHorizontalScaling();
			newHeight = editedBitmap.getHeight() * graphicLoadingOptions.getVerticalScaling();
			break;
		}
		
		if(newWidth >= 0 && newHeight >= 0) {
			try {
				editedBitmap = getResizedBitmap(editedBitmap, newWidth, newHeight);
			}
			catch(OutOfMemoryError error) {
				throw new AssetException("Out of memory. Failed to resize bitmap to " + newWidth + "x" + newHeight);
			}	
		}
		
		return editedBitmap;
	}
	

	private static Bitmap getResizedBitmap(Bitmap bm, int newWidth, int newHeight) {
		int width = bm.getWidth();
		int height = bm.getHeight();

		float scaleWidth = ((float) newWidth) / width;
		float scaleHeight = ((float) newHeight) / height;

		// CREATE A MATRIX FOR THE MANIPULATION
		Matrix matrix = new Matrix();

		// RESIZE THE BIT MAP
		matrix.postScale(scaleWidth, scaleHeight);

		// "RECREATE" THE NEW BITMAP
		Bitmap resizedBitmap = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, false); // Can throw OutOfMemoryError

		return resizedBitmap;
	}
	
}
