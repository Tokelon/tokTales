package com.tokelon.toktales.core.game.model.map;

import com.tokelon.toktales.core.util.AttributedImpl;

public class MapLayerImpl extends AttributedImpl implements IMapLayer {

	private final String name;
	
	public MapLayerImpl(String name) {
		this.name = name;
	}

	
	@Override
	public String getName() {
		return name;
	}
	

}
