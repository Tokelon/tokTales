package com.tokelon.toktales.core.render;

/** Coordinates the texture bindings to texture units.
 */
public interface ITextureCoordinator {

	
	/**
	 * @return The texture manager for this coordinator.
	 */
	public ITextureManager getTextureManager();
	
	
	/** Binds the given texture to a texture unit on the GPU.
	 * <p>
	 * An optimal binding index will be determined, possibly overriding an existing binding.<br>
	 * If the texture is already bound by this coordinator, there will be no effect.
	 * <p>
	 * <i>Note: The texture must be managed and loaded by this coordinator's texture manager.</i>
	 * 
	 * @param texture The texture that should be bound.
	 * @return The index of the texture unit the texture was bound to.
	 * @throws IllegalStateException If the texture is not loaded by this coordinator's texture manager.
	 * @throws NullPointerException If texture is null.
	 */
	public int bindTexture(IRenderTexture texture);
	//public void markTextureBound(IRenderTexture texture, int textureIndex);
	
	
	/** Determines a binding index for the given texture and marks it as externally bound.
	 * <p>
	 * The binding index will be determined in the same way as {@link #bindTexture(IRenderTexture)},
	 * only the texture will not be bound.<br>
	 * If the texture is already bound by this coordinator, there will be no effect.
	 * <p>
	 * The coordinator will assume that the texture is bound to that index.
	 * <br>
	 * No calls to the GPU will be made.
	 * <p>
	 * <i>Note: The texture must be managed and loaded by this coordinator's texture manager.</i>
	 * 
	 * @param texture The texture for which a index should be determined.
	 * @return The index of the texture unit that was chosen.
	 * @throws IllegalStateException If the texture is not loaded by this coordinator's texture manager.
	 * @throws NullPointerException If texture is null.
	 */
	public int requestIndexFor(IRenderTexture texture);
	
	/** Determines a binding index for the given texture.
	 * <p>
	 * The binding index will be determined in the same way as {@link #bindTexture(IRenderTexture)},
	 * without having an effect.
	 * <p>
	 * <i>Note: The texture must be managed and loaded by this coordinator's texture manager.</i>
	 * 
	 * @param texture The texture for which a index should be determined. 
	 * @return The index of the texture unit that was chosen.
	 * @throws IllegalStateException If the texture is not loaded by this coordinator's texture manager.
	 * @throws NullPointerException If texture is null.
	 */
	public int peekIndexFor(IRenderTexture texture);

	
	/** Unbinds the given texture.
	 * <p>
	 * If the texture is not bound by this coordinator, there will be no effect.
	 * 
	 * @param texture The texture that should be unbound.
	 * @throws NullPointerException If texture is null.
	 */
	public void unbindTexture(IRenderTexture texture);
	
	
	/** Returns the index of the texture unit that the given texture is bound to.
	 * 
	 * @param texture The texture of which the binding index should be returned.
	 * @return A texture unit index, or -1 if the texture is not bound by this coordinator.
	 * @throws NullPointerException If texture is null.
	 */
	public int getTextureIndexFor(IRenderTexture texture);
	
	/** Returns the texture which is bound for the given index.
	 * 
	 * @param textureIndex The binding index for which the texture should be returned.
	 * @return A texture, or null if no texture is bound for the index by this coordinator.
	 * @throws IllegalArgumentException If textureIndex < 0.
	 */
	public IRenderTexture getBoundTextureForIndex(int textureIndex);
	
	/** Returns whether the given texture is bound on the given index.
	 * 
	 * @param texture The texture.
	 * @param textureIndex The binding index.
	 * @return True if the texture is bound for the index, false if not.
	 * @throws IllegalArgumentException If textureIndex < 0.
	 * @throws NullPointerException If texture is null.
	 */
	public boolean isTextureBoundForIndex(IRenderTexture texture, int textureIndex);

	
	/**
	 * @return The current number of available binding indexes (free and used).
	 */
	public int getUsedIndexCount();
	
	/**
	 * @return The current number of used binding indexes.
	 */
	public int getBoundIndexCount();

	
	
	public interface ITextureCoordinatorFactory {
		
		public ITextureCoordinator create(ITextureManager textureManager);
	}
	
}
