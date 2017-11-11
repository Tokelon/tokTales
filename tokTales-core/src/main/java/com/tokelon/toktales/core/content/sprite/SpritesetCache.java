package com.tokelon.toktales.core.content.sprite;

import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;

public class SpritesetCache extends AbstractCache {

	
	private final ConcurrentHashMap<ISpriteset, ISpriteAsset> cache;
		
	
	public SpritesetCache(int initialCapacity) {
		
		cache = new ConcurrentHashMap<ISpriteset, ISpriteAsset>(initialCapacity, 0.9f, 1);
	}
	
	
	public void store(ISpriteset spriteset, ISpriteAsset asset) {
		cache.put(spriteset, asset);
	}
	
	public ISpriteAsset retrieve(ISpriteset spriteset) {
		return cache.get(spriteset);
	}
	
	public boolean contains(ISpriteset spriteset) {
		return cache.containsKey(spriteset);
	}
	
	
	public void runClearAll() {
		/* Free assets.
		 */
		for(ISpriteAsset spriteAsset: cache.values()) {
			spriteAsset.getContent().freeAsset();
		}
		
		cache.clear();
	}
	

	public void runClearErrorSprites() {
		Iterator<ISpriteset> it = cache.keySet().iterator();
		
		while(it.hasNext()) {
			ISpriteAsset na = cache.get(it.next());
			if(na == CACHE_SPECIAL_NOT_FOUND || na == CACHE_SPECIAL_LOAD_ERROR) {
				// Do not call freeAsset() as this is a special asset!
				it.remove();
			}
		}
	}

}
