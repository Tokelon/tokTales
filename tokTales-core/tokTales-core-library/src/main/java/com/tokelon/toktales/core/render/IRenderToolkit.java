package com.tokelon.toktales.core.render;

import com.tokelon.toktales.core.content.graphics.IRGBAColor;
import com.tokelon.toktales.tools.core.objects.params.IParams;

public interface IRenderToolkit {

	public void clearSurface(IRGBAColor clearColor);
	
	
	public interface IRenderToolkitFactory {
		
		public IRenderToolkit newRenderToolkit(IParams params);
	}
	
}
