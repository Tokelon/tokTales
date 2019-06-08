package com.tokelon.toktales.core.content.manage.embedded;

public class EmbeddedAssetKey implements IEmbeddedAssetKey {
	
	
	private final byte[] content;

	public EmbeddedAssetKey(byte[] content) {
		this.content = content;
	}
	
	
	@Override
	public byte[] getContent() {
		return content;
	}

}
