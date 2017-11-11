package com.tokelon.toktales.core.game.world;

import com.tokelon.toktales.core.game.model.IPoint2f;
import com.tokelon.toktales.core.game.model.IPoint2i;
import com.tokelon.toktales.core.game.model.IRectangle2f;
import com.tokelon.toktales.core.game.model.IPoint2i.IMutablePoint2i;
import com.tokelon.toktales.core.game.model.IRectangle2f.IMutableRectangle2f;
import com.tokelon.toktales.core.game.model.IRectangle2i.IMutableRectangle2i;

public class WorldGrid implements IWorldGrid {

	
	private final float mTileSize;
	
	public WorldGrid(float tileSize) {
		mTileSize = tileSize;
	}
	
	
	@Override
	public float getTileSize() {
		return mTileSize;
	}
	


	@Override
	public int worldToTile(float w) {
		int tileIndex;
		if(w < 0) {
			tileIndex = (int) ((w / mTileSize) - 1);
		}
		else {
			tileIndex = (int) (w / mTileSize);
		}
		
		return tileIndex;
	}
	
	@Override
	public void worldToTile(IPoint2f worldCoords, IMutablePoint2i result) {
		worldToTile(worldCoords.x(), worldCoords.y(), result);
	}
	
	@Override
	public void worldToTile(float worldx, float worldy, IMutablePoint2i result) {
		int tileX;
		if(worldx < 0) {
			tileX = (int) ((worldx / mTileSize) - 1);
		}
		else {
			tileX = (int) (worldx / mTileSize);
		}
		
		int tileY;
		if(worldy < 0) {
			tileY = (int) ((worldy / mTileSize) - 1);
		}
		else {
			tileY = (int) (worldy / mTileSize);
		}
		
		result.set(tileX, tileY);
	}
	
	
	@Override
	public void worldToGrid(IRectangle2f worldRect, IMutableRectangle2i gridRect) {
		/* World coordinates are 0 indexed so we subtract 1 from the positive sides
		 */
		gridRect.set(worldToTile(worldRect.left())
				, worldToTile(worldRect.top())
				, worldToTile(worldRect.right() - 1)
				, worldToTile(worldRect.bottom() - 1)
				);
		
		if(worldRect.left() == worldRect.right()) {
			gridRect.setRight(gridRect.left());
		}
		
		if(worldRect.top() == worldRect.bottom()) {
			gridRect.setBottom(gridRect.top());
		}
	}
	

	
	@Override
	public float tileToWorld(int tileIndex) {
		return mTileSize * tileIndex;
	}

	@Override
	public float tileCenterToWorld(int tileIndex) {
		return mTileSize * tileIndex + mTileSize / 2.0f;
	}
	

	@Override
	public float gridShiftPositive(float worldCoord) {
		float res;
		if(worldCoord < 0) {
			res = mTileSize - (-worldCoord % mTileSize);
		}
		else {
			res = worldCoord % mTileSize;
		}
		
		return res;
	}

	@Override
	public float gridShiftNegative(float worldCoord) {
		float res;
		if(worldCoord < 0) {
			res = -worldCoord % mTileSize; 
		}
		else {
			res = mTileSize - (worldCoord % mTileSize);
		}
		
		return res;
	}
	
	
	@Override
	public void tileBounds(IPoint2i tilePosition, IMutableRectangle2f result) {
		result.set(mTileSize * tilePosition.x(), mTileSize * (tilePosition.y() + 1), mTileSize * (tilePosition.x() + 1), mTileSize * tilePosition.y());
	}
	
	
	@Override
	public float tileWorldStart(float worldCoord) {
		int tileIndex = worldToTile(worldCoord);
		return tileToWorld(tileIndex);
	}
	

}
