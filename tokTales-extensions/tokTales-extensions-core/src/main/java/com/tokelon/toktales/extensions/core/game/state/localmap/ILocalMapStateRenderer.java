package com.tokelon.toktales.extensions.core.game.state.localmap;

import com.tokelon.toktales.core.game.state.render.IGameStateRenderer;
import com.tokelon.toktales.extensions.core.game.renderer.IDebugRenderer;
import com.tokelon.toktales.extensions.core.game.renderer.IEntityRenderer;
import com.tokelon.toktales.extensions.core.game.renderer.IMapRenderer;
import com.tokelon.toktales.extensions.core.game.renderer.IObjectRenderer;
import com.tokelon.toktales.extensions.core.game.renderer.IPlayerRenderer;

public interface ILocalMapStateRenderer extends IGameStateRenderer {
	

	//public void enableSegmentRendering(String segmentName, boolean enabled);
	
	public void setDebugRendering(boolean enabled);
	
	public boolean isDebugRenderingEnabled();
	
	
	public IDebugRenderer getDebugRenderer();
	
	public IEntityRenderer getEntityRenderer();
	
	public IMapRenderer getMapRenderer();
	
	public IObjectRenderer getObjectRenderer();
	
	public IPlayerRenderer getPlayerRenderer();
	
	

	public interface ILocalMapStateRendererFactory {
		
		public ILocalMapStateRenderer create(ILocalMapGamestate gamestate);
	}
	
}
