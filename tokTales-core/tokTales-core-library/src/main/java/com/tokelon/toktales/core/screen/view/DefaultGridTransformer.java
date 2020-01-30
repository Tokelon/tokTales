package com.tokelon.toktales.core.screen.view;

import com.tokelon.toktales.core.game.model.ICamera;
import com.tokelon.toktales.core.game.model.IPoint2i;
import com.tokelon.toktales.core.game.model.IRectangle2f.IMutableRectangle2f;
import com.tokelon.toktales.core.game.world.IWorldGrid;

public class DefaultGridTransformer implements IGridTransformer {

	private final IWorldGrid grid;
	private final ICamera camera;
	
	public DefaultGridTransformer(IWorldGrid grid, ICamera camera) {
		this.grid = grid;
		this.camera = camera;
	}
	
	
	
	@Override
	public IWorldGrid getGrid() {
		return grid;
	}

	@Override
	public ICamera getCamera() {
		return camera;
	}

	
	@Override
	public float getGridTileSize() {
		return getGrid().getTileSize();
	}
	
	
	@Override
	public float getCameraShiftX() {
		float camerax = getCamera().getRawWorldX();
		
		float shiftx = getGrid().gridShiftPositive(camerax);
		
		return shiftx;
	}

	@Override
	public float getCameraShiftY() {
		float cameray = getCamera().getRawWorldY();
		
		float shifty = getGrid().gridShiftPositive(cameray);
		
		return shifty;
	}

	
	@Override
	public int getCameraGridPositionX() {
		return getGrid().worldToTile(getCamera().getRawWorldX());
	}

	@Override
	public int getCameraGridPositionY() {
		return getGrid().worldToTile(getCamera().getRawWorldY());
	}

	
	@Override
	public void visibleWorldBoundsForTile(int wTileX, int wTileY, IMutableRectangle2f wTileBounds) {
		visibleWorldBoundsForCameraTile(wTileX - getCameraGridPositionX(), wTileY - getCameraGridPositionY(), wTileBounds);
	}
	
	@Override
	public void visibleWorldBoundsForTile(IPoint2i wTile, IMutableRectangle2f wTileBounds) {
		visibleWorldBoundsForCameraTile(wTile.x() - getCameraGridPositionX(), wTile.y() - getCameraGridPositionY(), wTileBounds);
	}

	

	protected void visibleWorldBoundsForCameraTile(int cameraTileX, int cameraTileY, IMutableRectangle2f tileWorldBounds) {
		
		int camGridLeft = getGrid().worldToTile(getCamera().getRawWorldX());
		int camGridRight = getGrid().worldToTile(getCamera().getRawWorldX() + getCamera().getWidth());
		float camShiftX = getCameraShiftX();
		
		int tileX = cameraTileX + camGridLeft;
		if(tileX < camGridLeft || tileX > camGridRight) {
			tileWorldBounds.set(0f, 0f, 0f, 0f);
		}
		else if(tileX == camGridLeft) {
			tileWorldBounds.setLeft(camShiftX);
			tileWorldBounds.setRight(getGrid().getTileSize());
		}
		else if(tileX == camGridRight) {
			tileWorldBounds.setLeft(0f);
			tileWorldBounds.setRight(getGrid().gridShiftPositive(getCamera().getRawWorldX() + getCamera().getWidth())); //camShiftX
		}
		else {
			tileWorldBounds.setLeft(0f);
			tileWorldBounds.setRight(getGrid().getTileSize());
		}
		
		
		
		int camGridTop = getGrid().worldToTile(getCamera().getRawWorldY());
		int camGridBottom = getGrid().worldToTile(getCamera().getRawWorldY() + getCamera().getHeight());
		float camShiftY = getCameraShiftY();

		int tileY = cameraTileY + camGridTop;
		if(tileY < camGridTop || tileY > camGridBottom) {
			tileWorldBounds.set(0f, 0f, 0f, 0f);
		}
		else if(tileY == camGridTop) {
			tileWorldBounds.setTop(camShiftY);
			tileWorldBounds.setBottom(getGrid().getTileSize());
		}
		else if(tileY == camGridBottom) {
			tileWorldBounds.setTop(0f);
			tileWorldBounds.setBottom(getGrid().gridShiftPositive(getCamera().getRawWorldY() + getCamera().getHeight()));	//camShiftY
		}
		else {
			tileWorldBounds.setTop(0f);
			tileWorldBounds.setBottom(getGrid().getTileSize());
		}
		
	}
	
	
}
