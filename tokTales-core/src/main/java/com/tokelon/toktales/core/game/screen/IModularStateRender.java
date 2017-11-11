package com.tokelon.toktales.core.game.screen;

import java.util.Map;

import com.tokelon.toktales.core.game.states.IGameState;

public interface IModularStateRender extends IStateRender {

	// TODO: Probably obsolete since managed renderer were added in IStateRender
	
	//public ISurface getCurrentSurface();	// getter for surface ?
	//public ISurfaceHandler getSurfaceHandler();
	public IGameState getGamestate();
	
	
	public void addSegmentRenderer(String name, ISegmentRenderer renderer);
	
	public ISegmentRenderer getSegmentRenderer(String name);

	public void removeSegmentRenderer(String name);

	
	public Map<String, ISegmentRenderer> getRenderers();
	
}
