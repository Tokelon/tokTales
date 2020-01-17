package com.tokelon.toktales.extensions.core.render;

import com.tokelon.toktales.core.content.graphics.IRGBAColor;
import com.tokelon.toktales.core.render.IChunkRenderer;
import com.tokelon.toktales.extensions.core.game.model.ITextBox;

public interface ITextRenderer extends IChunkRenderer {

	
	public void drawTextBox(ITextBox textBox, IRGBAColor color);
	
}
