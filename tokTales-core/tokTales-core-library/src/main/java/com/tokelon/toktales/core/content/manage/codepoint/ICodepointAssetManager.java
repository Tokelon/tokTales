package com.tokelon.toktales.core.content.manage.codepoint;

import com.tokelon.toktales.core.content.text.ITextureFont;
import com.tokelon.toktales.tools.assets.manager.IAssetManager;
import com.tokelon.toktales.tools.core.objects.options.INamedOptions;

public interface ICodepointAssetManager extends IAssetManager<ICodepointAsset, ICodepointAssetKey, INamedOptions> {
	
	
	// Maybe add getCodepointAsset...() methods analogous to getAsset...() in IAssetManager
	public ICodepointAsset getCodepointAsset(ITextureFont font, int codepoint, float fontPixelHeight);
	public ICodepointAsset getCodepointAsset(ITextureFont font, int codepoint, float fontPixelHeight, INamedOptions options);
	
}
