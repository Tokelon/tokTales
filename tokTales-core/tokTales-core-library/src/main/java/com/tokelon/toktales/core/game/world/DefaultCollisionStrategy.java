package com.tokelon.toktales.core.game.world;

import com.tokelon.toktales.core.game.model.IRectangle2f;

public class DefaultCollisionStrategy implements ICollisionStrategy {

	// TODO: Test collision for empty rectangles!
	
	@Override
	public boolean doRectanglesCollide(IRectangle2f first, IRectangle2f second) {
		// Do rectangles intersect
		// only use < and not <= because of the way rectangles work (right and bottom are not part of the rectangle)
		return first.getLeft() < second.getRight() && second.getLeft() < first.getRight()
				&& first.getTop() < second.getBottom() && second.getTop() < first.getBottom();
	}

	@Override
	public boolean doRectanglesCollide(float fLeft, float fTop, float fRight, float fBottom, float sLeft, float sTop, float sRight, float sBottom) {
		// Do rectangles intersect
		// only use < and not <= because of the way rectangles work (right and bottom are not part of the rectangle)
		return fLeft < sRight && sLeft < fRight
				&& fTop < sBottom && sTop < fBottom;
	}

}
