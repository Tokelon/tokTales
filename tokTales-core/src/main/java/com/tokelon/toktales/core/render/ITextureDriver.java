package com.tokelon.toktales.core.render;

/** Provides access to low level GPU functionality for managing textures.
 */
public interface ITextureDriver {
	
	
	/** Loads the given texture via the GL onto the GPU.
	 * 
	 * @param texture The texture that should be loaded.
	 * @param textureIndex The index of the texture unit that should be used for loading. Must be in range [0, {@link #getMaxTextureUnits()}).
	 * @return The location (name) the texture was loaded to.
	 */
	public int loadTexture(ITexture texture, int textureIndex);
	
	/** Unloads the texture at the given location via the GL from the GPU.
	 * 
	 * @param textureLocation The location (name) of the texture that should be unloaded.
	 */
	public void unloadTexture(int textureLocation);

	
	/** Binds the texture at the given location to the texture unit for the given index.
	 * 
	 * @param textureLocation The location of the texture that should be bound.
	 * @param textureIndex The index of the texture unit the texture should be bound to. Must be in range [0, {@link #getMaxTextureUnits()}).
	 */
	public void bindTexture(int textureLocation, int textureIndex);
	
	/** Unbinds the texture on the texture unit for the given index.
	 * 
	 * @param textureIndex The index of the texture unit that should be unbound. Must be in range [0, {@link #getMaxTextureUnits()}).
	 */
	public void unbindTexture(int textureIndex);
	
	
	/** Returns the location of the texture currently bound to the texture unit for the given index.
	 * 
	 * @param textureIndex The index of the texture unit that should be used.
	 * @return A texture location.
	 */
	public int getTextureBoundToIndex(int textureIndex);
	

	// Put these in separate interface?
	
	/** Returns the maximum texture size supported, in pixels.
	 * <p>
	 * As an example, a value of 2048 would mean support for 2D textures up to 2048x2048 size. 
	 * <p>
	 * Note: This value is GPU dependent.
	 * 
	 * @return The max texture size.
	 */
	public int getMaxTextureSize();
	
	/** Returns the number of texture units that can be used for each state (vertex shader, fragment shader, etc.).
	 * <p>
	 * Most of the time this should be at least 8.
	 * <p>
	 * Note: This value is GPU dependent.
	 * 
	 * @return The max texture units.
	 */
	public int getMaxTextureUnits();
	
	/** Returns the number of texture units that are available on the GPU.
	 * <p>
	 * Note: This value is GPU dependent.
	 * 
	 * @return The max combined texture units.
	 */
	public int getMaxCombinedTextureUnits();

}
