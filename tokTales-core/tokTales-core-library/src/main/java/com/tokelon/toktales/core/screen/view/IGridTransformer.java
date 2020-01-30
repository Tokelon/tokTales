package com.tokelon.toktales.core.screen.view;

import com.tokelon.toktales.core.game.model.ICamera;
import com.tokelon.toktales.core.game.model.IPoint2i;
import com.tokelon.toktales.core.game.model.IRectangle2f.IMutableRectangle2f;
import com.tokelon.toktales.core.game.world.IWorldGrid;

/** Transforms between coordinates and grid positions.
 * Uses a grid and a camera.
 * 
 */
public interface IGridTransformer {

	// Add 'current' modifier?
	public IWorldGrid getGrid();
	public ICamera getCamera();
	

	public float getGridTileSize();

	public float getCameraShiftX();
	public float getCameraShiftY();
	
	public int getCameraGridPositionX();
	public int getCameraGridPositionY();
	
	
	public void visibleWorldBoundsForTile(int wTileX, int wTileY, IMutableRectangle2f wTileBounds);
	public void visibleWorldBoundsForTile(IPoint2i wTile, IMutableRectangle2f wTileBounds);
	
	
	//public void getVisibleWorldBoundsForCameraTile(int cameraTileX, int cameraTileY, WRectangle tileWorldBounds);
	

}
