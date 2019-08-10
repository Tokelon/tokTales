package com.tokelon.toktales.core.content.manage.codepoint;

import com.tokelon.toktales.core.content.text.ITextureFont;

public class CodepointAssetKeyImpl implements ICodepointAssetKey {

	
	private final ITextureFont font;
	private final int codepoint;
	private final float fontPixelHeight;

	public CodepointAssetKeyImpl(ITextureFont font, int codepoint, float fontPixelHeight) {
		if(font == null) {
			throw new NullPointerException();
		}
		
		this.font = font;
		this.codepoint = codepoint;
		this.fontPixelHeight = fontPixelHeight;
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
	public float getFontPixelHeight() {
		return fontPixelHeight;
	}
	
	
	@Override
	public int hashCode() {
		return 13 + codepoint*37 + font.hashCode()*37 + (int)(fontPixelHeight*13);
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
		
		return this.font.equals(that.getFont()) && this.codepoint == that.getCodepoint() && this.fontPixelHeight == that.getFontPixelHeight(); // Float comparison - use error value?
	}

}
