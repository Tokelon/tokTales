package com.tokelon.toktales.core.content;

import com.tokelon.toktales.core.content.graphics.IBitmap;
import com.tokelon.toktales.core.game.model.IRectangle2i;
import com.tokelon.toktales.core.render.ITexture;

public interface IContentUtils {


	public ITexture cropTexture(ITexture texture, IRectangle2i bounds);
	
	public IBitmap cropBitmap(IBitmap bitmap, IRectangle2i bounds);
	
}
