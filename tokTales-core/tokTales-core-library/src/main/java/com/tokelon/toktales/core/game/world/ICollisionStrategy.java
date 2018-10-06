package com.tokelon.toktales.core.game.world;

import com.tokelon.toktales.core.game.model.IRectangle2f;


public interface ICollisionStrategy {

	
	public boolean doRectanglesCollide(IRectangle2f first, IRectangle2f second);
	
	public boolean doRectanglesCollide(float fLeft, float fTop, float fRight, float fBottom, float sLeft, float sTop, float sRight, float sBottom);
	
}
