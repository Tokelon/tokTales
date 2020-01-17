package com.tokelon.toktales.extensions.core.game.states.localmap;

import com.tokelon.toktales.core.game.screen.IStateRender;
import com.tokelon.toktales.extensions.core.game.screen.IDebugRenderer;
import com.tokelon.toktales.extensions.core.game.screen.IEntityRenderer;
import com.tokelon.toktales.extensions.core.game.screen.IMapRenderer;
import com.tokelon.toktales.extensions.core.game.screen.IObjectRenderer;
import com.tokelon.toktales.extensions.core.game.screen.IPlayerRenderer;

public interface ILocalMapStateRenderer extends IStateRender {
	

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
