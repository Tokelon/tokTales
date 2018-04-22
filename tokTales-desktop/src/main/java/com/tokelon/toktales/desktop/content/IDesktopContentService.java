package com.tokelon.toktales.desktop.content;

import com.tokelon.toktales.core.content.IBitmap;
import com.tokelon.toktales.core.engine.content.IContentService;
import com.tokelon.toktales.core.game.model.IRectangle2i;
import com.tokelon.toktales.core.render.IRenderTexture;

public interface IDesktopContentService extends IContentService {

	
	public IRenderTexture cropTexture(IRenderTexture texture, IRectangle2i bounds);
	
	public IBitmap cropBitmap(IBitmap bitmap, IRectangle2i bounds);
	
}
