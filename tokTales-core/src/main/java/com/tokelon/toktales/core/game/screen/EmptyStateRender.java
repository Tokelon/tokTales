package com.tokelon.toktales.core.game.screen;

import javax.inject.Inject;

import com.tokelon.toktales.core.engine.render.ISurfaceHandler;

public class EmptyStateRender extends AbstractStateRender {

	
	@Inject
	public EmptyStateRender(ISurfaceHandler surfaceHandler) {
		super(surfaceHandler);
	}

	
	@Override
	protected void onSurfaceCreated() {	}

	@Override
	protected void onSurfaceChanged() { }

	@Override
	protected void onSurfaceDestroyed() { }

	
	@Override
	public void renderCall(String layerName, double stackPosition) { }

	@Override
	public String getDescription() {
		return "EmptyStateRender";
	}
	
}