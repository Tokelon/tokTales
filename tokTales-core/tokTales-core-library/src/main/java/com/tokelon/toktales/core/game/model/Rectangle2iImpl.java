package com.tokelon.toktales.core.game.model;

import com.tokelon.toktales.core.game.model.IRectangle2i.IMutableRectangle2i;

public class Rectangle2iImpl implements IMutableRectangle2i {

	private int left = 0;
	private int top = 0;
	private int right = 0;
	private int bottom = 0;
	
	
	@Override
	public int left() {
		return left;
	}

	@Override
	public int top() {
		return top;
	}

	@Override
	public int right() {
		return right;
	}

	@Override
	public int bottom() {
		return bottom;
	}
	
	@Override
	public int width() {
		return right - left;
	}

	@Override
	public int height() {
		return bottom - top;
	}
	

	@Override
	public int getLeft() {
		return left;
	}

	@Override
	public int getTop() {
		return left;
	}

	@Override
	public int getRight() {
		return right;
	}

	@Override
	public int getBottom() {
		return bottom;
	}

	@Override
	public int getWidth() {
		return right - left;
	}

	@Override
	public int getHeight() {
		return bottom - top;
	}

	
	@Override
	public boolean contains(int x, int y) {
		return left < right && top < bottom		// not empty rectangle
				&& x >= left && x < right && y >= top && y < bottom;
	}

	@Override
	public boolean contains(int left, int top, int right, int bottom) {
		return this.left < this.right && this.top < this.bottom 	// not empty rectangle
				&& left >= this.left && top >= this.top
				&& right <= this.right && bottom <= this.bottom;
	}
	
	@Override
	public boolean contains(IRectangle2i rectangle) {
		return this.left < this.right && this.top < this.bottom 	// not empty rectangle
				&& rectangle.left() >= this.left && rectangle.top() >= this.top
				&& rectangle.right() <= this.right && rectangle.bottom() <= this.bottom;
	}
	
	
	@Override
	public boolean intersects(int left, int top, int right, int bottom) {
		// if left == this.right they do NOT intersect
		// if this.left == right they also do NOT intersect
		// both because the right side is never part of the rectangle
		// same for the bottom side
		return left < this.right && this.left < right
				&& top < this.bottom && this.top < bottom;
	}
	
	@Override
	public boolean intersects(IRectangle2i rectangle) {
		// Same as above
		return rectangle.left() < this.right && this.left < rectangle.right()
				&& rectangle.top() < this.bottom && this.top < rectangle.bottom();
	}

	
	@Override
	public boolean isEmpty() {
		return left >= right && top >= bottom;
	}

	
	
	@Override
	public IMutableRectangle2i setLeft(int left) {
		this.left = left;
		return this;
	}

	@Override
	public IMutableRectangle2i setTop(int top) {
		this.top = top;
		return this;
	}

	@Override
	public IMutableRectangle2i setRight(int right) {
		this.right = right;
		return this;
	}

	@Override
	public IMutableRectangle2i setBottom(int bottom) {
		this.bottom = bottom;
		return this;
	}

	@Override
	public IMutableRectangle2i set(int left, int top, int right, int bottom) {
		this.left = left;
		this.top = top;
		this.right = right;
		this.bottom = bottom;
		return this;
	}

	@Override
	public IMutableRectangle2i set(IRectangle2i rectangle) {
		this.left = rectangle.left();
		this.top = rectangle.top();
		this.right = rectangle.right();
		this.bottom = rectangle.bottom();
		return this;
	}

	@Override
	public IMutableRectangle2i setWidth(int width) {
		this.right = this.left + width;
		return this;
	}

	@Override
	public IMutableRectangle2i setHeight(int height) {
		this.bottom = this.top + height;
		return this;
	}

	@Override
	public IMutableRectangle2i moveTo(int x, int y) {
		this.right = x + this.width();
		this.left = x;
		this.bottom = y + this.height();
		this.top = y;
		return this;
	}

	@Override
	public IMutableRectangle2i moveBy(int dx, int dy) {
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
		if(!(obj instanceof IRectangle2i)) {
			return false;
		}
		IRectangle2i that = (IRectangle2i) obj;
		
		return left == that.left() && top == that.top()
				&& right == that.right() && bottom == that.bottom();
	}
	
	@Override
	public int hashCode() {
		return 11 + left*7 + top*11 + right*17 + bottom*19;
	}
	
	
}
