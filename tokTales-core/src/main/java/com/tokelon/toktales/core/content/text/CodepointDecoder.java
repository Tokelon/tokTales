package com.tokelon.toktales.core.content.text;

import java.io.InputStream;

import com.tokelon.toktales.core.content.manage.IAssetDecoder;
import com.tokelon.toktales.core.util.INamedOptions;

public class CodepointDecoder implements IAssetDecoder<ICodepointAsset, ICodepointKey, INamedOptions> {

	
	@Override
	public ICodepointAsset decode(InputStream inputstream, ICodepointKey key) {
		return decode(inputstream, key, null);
	}

	@Override
	public ICodepointAsset decode(InputStream inputstream, ICodepointKey key, INamedOptions options) {
		int codepoint = key.getCodepoint();
		
		ITextureFont font = key.getFont();
		ICodepointAsset codepointAsset = font.getCodepointAsset(codepoint);
		return codepointAsset;
	}

}
