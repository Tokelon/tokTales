package com.tokelon.toktales.core.render.texture;

import com.tokelon.toktales.core.content.graphics.IBitmap;
import com.tokelon.toktales.core.render.opengl.gl20.IGL11;
import com.tokelon.toktales.core.render.opengl.gl20.IGL12;

public class Texture implements ITexture {
	// TODO: Document default values and implementation logic
	
	
	private int textureFormat;
	private int internalFormat;
	private int unpackAlignment;
	
	private int dataType = IGL11.GL_UNSIGNED_BYTE;
	
	private int filterMin = IGL11.GL_NEAREST;
	private int filterMag = IGL11.GL_NEAREST;
	private int wrapS = IGL12.GL_CLAMP_TO_EDGE;
	private int wrapT = IGL12.GL_CLAMP_TO_EDGE;
	
	private final IBitmap bitmap;
	
	public Texture(IBitmap bitmap) {
		this.bitmap = bitmap;
		
		this.textureFormat = imageFormatToTextureFormat(bitmap.getFormat());
		this.internalFormat = textureFormat;
		this.unpackAlignment = unpackAlignmentFromTextureFormat(textureFormat);
	}
	
	
	@Override
	public IBitmap getBitmap() {
		return bitmap;
	}

	@Override
	public int getTextureFormat() {
		return textureFormat;
	}
	
	@Override
	public int getInternalFormat() {
		return internalFormat;
	}

	@Override
	public int getUnpackAlignment() {
		return unpackAlignment;
	}
	
	
	private int imageFormatToTextureFormat(int imageFormat) {
		switch (imageFormat) {
		case IBitmap.FORMAT_RGB_888:
			return IGL11.GL_RGB;
		case IBitmap.FORMAT_RGBA_8888:
			return IGL11.GL_RGBA;
		case IBitmap.FORMAT_ALPHA_8:
			return IGL11.GL_ALPHA; // TODO: Use GL_LUMINANCE ?
		case IBitmap.FORMAT_GREY_ALPHA_88:
			return IGL11.GL_LUMINANCE_ALPHA;
		default:
			return -1;
		}
	}
	
	private int unpackAlignmentFromTextureFormat(int textureFormat) {
		switch (textureFormat) {
		case IGL11.GL_RGB:
		case IGL11.GL_ALPHA:
		case IGL11.GL_LUMINANCE:
			return 1;
		case IGL11.GL_LUMINANCE_ALPHA:
			return 2;
		case IGL11.GL_RGBA:
			return 4;
		default:
			return -1;
		}
	}
	
	private int unpackAlignmentFromImageFormat(int imageFormat) {
		switch (imageFormat) {
		case IBitmap.FORMAT_ALPHA_8:
		case IBitmap.FORMAT_RGB_888:
			return 1;
		case IBitmap.FORMAT_GREY_ALPHA_88:
			return 2;
		case IBitmap.FORMAT_RGBA_8888:
			return 4;
		default:
			return -1;
		}
	}
	

	@Override
	public int getDataType() {
		return dataType;
	}

	@Override
	public int getFilterMin() {
		return filterMin;
	}
	
	@Override
	public int getFilterMag() {
		return filterMag;
	}
	
	@Override
	public int getWrapS() {
		return wrapS;
	}
	
	@Override
	public int getWrapT() {
		return wrapT;
	}
	

	@Override
	public ITexture setTextureFormat(int textureFormat) {
		this.textureFormat = textureFormat;
		return this;
	}

	@Override
	public ITexture setInternalFormat(int internalFormat) {
		this.internalFormat = internalFormat;
		return this;
	}

	@Override
	public ITexture setUnpackAlignment(int unpackAlignment) {
		this.unpackAlignment = unpackAlignment;
		return this;
	}

	@Override
	public ITexture setDataType(int dataType) {
		this.dataType = dataType;
		return this;
	}

	@Override
	public ITexture setFilter(int filterMin, int filterMag) {
		this.filterMin = filterMin;
		this.filterMag = filterMag;
		return this;
	}

	@Override
	public ITexture setWrap(int wrapS, int wrapT) {
		this.wrapS = wrapS;
		this.wrapT = wrapT;
		return this;
	}

}
