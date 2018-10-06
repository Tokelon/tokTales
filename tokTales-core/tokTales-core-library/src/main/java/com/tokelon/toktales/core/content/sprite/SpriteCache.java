package com.tokelon.toktales.core.content.sprite;

import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;

public class SpriteCache extends AbstractCache {

	
	private final ConcurrentHashMap<ISprite, ISpriteAsset> cache;
	
	//private int size = Integer.MAX_VALUE;
	
	
	public SpriteCache(int initialCapacity) {
		
		cache = new ConcurrentHashMap<ISprite, ISpriteAsset>(initialCapacity, 0.9f, 1);
	}
	
	
	public void store(ISprite sprite, ISpriteAsset asset) {
		cache.put(sprite, asset);
	}
	
	public ISpriteAsset retrieve(ISprite sprite) {
		return cache.get(sprite);
	}
	
	public boolean contains(ISprite sprite) {
		return cache.containsKey(sprite);
	}
	
	
	public void runClearAll() {
		
		/* FREE ASSETS - This is important!
		 * -> Maybe not as important in this case as the Bitmap memory will be freed as soon as it will be GC 
		 * 
		 */
		for(ISpriteAsset spriteAsset: cache.values()) {
			if(spriteAsset == CACHE_SPECIAL_NOT_FOUND || spriteAsset == CACHE_SPECIAL_LOAD_ERROR) {
				// Do not call freeAsset() as this is a special asset!
			}
			else {
				spriteAsset.getContent().freeAsset();
			}
		}
		
		cache.clear();
	}
	
	// Maybe put in interface like "cache tools" or something
	public void runClearErrorSprites() {
		Iterator<ISprite> it = cache.keySet().iterator();
		
		while(it.hasNext()) {
			ISpriteAsset na = cache.get(it.next());
			if(na == CACHE_SPECIAL_NOT_FOUND || na == CACHE_SPECIAL_LOAD_ERROR) {
				// Do not call freeAsset() as this is a special asset!
				it.remove();
			}
		}
	}

	
}
