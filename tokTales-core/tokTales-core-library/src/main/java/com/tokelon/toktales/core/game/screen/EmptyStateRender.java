package com.tokelon.toktales.core.game.screen;

import javax.inject.Inject;

import com.tokelon.toktales.core.render.texture.ITextureCoordinator;

public class EmptyStateRender extends AbstractStateRender {

	
	@Inject
	public EmptyStateRender(ITextureCoordinator textureCoordinator) {
		super(textureCoordinator);
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