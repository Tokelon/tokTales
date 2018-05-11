package com.tokelon.toktales.core.render;

public class TextureRegion implements ITextureRegion {

	
	private int regionWidth;
	private int regionHeight;
	private int regionX;
	private int regionY;
	
	private final IRenderTexture texture;
	
	public TextureRegion(IRenderTexture texture) {
		this(texture, texture.getBitmap().getWidth(), texture.getBitmap().getHeight(), 0, 0);
	}
	
	public TextureRegion(IRenderTexture texture, int width, int height) {
		this(texture, width, height, 0, 0);
	}
	
	public TextureRegion(IRenderTexture texture, int width, int height, int x, int y) {
		this.texture = texture;
		this.regionWidth = width;
		this.regionHeight = height;
		this.regionX = x;
		this.regionY = y;
	}
	
	
	@Override
	public IRenderTexture getTexture() {
		return texture;
	}

	@Override
	public int getRegionWidth() {
		return regionWidth;
	}

	@Override
	public int getRegionHeight() {
		return regionHeight;
	}

	@Override
	public int getRegionX() {
		return regionX;
	}

	@Override
	public int getRegionY() {
		return regionY;
	}


	@Override
	public ITextureRegion setRegionWidth(int width) {
		this.regionWidth = width;
		return this;
	}

	@Override
	public ITextureRegion setRegionHeight(int height) {
		this.regionHeight = height;
		return this;
	}

	@Override
	public ITextureRegion setRegionX(int x) {
		this.regionX = x;
		return this;
	}

	@Override
	public ITextureRegion setRegionY(int y) {
		this.regionY = y;
		return this;
	}

	@Override
	public ITextureRegion setRegionSize(int width, int height) {
		this.regionWidth = width;
		this.regionHeight = height;
		return this;
	}

	@Override
	public ITextureRegion setRegionPosition(int x, int y) {
		this.regionX = x;
		this.regionY = y;
		return this;
	}
	
	@Override
	public ITextureRegion setRegion(int width, int height, int x, int y) {
		this.regionWidth = width;
		this.regionHeight = height;
		this.regionX = x;
		this.regionY = y;
		return this;
	}

}
