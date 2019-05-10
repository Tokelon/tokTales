package com.tokelon.toktales.core.content.sprite;

import com.tokelon.toktales.core.content.manage.sprite.ISpriteAssetKey;
import com.tokelon.toktales.core.resources.IResource;

/** <i>Note that two ISprite are equal if their names and existing ISpritesets are equal. A resource will be ignored.</i>
 * 
 *
 */
public interface ISprite {
	

	// TODO: This should also be part of the asset key
	public static final int NAME_MODE_ABSOLUTE = 1;
	public static final int NAME_MODE_START = 2;
	public static final int NAME_MODE_CONTAINED = 3;
	
	// TODO: Implement name mode
	//public int getNameMode();
	
	// TODO: These will be replaced by the asset key
	public boolean hasResourceAttached();
	public IResource getResource();
	
	
	
	public ISpriteAssetKey getAssetKey();
	
	
	// This can stay if it's used for anything but loading the sprite
	public String getSpriteName();
	
	
	/** A Sprite is enclosed if it is part of a Spriteset.
	 * 
	 * @return True if this sprite is enclosed, false it not.
	 */
	public boolean isEnclosed(); // TODO: Rename to something better
	
	/**
	 * 
	 * @return The enclosing Spriteset or null if this sprite is not enclosed.
	 */
	public ISpriteset getSpriteset();
	
	/**
	 * 
	 * @return The index of this sprite in the Spriteset. Returns -1 if this sprite is not enclosed.
	 */
	public int getSpritesetIndex();
	
}
