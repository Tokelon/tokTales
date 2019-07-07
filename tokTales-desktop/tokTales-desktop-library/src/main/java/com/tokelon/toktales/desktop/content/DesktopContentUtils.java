package com.tokelon.toktales.desktop.content;

import java.nio.ByteBuffer;

import org.lwjgl.system.MemoryUtil;

import com.tokelon.toktales.core.content.ContentUtils;
import com.tokelon.toktales.core.content.graphics.IBitmap;
import com.tokelon.toktales.core.game.model.IRectangle2i;
import com.tokelon.toktales.desktop.lwjgl.data.ISTBBitmap;
import com.tokelon.toktales.desktop.lwjgl.data.STBBitmap;

public class DesktopContentUtils extends ContentUtils {


	@Override
	public IBitmap cropBitmap(IBitmap bitmap, IRectangle2i bounds) {
		IBitmap result;
		if(bitmap instanceof ISTBBitmap) {
			ISTBBitmap stbBitmap = (ISTBBitmap) bitmap;
			result = cropSTBBitmap(stbBitmap, bounds);
		}
		else {
			result = super.cropBitmap(bitmap, bounds);
		}
		
		return result;
	}
	
	private static IBitmap cropSTBBitmap(ISTBBitmap bitmap, IRectangle2i bounds) {
		int channels = bitmap.getChannels();
		
		ByteBuffer cropBuffer = MemoryUtil.memAlloc(channels * bounds.width() * bounds.height());
		
		byte[] arrayBuffer = new byte[channels * bounds.width()];
		for(int i = 0; i < bounds.height(); i++) {
			
			int srcPos = (channels * (i + bounds.top()) * bitmap.getWidth()) + 4 * bounds.left();
			bitmap.getData().position(srcPos);
			bitmap.getData().get(arrayBuffer);
			
			cropBuffer.put(arrayBuffer);
		}
		cropBuffer.position(0); // flip buffer?
		bitmap.getData().position(0);
		

		STBBitmap texImage = new STBBitmap(cropBuffer, bounds.width(), bounds.height(), channels, bitmap.getSourceChannels(), (image) -> MemoryUtil.memFree(cropBuffer));
		return texImage;
	}

	
	/* TODO: Consider implementing this
	public static TextureImage cropTexture(TextureImage source, TRectangle bounds) {
		ByteBuffer cropBuffer = BufferUtils.createByteBuffer(4 * bounds.width() * bounds.height());
		
		logger.d("size: " +source.getWidth() + ", " +source.getHeight());
		logger.d("limit: " +source.getData().limit());
		
		int result = STBImageResize.stbir_resize_region(
		//int result = STBImageResize.stbir_resize_subpixel(
				source.getData(), source.getWidth(), source.getHeight(), 0,
				cropBuffer, bounds.width(), bounds.height(), 0,
				STBImageResize.STBIR_TYPE_UINT8, 4, 3, 0,
				STBImageResize.STBIR_EDGE_CLAMP, STBImageResize.STBIR_EDGE_CLAMP, STBImageResize.STBIR_FILTER_DEFAULT, STBImageResize.STBIR_FILTER_DEFAULT, STBImageResize.STBIR_COLORSPACE_LINEAR,
				bounds.left, bounds.top, bounds.right, bounds.bottom);
				//1, 1, bounds.left, bounds.top);
		
		if(result != 1) {
			logger.e(TAG, "Image resize failed with error: " +result);
			return null;
		}
		
		TextureImage cropImage = new TextureImage(cropBuffer, bounds.width(), bounds.hashCode(), 4);
		return cropImage;
	}
	*/
	
}
