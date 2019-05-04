package com.tokelon.toktales.core.content.manage.sprite;

import javax.inject.Inject;

import com.tokelon.toktales.core.content.manage.IAssetLoader;
import com.tokelon.toktales.core.content.manage.IAssetStore;
import com.tokelon.toktales.core.content.sprite.ISprite;
import com.tokelon.toktales.core.content.sprite.ISpriteAsset;
import com.tokelon.toktales.core.content.sprite.ISpriteset;
import com.tokelon.toktales.core.engine.content.ContentException;
import com.tokelon.toktales.core.engine.content.ContentNotFoundException;
import com.tokelon.toktales.core.engine.log.ILogger;
import com.tokelon.toktales.core.util.SynchronizedPool;
import com.tokelon.toktales.core.util.options.IOptions;

public class ExtendedSpriteAssetManager extends SpriteAssetManager implements IExtendedSpriteAssetManager {

	
	private final SynchronizedPool<PooledSpriteAssetKey> spriteAssetKeyPool;
	private final SynchronizedPool<PooledSpritesetAssetKey> spritesetAssetKeyPool;
	
	
	@Inject
	public ExtendedSpriteAssetManager(ILogger logger, IAssetStore<ISpriteAsset, ISpriteAssetKey> assetStore, IAssetLoader<ISpriteAsset, ISpriteAssetKey, IOptions> assetLoader) {
		super(logger, assetStore, assetLoader);
		
		this.spriteAssetKeyPool = new SynchronizedPool<>(() -> new PooledSpriteAssetKey(), 100);
		this.spritesetAssetKeyPool = new SynchronizedPool<>(() -> new PooledSpritesetAssetKey(), 100);
	}

	
	private PooledSpriteAssetKey getAssetKeyForSprite(ISprite sprite) {
		// TODO: Consider resource!! Use ResourceKey
		
		PooledSpriteAssetKey key;
		if(sprite.isEnclosed()) {
			ISpriteset spriteset = sprite.getSpriteset();
			// TODO: Do this correctly - assumes the spriteset is square
			int horizontalSpriteCount = (int) Math.sqrt(spriteset.getSpriteCount());
			int verticalSpriteCount = (int) Math.sqrt(spriteset.getSpriteCount());
			
			//SpritesetAssetKey spritesetKey = new SpritesetAssetKey(spriteset.getSpritesetName(), spriteset.getSpriteWidth(), spriteset.getSpriteHeight(), horizontalSpriteCount, verticalSpriteCount);
			//key = new SpriteAssetKey(sprite.getSpriteName(), spritesetKey, sprite.getSpritesetIndex());
			PooledSpritesetAssetKey spritesetKey = spritesetAssetKeyPool.newObject().set(spriteset.getSpritesetName(), spriteset.getSpriteWidth(), spriteset.getSpriteHeight(), horizontalSpriteCount, verticalSpriteCount);
			key = spriteAssetKeyPool.newObject().set(sprite.getSpriteName(), spritesetKey, sprite.getSpritesetIndex());
		}
		else {
			//key = new SpriteAssetKey(sprite.getSpriteName());
			key = spriteAssetKeyPool.newObject().set(sprite.getSpriteName());
		}
		
		return key;
	}
	
	
	private ISpriteAsset getAssetWithKey(ISprite key, IOptions options, GetAssetFunction getAssetFunction) throws ContentException {
		PooledSpriteAssetKey assetKeyForSprite = getAssetKeyForSprite(key);
		
		boolean returnAssetKey = getStore().contains(assetKeyForSprite);
		
		ISpriteAsset asset = getAssetFunction.apply(assetKeyForSprite, options);
		if(asset == null || returnAssetKey) {
			PooledSpritesetAssetKey spritesetKey = assetKeyForSprite.getSpritesetKey();
			
			assetKeyForSprite.reset();
			spriteAssetKeyPool.free(assetKeyForSprite);

			spritesetKey.reset();
			spritesetAssetKeyPool.free(spritesetKey);
		}
		
		return asset;
	}
	
	
	@Override
	public ISpriteAsset getAsset(ISprite key) {
		try {
			return getAssetWithKey(key, null, (k, o) -> getAsset(k));
		} catch (ContentException e) {
			return null;
		}
	}

