package com.tokelon.toktales.core.content.manage.codepoint;

import java.io.InputStream;

import com.tokelon.toktales.core.content.text.ICodepoint;
import com.tokelon.toktales.core.content.text.ITextureFont;
import com.tokelon.toktales.core.engine.content.ContentException;
import com.tokelon.toktales.core.util.options.INamedOptions;

public class CodepointAssetDecoder implements ICodepointAssetDecoder {

	
	@Override
	public ICodepointAsset decode(InputStream inputstream, ICodepointAssetKey key) throws ContentException {
		int codepoint = key.getCodepoint();
		
		ITextureFont font = key.getFont();
		ICodepoint assetCodepoint = font.getCodepoint(codepoint);
		return new CodepointAsset(assetCodepoint);
	}

	@Override
	public ICodepointAsset decode(InputStream inputstream, ICodepointAssetKey key, INamedOptions options) throws ContentException {
		return decode(inputstream, key);
	}

}
