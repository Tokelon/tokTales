package com.tokelon.toktales.tools.tiledmap;

import com.tokelon.toktales.core.content.manage.resources.ResourceScannerKey;
import com.tokelon.toktales.core.content.manage.sprite.ISpriteAssetKey;
import com.tokelon.toktales.core.content.manage.sprite.ReadDelegateSpriteAssetKey;
import com.tokelon.toktales.core.content.sprite.ISpriteset;
import com.tokelon.toktales.core.resources.IResource;
import com.tokelon.toktales.tools.tiledmap.model.ITiledMapTileset;

public class TiledSpriteset implements ISpriteset {
	
	
	private final ISpriteAssetKey assetKey;
	
	private final String mName;
	private final ITiledMapTileset mTileset;
	
	public TiledSpriteset(String name, ITiledMapTileset tileset) {
		if(name == null || tileset == null) {
			throw new NullPointerException();
		}
		
		mName = name;
		mTileset = tileset;
		assetKey = new ReadDelegateSpriteAssetKey(name, new ResourceScannerKey(name));
	}

	
	@Override
	public ISpriteAssetKey getAssetKey() {
		return assetKey;
	}
	
	@Override
	public String getSpritesetName() {
		return mName;
	}

	@Override
	public boolean hasResourceAttached() {
		return false;
	}

	@Override
	public IResource getResource() {
		return null;
	}

	@Override
	public int getSpriteWidth() {
		return mTileset.getTileWidth();
	}
	
	@Override
	public int getSpriteHeight() {
		return mTileset.getTileHeight();
	}
	
	@Override
	public int getHorizontalOffsetFor(int spriteIndex) {
		return mTileset.getHorizontalOffsetFor(spriteIndex);
	}

	@Override
	public int getVerticalOffsetFor(int spriteIndex) {
		return mTileset.getVerticalOffsetFor(spriteIndex);
	}

	@Override
	public int getSpriteCount() {
		return mTileset.getTileCount();
	}
	
	
	@Override
	public boolean equals(Object o) {
		if(this == o) {
			return true;
		}
		if(!(o instanceof ISpriteset)) {
			return false;
		}
		ISpriteset that = (ISpriteset) o;
		
		return mName.equals(that.getSpritesetName());
	}
	
	@Override
	public int hashCode() {
		return mName.hashCode();
	}
	
}
