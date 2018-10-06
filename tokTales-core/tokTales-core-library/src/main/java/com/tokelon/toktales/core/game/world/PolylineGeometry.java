package com.tokelon.toktales.core.game.world;

import com.tokelon.toktales.core.game.model.IPolyline2f.IExtendablePolyline2f;

public class PolylineGeometry implements IPolylineGeometry {

	private final IExtendablePolyline2f polyline;
	
	public PolylineGeometry(IExtendablePolyline2f polyline) {
		this.polyline = polyline;
	}
	
	
	@Override
	public IExtendablePolyline2f getPolyline() {
		return polyline;
	}

}
