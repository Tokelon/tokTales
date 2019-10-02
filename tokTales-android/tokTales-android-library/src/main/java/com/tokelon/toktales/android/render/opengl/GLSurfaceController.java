package com.tokelon.toktales.android.render.opengl;

import com.tokelon.toktales.core.engine.render.ISurfaceController;

public class GLSurfaceController implements ISurfaceController {


	private final IGLRenderView renderView;

	public GLSurfaceController(IGLRenderView renderView) {
		this.renderView = renderView;
	}
	
	
	@Override
	public void queueEvent(Runnable event) {
		renderView.queueEvent(event);
	}

}
