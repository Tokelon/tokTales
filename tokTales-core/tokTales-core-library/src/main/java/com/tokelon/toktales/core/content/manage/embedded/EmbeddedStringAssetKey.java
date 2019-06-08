package com.tokelon.toktales.core.content.manage.embedded;

import java.nio.charset.StandardCharsets;

public class EmbeddedStringAssetKey implements IEmbeddedStringAssetKey {
	
	
	private final String content;

	public EmbeddedStringAssetKey(String content) {
		this.content = content;
	}
	
	
	@Override
	public byte[] getContent() {
		return content.getBytes(StandardCharsets.US_ASCII);
	}

	@Override
	public String getStringContent() {
		return content;
	}

}
