package com.tokelon.toktales.core.game.world;

import com.tokelon.toktales.core.game.model.IPolyline2f.IExtendablePolyline2f;

public interface IPolylineGeometry extends IWorldGeometry {

	public IExtendablePolyline2f getPolyline();
	
}
