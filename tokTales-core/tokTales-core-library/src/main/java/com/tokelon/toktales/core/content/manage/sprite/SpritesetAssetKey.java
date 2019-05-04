package com.tokelon.toktales.core.content.manage.sprite;

public class SpritesetAssetKey implements ISpritesetAssetKey {

	
	private final String spritesetName;
	private final int spriteWidth;
	private final int spriteHeight;
	private final int horizontalSpriteCount;
	private final int verticalSpriteCount;

	public SpritesetAssetKey(String spritesetName, int spriteWidth, int spriteHeight, int horizontalSpriteCount, int verticalSpriteCount) {
		this.spritesetName = spritesetName;
		this.spriteWidth = spriteWidth;
		this.spriteHeight = spriteHeight;
		this.horizontalSpriteCount = horizontalSpriteCount;
		this.verticalSpriteCount = verticalSpriteCount;
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


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + horizontalSpriteCount;
		result = prime * result + spriteHeight;
		result = prime * result + spriteWidth;
		result = prime * result + ((spritesetName == null) ? 0 : spritesetName.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		
		if (!(obj instanceof ISpritesetAssetKey)) {
			return false;
		}
		ISpritesetAssetKey other = (ISpritesetAssetKey) obj;
		
		if (getSpriteCount() != other.getSpriteCount()) {
			return false;
		}
		
		if (spriteHeight != other.getSpriteHeight()) {
			return false;
		}
		if (spriteWidth != other.getSpriteWidth()) {
			return false;
		}
		
		if (spritesetName == null) {
			if (other.getSpritesetName() != null) {
				return false;
			}
		} else if (!spritesetName.equals(other.getSpritesetName())) {
			return false;
		}
		
		return true;
	}

}
