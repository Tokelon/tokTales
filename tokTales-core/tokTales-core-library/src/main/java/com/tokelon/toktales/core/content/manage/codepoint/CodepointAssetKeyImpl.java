package com.tokelon.toktales.core.content.manage.codepoint;

import com.tokelon.toktales.core.content.text.ITextureFont;

public class CodepointAssetKeyImpl implements ICodepointAssetKey {

	
	private final ITextureFont font;
	private final int codepoint;

	public CodepointAssetKeyImpl(ITextureFont font, int codepoint) {
		if(font == null) {
			throw new NullPointerException();
		}
		
		this.font = font;
		this.codepoint = codepoint;
	}
	
	
	@Override
	public ITextureFont getFont() {
		return font;
	}

	@Override
	public int getCodepoint() {
		return codepoint;
	}
	
	
	@Override
	public int hashCode() {
		return 13 + codepoint*37 + font.hashCode()*37;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj == this) {
			return true;
		}
		if(!(obj instanceof ICodepointAssetKey)) {
			return false;
		}
		ICodepointAssetKey that = (ICodepointAssetKey) obj;
		
		return this.font.equals(that.getFont()) && this.codepoint == that.getCodepoint();
	}

}
