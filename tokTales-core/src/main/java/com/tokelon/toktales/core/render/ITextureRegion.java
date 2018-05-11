package com.tokelon.toktales.core.render;

/** Defines an area inside a texture.
 */
public interface ITextureRegion {

	
	public IRenderTexture getTexture();

	public int getRegionWidth();
	public int getRegionHeight();
	
	public int getRegionX();
	public int getRegionY();
	
	
	public ITextureRegion setRegionWidth(int width);
	public ITextureRegion setRegionHeight(int height);
	public ITextureRegion setRegionX(int x);
	public ITextureRegion setRegionY(int y);
	public ITextureRegion setRegionSize(int width, int height);
	public ITextureRegion setRegionPosition(int x, int y);
	public ITextureRegion setRegion(int width, int height, int x, int y);
	

	/** Returns the scale factor between the region width and the texture width.
	 * <p>
	 * <i>widthScaleFactor = regionWidth / textureWidth</i><br>
	 * Ex. With a texture width of 64px and a region width of 16px, the width scale factor would equal 0.25f.
	 * 
	 * @return A floating point number in [0, 1].
	 */
	public default float getTextureWidthScaleFactor() {
		return (float) getRegionWidth() / getTexture().getBitmap().getWidth();
	}
	
	/** Returns the scale factor between the region height and the texture height.
	 * <p>
	 * <i>heightScaleFactor = regionHeight / textureHeight</i><br>
	 * Ex. With a texture height of 64px and a region height of 32px, the height scale factor would equal 0.50f.
	 * 
	 * @return A floating point number in [0, 1].
	 */
	public default float getTextureHeightScaleFactor() {
		return (float) getRegionHeight() / getTexture().getBitmap().getHeight();
	}
	
	/** Returns the scale factor between the region x position and the texture width.
	 * <p>
	 * <i>xScaleFactor = regionX / textureWidth</i><br>
	 * Ex. With a texture width of 64px and a region x of 8px, the x scale factor would equal 0.125f.
	 * 
	 * @return A floating point number in [0, 1].
	 */
	public default float getTextureXScaleFactor() {
		return (float) getRegionX() / getTexture().getBitmap().getWidth();
	}
	
	/** Returns the scale factor between the region y position and the texture height.
	 * <p>
	 * <i>yScaleFactor = regionY / textureHeight</i><br>
	 * Ex. With a texture height of 64px and a region y of 0px, the y scale factor would equal 0f.
	 * 
	 * @return A floating point number in [0, 1].
	 */
	public default float getTextureYScaleFactor() {
		return (float) getRegionY() / getTexture().getBitmap().getHeight();
	}
	
}
