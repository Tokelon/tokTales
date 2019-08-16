package com.tokelon.toktales.core.game.model;

import com.tokelon.toktales.core.game.model.IRectangle2f.IMutableRectangle2f;

public class Rectangle2fImpl implements IMutableRectangle2f {

	/* Maybe consider refactoring for float comparison/equality
	 * 
	 */
	
	private float left = 0f;
	private float top = 0f;
	private float right = 0f;
	private float bottom = 0f;
	
	
	@Override
	public float left() {
		return left;
	}

	@Override
	public float top() {
		return top;
	}

	@Override
	public float right() {
		return right;
	}

	@Override
	public float bottom() {
		return bottom;
	}

	@Override
	public float width() {
		return right - left;
	}

	@Override
	public float height() {
		return bottom - top;
	}

	
	@Override
	public float getLeft() {
		return left;
	}

	@Override
	public float getTop() {
		return top;
	}

	@Override
	public float getRight() {
		return right;
	}

	@Override
	public float getBottom() {
		return bottom;
	}

	@Override
	public float getWidth() {
		return right - left;
	}

	@Override
	public float getHeight() {
		return bottom - top;
	}

	
	@Override
	public boolean contains(float x, float y) {
		return left < right && top < bottom		// not empty rectangle
				&& x >= left && x < right && y >= top && y < bottom;
	}

	@Override
	public boolean contains(float left, float top, float right, float bottom) {
		return this.left < this.right && this.top < this.bottom 	// not empty rectangle
				&& left >= this.left && top >= this.top
				&& right <= this.right && bottom <= this.bottom;
	}

	@Override
	public boolean contains(IRectangle2f rectangle) {
		return this.left < this.right && this.top < this.bottom 	// not empty rectangle
				&& rectangle.left() >= this.left && rectangle.top() >= this.top
				&& rectangle.right() <= this.right && rectangle.bottom() <= this.bottom;
	}

	@Override
	public boolean intersects(float left, float top, float right, float bottom) {
		// if left == this.right they do NOT intersect
		// if this.left == right they also do NOT intersect
		// both because the right side is never part of the rectangle
		// same for the bottom side
		return this.left < this.right && this.top < this.bottom // not empty
				&& left < right && top < bottom
				&& left < this.right && this.left < right
				&& top < this.bottom && this.top < bottom;
	}

	@Override
	public boolean intersects(IRectangle2f rectangle) {
		// Same as above
		return this.left < this.right && this.top < this.bottom // not empty
				&& !rectangle.isEmpty()
				&& rectangle.left() < this.right && this.left < rectangle.right()
				&& rectangle.top() < this.bottom && this.top < rectangle.bottom();
	}

	@Override
	public boolean isEmpty() {
		return left >= right && top >= bottom;	// Use epsilon?
	}
	
	

	@Override
	public IMutableRectangle2f setLeft(float left) {
		this.left = left;
		return this;
	}

	@Override
	public IMutableRectangle2f setTop(float top) {
		this.top = top;
		return this;
	}

	@Override
	public IMutableRectangle2f setRight(float right) {
		this.right = right;
		return this;
	}

	@Override
	public IMutableRectangle2f setBottom(float bottom) {
		this.bottom = bottom;
		return this;
	}

	@Override
	public IMutableRectangle2f set(float left, float top, float right, float bottom) {
		this.left = left;
		this.top = top;
		this.right = right;
		this.bottom = bottom;
		return this;
	}

	@Override
	public IMutableRectangle2f set(IRectangle2f rectangle) {
		this.left = rectangle.left();
		this.top = rectangle.top();
		this.right = rectangle.right();
		this.bottom = rectangle.bottom();
		return this;
	}

	@Override
	public IMutableRectangle2f setWidth(float width) {
		this.right = this.left + width;
		return this;
	}

	@Override
	public IMutableRectangle2f setHeight(float height) {
		this.bottom = this.top + height;
		return this;
	}

	@Override
	public IMutableRectangle2f moveTo(float x, float y) {
		this.right = x + this.width();
		this.left = x;
		this.bottom = y + this.height();
		this.top = y;
		return this;
	}

	@Override
	public IMutableRectangle2f moveBy(float dx, float dy) {
		this.left += dx;
		this.right += dx;
		this.top += dy;
		this.bottom += dy;
		return this;
	}
	
	
	@Override
	public boolean equals(Object obj) {
		if(this == obj) {
			return true;
		}
		if(!(obj instanceof IRectangle2f)) {
			return false;
		}
		IRectangle2f that = (IRectangle2f) obj;
		
		// TODO: Choose error to compare with?
		return left == that.left() && top == that.top()
				&& right == that.right() && bottom == that.bottom();
	}
	
	@Override
	public int hashCode() {
		return (int) (11 + left*7 + top*11 + right*17 + bottom*19);
	}
	

}
