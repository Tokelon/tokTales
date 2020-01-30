package com.tokelon.toktales.core.content.manage.codepoint;

import com.tokelon.toktales.core.content.text.IFont;
import com.tokelon.toktales.tools.assets.key.IIdentifiableAssetKey;

public interface ICodepointAssetKey extends IIdentifiableAssetKey {

	
	public IFont getFont();
	
	public int getCodepoint();
	
	public float getFontPixelHeight();
	
}
