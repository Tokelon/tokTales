package com.tokelon.toktales.core.render;

import com.tokelon.toktales.core.content.IBitmap;

/** Contains an {@link IBitmap} and the configuration for how it should be drawn.
 * <p>
 * The configuration is based on OpenGL, but this type may be used with any other GL (Graphics Library)
 * and the configuration values returned are not guaranteed to conform with OpenGL. 
 * 
 */
public interface IRenderTexture { // TODO: Rename to ITexture
	/* Add name member? | Or id member
	 * Maybe define equals and hashCode and make it part of it? 
	public String getName();
	public void setName(String name);
	*/
	
	
	/** Returns the bitmap for this texture.
	 * 
	 * @return A bitmap.
	 */
	public IBitmap getBitmap();
	

	/** Returns the format of this texture.
	 * <p>
	 * This is usually the GL representation of the bitmap format. (ex. IGL11.GL_RGBA)
	 * 
	 * @return A flag indicating the format of the texture.
	 */
	public int getTextureFormat();
	
	/** Returns the internal format the GL will use to store the texture.
	 * <p>
	 * This will usually be the same as {@link #getTextureFormat()},
	 * and on some platforms it is even required to be the same (ex. Android).
	 * 
	 * @return A flag indicating the internal format.
	 */
	public int getInternalFormat();
	
	/** Returns the data alignment for the start of each pixel row.
	 * <p>
	 * Generally this is <b>4</b> for {@link IBitmap#FORMAT_RGBA_8888} and <b>1</b> for {@link IBitmap#FORMAT_RGB_888}.
	 * <p>
	 * For more information see the OpenGL specification.  
	 * 
	 * @return The number of bytes to which the data is aligned. Usually a value in (1, 2, 4, 8).
	 */
	public int getUnpackAlignment();
	
	/** Returns the data type for this texture.
	 * <p>
	 * This specifies the number of bits per channel (component).<br>
	 * Most common are formats where each channel gets 8 bits (ex. {@link IBitmap#FORMAT_RGBA_8888})
	 * and, in OpenGL for example, use IGL11.GL_UNSIGNED_BYTE.
	 * 
	 * @return A flag indicating the data type.
	 */
	public int getDataType();


	/** Returns the filter setting that will be used for texture minification.
	 * <p>
	 * For OpenGL this may be IGL11.GL_LINEAR or IGL11.GL_NEAREST.
	 * 
	 * @return A flag indicating the minification filter setting.
	 */
	public int getFilterMin();
	
	/** Returns the filter setting that will be used for texture magnification.
	 * <p>
	 * For OpenGL this may be IGL11.GL_LINEAR or IGL11.GL_NEAREST.
	 * 
	 * @return A flag indicating the magnification filter setting.
	 */
	public int getFilterMag();
	
	
	/** Returns the wrap setting that will be used for the S (or U or X) texture coordinate.
	 * <p>
	 * For OpenGL this may be IGL11.GL_REPEAT or IGL11.GL_CLAMP_TO_EDGE.
	 * 
	 * @return A flag indicating the wrap setting for S coordinates.
	 */
	public int getWrapS();
	
	/** Returns the wrap setting that will be used for the T (or V or Y) texture coordinate.
	 * <p>
	 * For OpenGL this may be IGL11.GL_REPEAT or IGL11.GL_CLAMP_TO_EDGE.
	 * 
	 * @return A flag indicating the wrap setting for T coordinates.
	 */
	public int getWrapT();


	public IRenderTexture setTextureFormat(int textureFormat);
	public IRenderTexture setInternalFormat(int internalFormat);
	public IRenderTexture setUnpackAlignment(int unpackAlignment);
	public IRenderTexture setDataType(int dataType);
	
	public IRenderTexture setFilter(int filterMin, int filterMag);
	public IRenderTexture setWrap(int wrapS, int wrapT);
	

	
	/* Subtextures
	// If not set, these return valid values (0 for offsets and bitmap sizes for sizes) -> Document
	public int getSubTextureOffsetX();
	public int getSubTextureOffsetY();
	
	// Check values before return and add additional logic? ex. width <= bitmap.getWidth()
	public int getSubTextureWidth();
	public int getSubTextureHeight();
	
	//public boolean hasSubTextureProperties();
	//public void removeSubTextureProperties();
	
	public IRenderTexture setSubTextureOffset(int subOffsetX, int subOffsetY);
	public IRenderTexture setSubTextureSize(int subWidth, int subHeight);
	*/
	
	// Implement copying?
	//public default IRenderTexture subCopy(width, height, offsetx, offsety)
	//public default IRenderTexture copy()
	
}