	@Override
	public ISpriteAsset getAsset(ISprite key, IOptions options) {
		try {
			return getAssetWithKey(key, options, (k, o) -> getAsset(k, o));
		} catch (ContentException e) {
			return null;
		}
	}

	
	@Override
	public ISpriteAsset getAssetOrError(ISprite key) throws ContentNotFoundException {
		try {
			return getAssetWithKey(key, null, (k, o) -> getAssetOrError(k));
		} catch (ContentNotFoundException e) {
			throw e;
		} catch (ContentException e) {
			assert false : "Only ContentNotFoundException should be thrown here";
			return null;
		}
	}

	
	@Override
	public ISpriteAsset getAssetLoadIfNeeded(ISprite key) {
		try {
			return getAssetWithKey(key, null, (k, o) -> getAssetLoadIfNeeded(k));
		} catch (ContentException e) {
			assert false : "ContentException should never be thrown here";
			return null;
		}
	}

	@Override
	public ISpriteAsset getAssetLoadIfNeeded(ISprite key, IOptions options) {
		try {
			return getAssetWithKey(key, options, (k, o) -> getAssetLoadIfNeeded(k, o));
		} catch (ContentException e) {
			assert false : "ContentException should never be thrown here";
			return null;
		}
	}

	
	@Override
	public ISpriteAsset getAssetLoadIfNeededOrError(ISprite key) throws ContentException {
		return getAssetWithKey(key, null, (k, o) -> getAssetLoadIfNeededOrError(k));
	}

	@Override
	public ISpriteAsset getAssetLoadIfNeededOrError(ISprite key, IOptions options) throws ContentException {
		return getAssetWithKey(key, options, (k, o) -> getAssetLoadIfNeededOrError(k, o));
	}
	
	
	private interface GetAssetFunction {
		public ISpriteAsset apply(ISpriteAssetKey k, IOptions o) throws ContentException;
	}
	
	
	protected static class PooledSpriteAssetKey implements ISpriteAssetKey {
		private String spriteName;
		private PooledSpritesetAssetKey spritesetKey;
		private int spritesetIndex;
		
		public PooledSpriteAssetKey set(String spriteName) {
			this.spriteName = spriteName;
			this.spritesetKey = null;
			this.spritesetIndex = 0;
			
			return this;
		}
		
		public PooledSpriteAssetKey set(String spriteName, PooledSpritesetAssetKey spritesetKey, int spritesetIndex) {
			this.spriteName = spriteName;
			this.spritesetKey = spritesetKey;
			this.spritesetIndex = spritesetIndex;
			
			return this;
		}
		
		public PooledSpriteAssetKey reset() {
			this.spriteName = null;
			this.spritesetKey = null;
			this.spritesetIndex = 0;
			
			return this;
		}
		
		@Override
		public String getSpriteName() {
			return spriteName;
		}

		@Override
		public boolean isSpriteset() {
			return spritesetKey != null;
		}

		@Override
		public PooledSpritesetAssetKey getSpritesetKey() {
			return spritesetKey;
		}

		@Override
		public int getSpritesetIndex() {
			return spritesetIndex;
		}
	}
	
	protected static class PooledSpritesetAssetKey implements ISpritesetAssetKey {
		private String spritesetName;
		private int spriteWidth;
		private int spriteHeight;
		private int horizontalSpriteCount;
		private int verticalSpriteCount;

		public PooledSpritesetAssetKey set(String spritesetName, int spriteWidth, int spriteHeight, int horizontalSpriteCount, int verticalSpriteCount) {
			this.spritesetName = spritesetName;
			this.spriteWidth = spriteWidth;
			this.spriteHeight = spriteHeight;
			this.horizontalSpriteCount = horizontalSpriteCount;
			this.verticalSpriteCount = verticalSpriteCount;
			
			return this;
		}
		
		public PooledSpritesetAssetKey reset() {
			this.spritesetName = null;
			this.spriteWidth = 0;
			this.spriteHeight = 0;
			this.horizontalSpriteCount = 0;
			this.verticalSpriteCount = 0;
			
			return this;
		}
		
		@Override
		public String getSpritesetName() {
			return spritesetName;
		}

		@Override
		public int getSpriteWidth() {
			return spriteWidth;
		}

		@Override
		public int getSpriteHeight() {
			return spriteHeight;
		}

		@Override
		public int getHorizontalOffsetFor(int spriteIndex) {
			int horizontalIndex = spriteIndex % horizontalSpriteCount;
			return spriteWidth * horizontalIndex; // TODO: +1 ?
		}

		@Override
		public int getVerticalOffsetFor(int spriteIndex) {
			int verticalIndex = spriteIndex / horizontalSpriteCount;
			return spriteHeight * verticalIndex; // TODO: +1 ?
		}

		@Override
		public int getSpriteCount() {
			return horizontalSpriteCount * verticalSpriteCount;
		}
	}
	
}
