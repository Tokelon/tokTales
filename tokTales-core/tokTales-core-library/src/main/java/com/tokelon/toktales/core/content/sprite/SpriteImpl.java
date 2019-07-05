package com.tokelon.toktales.core.content.sprite;

import com.tokelon.toktales.core.content.manage.resources.ResourceScannerKey;
import com.tokelon.toktales.core.content.manage.sprite.ISpriteAssetKey;
import com.tokelon.toktales.core.content.manage.sprite.ReadDelegateSpriteAssetKey;
import com.tokelon.toktales.core.resources.IResource;

public class SpriteImpl implements ISprite {

	
	private final ISpriteAssetKey assetKey;
	
	private final String name;
	private IResource resource;

	public SpriteImpl(String name) {
		if(name == null) {
			throw new NullPointerException();
		}
		
		this.name = name;
		this.assetKey = new ReadDelegateSpriteAssetKey(name, new ResourceScannerKey(name));
	}
	
	
	public void attachResource(IResource resource) {
		this.resource = resource;
	}
	
	
	@Override
	public ISpriteAssetKey getAssetKey() {
		return assetKey;
	}
	

	@Override
	public String getSpriteName() {
		return name;
	}
	
	@Override
	public boolean hasResourceAttached() {
		return resource != null;
	}

	@Override
	public IResource getResource() {
		return resource;
	}
	
	
	@Override
	public boolean isEnclosed() {
		return false;
	}

	@Override
	public ISpriteset getSpriteset() {
		return null;
	}
	
	@Override
	public int getSpritesetIndex() {
		return -1;
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
		
		return name.equals(that.getSpriteName());
	}
	
	@Override
	public int hashCode() {
		return name.hashCode();
	}
	
}
