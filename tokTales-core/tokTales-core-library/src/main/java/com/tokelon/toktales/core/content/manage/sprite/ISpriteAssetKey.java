package com.tokelon.toktales.core.content.manage.sprite;

import com.tokelon.toktales.core.content.manage.keys.IIdentifiableAssetKey;

public interface ISpriteAssetKey extends IIdentifiableAssetKey {
	// TODO: Define equals and check warning "the field type does not redeclare equals and hashcode the resulting code may not work" - redeclare in interface?

	// TODO: Combine SpriteImpl with SpritesetSprite?
	// TODO: Add sprite asset key parameter to ctors of implementations
	// TODO: Check getHorizontalOffsetFor: return spriteWidth * horizontalIndex; // TODO: +1 ?
	
	
	//public boolean isSpriteset(); // Needed?

	
	public String getName();
	
}
