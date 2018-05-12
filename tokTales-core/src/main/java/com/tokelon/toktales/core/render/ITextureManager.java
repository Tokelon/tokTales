package com.tokelon.toktales.core.render;

/** Manages the loading of textures to the GPU.
 */
public interface ITextureManager {
	
	
	/**
	 * @return The texture driver for this manager.
	 */
	public ITextureDriver getTextureDriver();
	
	
	/** Returns the index of the texture unit that will be used to manage textures.
	 * 
	 * @return An index for a texture unit.
	 */
	public int getManagingTextureIndex();
	
	/**
	 * @return The current number of textures managed by this manager.
	 */
	public int getTextureCount();
	
	/**
	 * @return The current number of textures loaded on the GPU by this manager.
	 */
	public int getLoadedTextureCount();
	
	
	
	/** Adds the given texture to this manager.
	 * <p>
	 * If the texture is already managed by this manager, there will be no effect.
	 * 
	 * @param texture The texture to add.
	 * @return True if the texture was added, false if it was already managed by this manager.
	 */
	public boolean addTexture(ITexture texture);
	// TODO: Add things like retain priority, or retain scope, etc.
	//public boolean addTexture(IRenderTexture texture, ITextureOptions options);
	
	
	/** Adds the given texture to this manager as externally loaded.
	 * <p>
	 * The texture manager will assume that the texture is loaded at the given location.<br>
	 * No calls to the GPU will be made.
	 * <p>
	 * If the texture is already loaded by this manager, there will be no effect.
	 * 
	 * @param texture The texture to add.
	 * @param location The location of the loaded texture on the GPU.
	 * @return True if the texture was added, false if it was already managed by this manager.
	 */
	public boolean addLoadedTexture(ITexture texture, int location);
	
	/** Loads the given texture onto the GPU.
	 * <p>
	 * If the texture is not managed by this manager,
	 * it will added in the same way as if {@link #addTexture(ITexture)} had been called before.
	 * <p> 
	 * If the texture is already loaded by this manager, there will be no effect.

	 * @param texture The texture to load.
	 * @return True if the texture was loaded, false if it was already loaded by this manager.
	 */
	public boolean loadTexture(ITexture texture);
	
	
	/** Returns whether the given texture is managed by this manager.
	 * 
	 * @param texture The texture that should be checked.
	 * @return True if the texture is managed, false if not.
	 */
	public boolean hasTexture(ITexture texture);
	
	/** Returns whether the given texture has been loaded by this manager.
	 * 
	 * @param texture The texture that should be checked.
	 * @return True if the texture is loaded, false if not.
	 */
	public boolean hasTextureLoaded(ITexture texture);
	
	
	/** Returns the location of the given texture on the GPU.
	 * 
	 * @param texture The texture of which the location should be returned.
	 * @return A texture location, or -1 if the texture is not loaded by this manager.
	 */
	public int getTextureLocation(ITexture texture);
	
	
	/** Removes the given texture from this manager.
	 * <p>
	 * If the texture is loaded by this manager, it will be unloaded.
	 * <p>
	 * If the texture is not managed by this manager, there will be no effect.
	 * 
	 * @param texture The texture that should be removed.
	 * @return True if the texture was managed, false if not.
	 */
	public boolean removeTexture(ITexture texture);
	
	/** Removes the given texture from this manager as externally unloaded.
	 * <p>
	 * The manager will assume that the texture is unloaded.<br>
	 * No calls to the GPU will be made.
	 * <p>
	 * If the texture is not managed by this manager, there will be no effect.
	 * 
	 * @param texture The texture that should be removed.
	 * @return True if the texture was managed, false if not.
	 */
	public boolean removeLoadedTexture(ITexture texture);
	
	/** Unloads the given texture from the GPU.
	 * <p>
	 * If the texture is not loaded by this manager, there will be no effect.
	 * 
	 * @param texture The texture that should be unloaded.
	 * @return True if the texture was loaded, false if not.
	 */
	public boolean unloadTexture(ITexture texture);
	
	
	/** Removes all textures from this manager.
	 * <p>
	 * Any loaded textures will be unloaded in the process.
	 */
	public void removeAll();
	
	/** Unloads all textures of this manager.
	 */
	public void unloadAll();
	

	//public Map<IRenderTexture, TextureInfo> getAllTextureEntries();
	
}
