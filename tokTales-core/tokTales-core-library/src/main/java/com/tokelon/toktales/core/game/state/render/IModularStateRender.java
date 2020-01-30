package com.tokelon.toktales.core.game.state.render;

import java.util.Map;

import com.tokelon.toktales.core.game.state.IGameState;

public interface IModularStateRender extends IStateRender {
	
	public IGameState getGamestate(); // TODO: Remove gamestate dependency if possible
	
	
	public void addSegmentRenderer(String name, ISegmentRenderer renderer); // What if name is taken? Return boolean?
	public ISegmentRenderer getSegmentRenderer(String name);
	public boolean hasSegmentRenderer(String name);
	public void removeSegmentRenderer(String name);

	public Map<String, ISegmentRenderer> getRenderers();
	
}