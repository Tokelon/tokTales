package com.tokelon.toktales.core.content.manage.codepoint;

import com.tokelon.toktales.core.content.manage.keys.IIdentifiableAssetKey;
import com.tokelon.toktales.core.content.text.ITextureFont;

public interface ICodepointAssetKey extends IIdentifiableAssetKey {

	
	public ITextureFont getFont();
	
	public int getCodepoint();
	
	public float getFontPixelHeight();
	
}
