package com.tokelon.toktales.core.content.manage.font;

import com.tokelon.toktales.core.content.text.IFont;

public class FontAssetImpl implements IFontAsset {

	
	private final IFont font;

	public FontAssetImpl(IFont font) {
		this.font = font;
	}
	
	
	@Override
	public IFont getFont() {
		return font;
	}
	
	@Override
	public void dispose() {
		font.dispose();
	}

}
