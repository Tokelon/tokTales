package com.tokelon.toktales.desktop.lwjgl.render;

import com.tokelon.toktales.core.engine.render.ISurfaceController;

public class GLSurfaceController implements ISurfaceController {


	@Override
	public void queueEvent(Runnable event) {
		// TODO: Something else needed?
		event.run();
	}

}
