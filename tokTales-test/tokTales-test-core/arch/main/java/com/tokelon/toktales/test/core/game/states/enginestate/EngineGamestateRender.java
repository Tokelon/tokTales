package com.tokelon.toktales.test.core.game.states.enginestate;

import javax.inject.Inject;

import com.google.inject.assistedinject.Assisted;
import com.tokelon.toktales.core.game.screen.AbstractStateRender;
import com.tokelon.toktales.core.render.ITextureCoordinator;

public class EngineGamestateRender extends AbstractStateRender implements IEngineGamestateRender {

	
	private final IEngineGamestate gamestate;

	@Inject
	public EngineGamestateRender(ITextureCoordinator textureCoordinator, @Assisted IEngineGamestate gamestate) {
		super(textureCoordinator);
		
		this.gamestate = gamestate;
	}
	
	public IEngineGamestate getGamestate() {
		return gamestate;
	}

	
	@Override
	public void renderCall(String layerName, double stackPosition) {
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
