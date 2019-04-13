package com.tokelon.toktales.core.content.manage.font;

import com.tokelon.toktales.core.content.text.ITextureFont;

public class TextureFontAssetImpl implements ITextureFontAsset {

	
	private final ITextureFont font;

	public TextureFontAssetImpl(ITextureFont font) {
		this.font = font;
	}
	
	
	@Override
	public ITextureFont getFont() {
		return font;
	}
	
	@Override
	public void dispose() {
		font.dispose();
	}

}
