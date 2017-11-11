package com.tokelon.toktales.extens.def.core.render;

import com.tokelon.toktales.core.content.IRGBAColor;
import com.tokelon.toktales.core.render.IChunkRenderer;
import com.tokelon.toktales.extens.def.core.game.model.ITextBox;

public interface ITextRenderer extends IChunkRenderer {

	
	public void drawTextBox(ITextBox textBox, IRGBAColor color);
	
}
