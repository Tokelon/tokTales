package com.tokelon.toktales.core.tiled;

import com.tokelon.toktales.core.content.manage.resources.ResourceScannerKey;
import com.tokelon.toktales.core.content.manage.sprite.ISpriteAssetKey;
import com.tokelon.toktales.core.content.manage.sprite.ReadDelegateSpriteAssetKey;
import com.tokelon.toktales.core.content.sprite.ISprite;
import com.tokelon.toktales.core.content.sprite.ISpriteset;
import com.tokelon.toktales.core.resources.IResource;

public class TiledTileSprite implements ISprite {

	
	private final ISpriteAssetKey assetKey;
	
	private final String mName;
	private final int mIndex;
	private final ISpriteset mSpriteset;
	
	private IResource mResource;

	
	public TiledTileSprite(String name) {
		if(name == null) {
			throw new NullPointerException();
		}
		
		this.mName = name;
		this.mIndex = -1;
		this.mSpriteset = null;
		this.assetKey = new ReadDelegateSpriteAssetKey(name, new ResourceScannerKey(name));
	}
	
	
	/* Pass ITMXTile instead of name similar to TiledSpriteset ?
	 * Why yes, why no?
	 * 
	 */
	public TiledTileSprite(int index, ISpriteset spriteset) {
		if(index < 0) {
			throw new IllegalArgumentException("Sprite index must be >= 0");
		}
		
		this.mName = Integer.toString(index);
		this.mIndex = index;
		this.mSpriteset = spriteset;
		this.assetKey = new ReadDelegateSpriteAssetKey(mName, new ResourceScannerKey(mName));
	}
	
	
	
	public void attachResource(IResource resource) {
		this.mResource = resource;
	}
	
	
	@Override
	public ISpriteAssetKey getAssetKey() {
		return mSpriteset == null ? assetKey : mSpriteset.getAssetKey();
	}
	
	
	@Override
	public String getSpriteName() {
		return mName;
	}

	@Override
	public boolean hasResourceAttached() {
		return mResource != null;
	}

	@Override
	public IResource getResource() {
		return mResource;
	}

	@Override
	public boolean isEnclosed() {
		return mSpriteset != null;
	}

	@Override
	public ISpriteset getSpriteset() {
		return mSpriteset;
	}
	
	@Override
	public int getSpritesetIndex() {
		return mIndex;
	}
	
	@Override
	public String toString() {
		return String.format("Sprite (name, index): %s | %s", mName, mIndex);
	}
	

	@Override
	public boolean equals(Object o) {
		if(this == o) {
			return true;
		}
		if(!(o instanceof ISprite)) {
			return false;
		}
		ISprite that = (ISprite) o;
		
		
		if(this.isEnclosed() && !mSpriteset.equals(that.getSpriteset())) {
			return false;
		}
		else if(!this.isEnclosed() && that.isEnclosed()) {
			return false;
		}
		
		if(!mName.equals(that.getSpriteName())) {
			return false;
		}
		
		return true;
	}
	
	@Override
	public int hashCode() {
		int res = 17;
		res += 11 * mName.hashCode();
		if(isEnclosed()) {
			res += 11 * mSpriteset.hashCode();
		}
		
		return res;
	}

}
