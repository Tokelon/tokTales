package com.tokelon.toktales.core.game.state.render;

import java.util.Map;

import com.tokelon.toktales.core.render.renderer.ISingleRenderer;

public interface IModularGameStateRenderer extends IGameStateRenderer {
	//public IGameState getGamestate(); // Needed?
	
	
	public void addRenderer(String name, ISingleRenderer renderer); // What if name is taken? Return boolean?

	public ISingleRenderer getRenderer(String name);

	public boolean hasRenderer(String name);

	public void removeRenderer(String name);


	public Map<String, ISingleRenderer> getRenderers();
	
}
