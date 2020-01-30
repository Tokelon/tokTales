package com.tokelon.toktales.extensions.core.game.model;

import com.tokelon.toktales.core.content.graphics.IRGBAColor;
import com.tokelon.toktales.core.content.text.ITextureFont;

public interface IScreenDialog extends ITextBox {

	public IRGBAColor getTextColor();
	public float getTextSize();
	
	public IRGBAColor getBorderColor();
	public float getBorderSize();
	
	public IRGBAColor getBackgroundColor();

	
	
	// TODO: Implement with char meta for each char separate
	public interface ICharMeta {
		
		// Either have offset or position
		//public float getPositionX();
		public float getOffsetX();
		public float getOffsetY();
		
		public void setOffsetX(float offsetx);
		public void setOffsetY(float offsety);
		
		public ITextureFont getFont();
		public void setFont(ITextureFont font);
		
		public float getSize();
		public void setSize(float size);

		public IRGBAColor getColor();
		public void setColor(IRGBAColor color);
	}
	
}
