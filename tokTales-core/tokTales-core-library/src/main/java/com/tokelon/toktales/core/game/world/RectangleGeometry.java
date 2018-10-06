package com.tokelon.toktales.core.game.world;

import com.tokelon.toktales.core.game.model.IRectangle2f.IMutableRectangle2f;

public class RectangleGeometry implements IRectangleGeometry {

	private final IMutableRectangle2f rectangle;
	
	public RectangleGeometry(IMutableRectangle2f rectangle) {
		this.rectangle = rectangle;
	}
	
	
	@Override
	public IMutableRectangle2f getRectangle() {
		return rectangle;
	}

}
