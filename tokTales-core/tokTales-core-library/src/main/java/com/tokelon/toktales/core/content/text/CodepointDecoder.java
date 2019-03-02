package com.tokelon.toktales.core.content.text;

import java.io.InputStream;

import com.tokelon.toktales.core.content.manage.IAssetDecoder;
import com.tokelon.toktales.core.engine.content.ContentException;
import com.tokelon.toktales.core.util.INamedOptions;

public class CodepointDecoder implements IAssetDecoder<ICodepointAsset, ICodepointKey, INamedOptions> {

	
	@Override
	public ICodepointAsset decode(InputStream inputstream, ICodepointKey key) throws ContentException {
		int codepoint = key.getCodepoint();
		
		ITextureFont font = key.getFont();
		ICodepointAsset codepointAsset = font.getCodepointAsset(codepoint);
		return codepointAsset;
	}

	@Override
	public ICodepointAsset decode(InputStream inputstream, ICodepointKey key, INamedOptions options) throws ContentException {
		return decode(inputstream, key);
	}

}
