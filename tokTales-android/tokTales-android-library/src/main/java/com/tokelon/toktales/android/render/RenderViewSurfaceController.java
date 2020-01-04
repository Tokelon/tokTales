package com.tokelon.toktales.android.render;

import com.tokelon.toktales.core.engine.render.ISurfaceController;

public class RenderViewSurfaceController implements ISurfaceController {


	private final IRenderView renderView;

	public RenderViewSurfaceController(IRenderView renderView) {
		this.renderView = renderView;
	}
	
	
	@Override
	public void queueEvent(Runnable event) {
		renderView.queueEvent(event);
	}

}
