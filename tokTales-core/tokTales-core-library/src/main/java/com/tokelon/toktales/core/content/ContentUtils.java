package com.tokelon.toktales.core.content;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import com.tokelon.toktales.core.content.graphics.BitmapImpl;
import com.tokelon.toktales.core.content.graphics.IBitmap;
import com.tokelon.toktales.core.game.model.IRectangle2i;
import com.tokelon.toktales.core.render.texture.ITexture;
import com.tokelon.toktales.core.render.texture.Texture;

public class ContentUtils implements IContentUtils {


	@Override
	public ITexture cropTexture(ITexture texture, IRectangle2i bounds) {
		IBitmap bitmap = cropBitmap(texture.getBitmap(), bounds);

		Texture result = new Texture(bitmap);
		result
			.setTextureFormat(texture.getTextureFormat())
			.setInternalFormat(texture.getInternalFormat())
			.setDataType(texture.getDataType())
			.setUnpackAlignment(texture.getUnpackAlignment())
			.setFilter(texture.getFilterMin(), texture.getFilterMag())
			.setWrap(texture.getWrapS(), texture.getWrapT());
		
		return result;
	}


	@Override
	public IBitmap cropBitmap(IBitmap bitmap, IRectangle2i bounds) {
		int channels = getChannels(bitmap.getFormat());
		if(channels == -1) {
			throw new IllegalArgumentException("Failed to determine number of channels: Unknown bitmap format");
		}
		
		int capacity = channels * bounds.width() * bounds.height();
		ByteBuffer cropBuffer = ByteBuffer.allocateDirect(capacity).order(ByteOrder.nativeOrder()); // TODO: Replace with IBufferUtils?

		
		byte[] arrayBuffer = new byte[channels * bounds.width()];
		
		for(int i = 0; i < bounds.height(); i++) {
			
			int srcPos = (channels * (i + bounds.top()) * bitmap.getWidth()) + 4 * bounds.left();
			bitmap.getData().position(srcPos);
			bitmap.getData().get(arrayBuffer);
			
			cropBuffer.put(arrayBuffer);
		}
		cropBuffer.position(0); // flip buffer?
		bitmap.getData().position(0);
		

		BitmapImpl result = new BitmapImpl(cropBuffer, bounds.width(), bounds.height(), bitmap.getFormat());
		return result;
	}
	
	private static int getChannels(int format) {
		switch (format) {
		case IBitmap.FORMAT_ALPHA_8:
			return 1;
		case IBitmap.FORMAT_GREY_ALPHA_88:
			return 2;
		case IBitmap.FORMAT_RGB_888:
			return 3;
		case IBitmap.FORMAT_RGBA_8888:
			return 4;
		default:
			return -1;
		}
	}

}
