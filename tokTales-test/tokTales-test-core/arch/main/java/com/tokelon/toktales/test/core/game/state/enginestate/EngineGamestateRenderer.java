package com.tokelon.toktales.test.core.game.state.enginestate;

import javax.inject.Inject;

import com.google.inject.assistedinject.Assisted;
import com.tokelon.toktales.core.game.state.render.AbstractGameStateRenderer;
import com.tokelon.toktales.core.render.texture.ITextureCoordinator;

public class EngineGamestateRenderer extends AbstractGameStateRenderer implements IEngineGamestateRenderer {

	
	private final IEngineGamestate gamestate;

	@Inject
	public EngineGamestateRenderer(ITextureCoordinator textureCoordinator, @Assisted IEngineGamestate gamestate) {
		super(textureCoordinator);
		
		this.gamestate = gamestate;
	}
	
	public IEngineGamestate getGamestate() {
		return gamestate;
	}

	
	@Override
	public void render() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void onSurfaceCreated() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void onSurfaceChanged() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void onSurfaceDestroyed() {
		// TODO Auto-generated method stub

	}

}
