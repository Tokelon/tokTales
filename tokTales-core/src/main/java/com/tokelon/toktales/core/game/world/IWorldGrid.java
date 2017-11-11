package com.tokelon.toktales.core.game.world;

import com.tokelon.toktales.core.game.model.IPoint2f;
import com.tokelon.toktales.core.game.model.IPoint2i;
import com.tokelon.toktales.core.game.model.IRectangle2f;
import com.tokelon.toktales.core.game.model.IPoint2i.IMutablePoint2i;
import com.tokelon.toktales.core.game.model.IRectangle2f.IMutableRectangle2f;
import com.tokelon.toktales.core.game.model.IRectangle2i.IMutableRectangle2i;

public interface IWorldGrid {
	
	public float getTileSize();
	
	
	public int worldToTile(float w);
	
	// Maybe replace IMutablePoint2i with IMutableTilePosition or IMutableGridPosition
	public void worldToTile(IPoint2f worldCoords, IMutablePoint2i result);
	public void worldToTile(float worldx, float worldy, IMutablePoint2i result);

	public void worldToGrid(IRectangle2f worldRect, IMutableRectangle2i gridRect);
	
	
	
	public float tileToWorld(int tileIndex);

	public float tileCenterToWorld(int tileIndex);
	
	
	
	public float gridShiftPositive(float worldCoord);
	public float gridShiftNegative(float worldCoord);
	
	
	public void tileBounds(IPoint2i tilePosition, IMutableRectangle2f result);
	
	public float tileWorldStart(float worldCoord);
	
}
