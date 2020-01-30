package com.tokelon.toktales.core.screen.view;

import com.tokelon.toktales.core.game.model.ICamera;
import com.tokelon.toktales.core.game.model.IPoint2i;
import com.tokelon.toktales.core.game.model.IRectangle2f.IMutableRectangle2f;
import com.tokelon.toktales.core.game.world.IWorldGrid;

public class DefaultViewGridTransformer extends DefaultGridTransformer implements IViewGridTransformer {

	private final IViewTransformer viewTransformer;
	
	public DefaultViewGridTransformer(IWorldGrid grid, IViewTransformer viewTransformer) {
		super(grid, viewTransformer.getCurrentCamera());
		
		this.viewTransformer = viewTransformer;
	}

	@Override
	public ICamera getCamera() {
		// This is important because the camera of the view transformer can change anytime
		return viewTransformer.getCurrentCamera();
	}
	
	@Override
	public IViewTransformer getViewTransformer() {
		return viewTransformer;
	}

	
	@Override
	public float getScreenTileSize() {
		return getGridTileSize() * viewTransformer.getCameraToViewportScale();
	}


	@Override
	public void visibleScreenBoundsForTile(int wTileX, int wTileY, IMutableRectangle2f sTileBounds) {
		visibleWorldBoundsForTile(wTileX, wTileY, sTileBounds);
		viewTransformer.cameraToViewport(sTileBounds, sTileBounds);
	}

	@Override
	public void visibleScreenBoundsForTile(IPoint2i wTile, IMutableRectangle2f sTileBounds) {
		visibleWorldBoundsForTile(wTile, sTileBounds);
		viewTransformer.cameraToViewport(sTileBounds, sTileBounds);
	}


}
