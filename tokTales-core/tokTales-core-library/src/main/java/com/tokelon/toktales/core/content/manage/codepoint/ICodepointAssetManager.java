package com.tokelon.toktales.core.content.manage.codepoint;

import com.tokelon.toktales.core.content.manage.IAssetManager;
import com.tokelon.toktales.core.content.text.ITextureFont;
import com.tokelon.toktales.core.util.INamedOptions;

public interface ICodepointAssetManager extends IAssetManager<ICodepointAsset, ICodepointAssetKey, INamedOptions> {
	
	
	// Maybe add getCodepointAsset...() methods analogous to getAsset...() in IAssetManager
	public ICodepointAsset getCodepointAsset(ITextureFont font, int codepoint);
	public ICodepointAsset getCodepointAsset(ITextureFont font, int codepoint, INamedOptions options);
	
}