package com.tokelon.toktales.core.content.text;

import com.tokelon.toktales.core.content.manage.IAssetManager;
import com.tokelon.toktales.core.util.INamedOptions;

public interface ICodepointManager extends IAssetManager<ICodepointAsset, ICodepointKey, INamedOptions> {
	
	
	// Maybe add getCodepointAsset...() methods analogous to getAsset...() in IAssetManager
	public ICodepointAsset getCodepointAsset(ITextureFont font, int codepoint);
	public ICodepointAsset getCodepointAsset(ITextureFont font, int codepoint, INamedOptions options);
	
}
