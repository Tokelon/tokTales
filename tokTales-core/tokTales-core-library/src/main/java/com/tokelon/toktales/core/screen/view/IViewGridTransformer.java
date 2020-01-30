package com.tokelon.toktales.core.screen.view;

import com.tokelon.toktales.core.game.model.IPoint2i;
import com.tokelon.toktales.core.game.model.IRectangle2f.IMutableRectangle2f;

/** Transforms between coordinates, pixels and grid positions.
 * Uses a grid, a camera and a view transformer.
 */
public interface IViewGridTransformer extends IGridTransformer {

	public IViewTransformer getViewTransformer();

	
	public float getScreenTileSize();

	
	public void visibleScreenBoundsForTile(int wTileX, int wTileY, IMutableRectangle2f sTileBounds);
	public void visibleScreenBoundsForTile(IPoint2i wTile, IMutableRectangle2f sTileBounds);
	
	
}
