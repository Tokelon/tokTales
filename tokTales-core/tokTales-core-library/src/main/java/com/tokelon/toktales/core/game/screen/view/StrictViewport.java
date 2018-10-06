package com.tokelon.toktales.core.game.screen.view;

import com.tokelon.toktales.core.game.model.IPoint2f;
import com.tokelon.toktales.core.game.model.IRectangle2f;
import com.tokelon.toktales.core.game.model.Rectangle2fImpl;
import com.tokelon.toktales.core.game.model.IPoint2f.IMutablePoint2f;
import com.tokelon.toktales.core.game.model.IRectangle2f.IMutableRectangle2f;

public class StrictViewport implements IScreenViewport {

	// TODO: Change back to Rectange2iImpl and implement asRectangle2i() method
	private final Rectangle2fImpl bounds = new Rectangle2fImpl();
	
	
	public void setSize(int width, int height) {
		bounds.setWidth(width);
		bounds.setHeight(height);
	}
	
	public void setOffset(int hOffset, int vOffset) {
		bounds.set(
					hOffset,
					vOffset,
					hOffset + (int) getWidth(),
					vOffset + (int) getHeight()
		);
	}


	/** Adjusts one of the viewport's sizes to match the given aspect ratio.<br><br>
	 * 
	 * Note that the new size will be rounded up to the nearest integer. If you need more accurate results use {@link StrictViewport#setSize(int, int)}.<br>
	 * Also: Setting the aspect ratio does not change the viewport orientation.
	 * 
	 * @param ratio A value >= 1.0.
	 */
	public void setAspectRatio(float ratio) {
		int newWidth, newHeight;
		if(getOrientation() == ORIENTATION_LANDSCAPE) {
			newWidth = (int) Math.ceil(ratio * getHeight());
			newHeight = (int) getHeight();
		}
		else {
			newWidth = (int) getWidth();
			newHeight = (int) Math.ceil(ratio * getWidth());
		}
		
		setSize(newWidth, newHeight);
	}
	

	/** Adjusts the viewport to the given orientation by flipping around the sizes if necessary.
	 * 
	 * @param orientation
	 */
	public void setOrientation(int orientation) {
		if(orientation == ORIENTATION_LANDSCAPE && getOrientation() != ORIENTATION_LANDSCAPE
				|| orientation == ORIENTATION_PORTRAIT && getOrientation() != ORIENTATION_PORTRAIT) {
			
			setSize((int)getHeight(), (int)getWidth());
		}
	}
	

	/** Centers the viewport to the rectangle (0, 0 -- maxHeight, maxWidth), but retains the original offsets (which will be added to the new bounds).
	 * 
	 * @param maxWidth
	 * @param maxHeight
	 */
	public void centerBy(int maxWidth, int maxHeight) {
		//if(getWidth() > maxWidth) // then what ??
		int offsetHor = (int)((maxWidth - getWidth()) / 2.0f);
		
		//if(getHeight() > maxHeight) // then what ??
		int offsetVer = (int)((maxHeight - getHeight()) / 2.0f);
		
		setOffset((int) getHorizontalOffset() + offsetHor, (int) getVerticalOffset() + offsetVer);
	}
	

	// TODO: Implement
	//** Centers the viewport to the retangle (offsetHor, offsetVer -- (offsetVer + maxHeight), offsetHor + maxWidth).
	//public void centerTo(int maxWidth, int maxHeight, int offsetHor, int offsetVer);
	

	/**
	 * Clampscale means clamp but retain aspect ratio.
	 * 
	 * @param maxWidth
	 * @param maxHeight
	 */
	public void clampscale(int maxWidth, int maxHeight, boolean clampToCenter) {
		int currentWidth = (int) getWidth();
		int currentHeight = (int) getHeight();
		
		// Initialize with the current sizes
		int newWidth = currentWidth;
		int newHeight = currentHeight;
		
		float aspectRatio = calcAspectRatio(currentWidth, currentHeight);
		
		
		if(getWidth() > maxWidth) {
			// Clampscale the width
			
			newWidth = maxWidth;
			newHeight = (int) Math.ceil(newWidth / aspectRatio);
		}
		
		if(getHeight() > maxHeight) {
			// Clampscale the height
			
			newHeight = maxHeight;
			newWidth = (int) Math.ceil(newHeight / aspectRatio);
		}
		
		setSize(newWidth, newHeight);
		
		
		// TODO: Maybe remove this because where would you need it?
		if(clampToCenter) {
			
			int offHor = (int)((currentWidth - newWidth) / 2.0f);
			int offVer = (int)((currentHeight - newHeight) / 2.0f);
			
			setOffset((int) getHorizontalOffset() + offHor, (int) getVerticalOffset() + offVer);
		}
		
	}
	

	private float calcAspectRatio(float width, float height) {
		float res;
		if(getOrientation() == ORIENTATION_LANDSCAPE) {	// TODO: Fix: width or height could be 0
			res = width / height;
		}
		else {
			res = height / width;
		}
		
		return res;
	}


	/** Apply size and offset from the given viewport.
	 * 
	 * @param master
	 */
	public void applyMasterViewport(IScreenViewport master) {
		setSize((int) master.getWidth(), (int) master.getHeight());
		setOffset((int) master.getHorizontalOffset(), (int) master.getVerticalOffset());
	}
	
	private void limitToMaster(int masterWidth, int masterHeight, float masterOffsetHor, float masterOffsetVer) {	// Pass orientation too?
		// TODO: Implement
		// Store these values and make sure the viewport is limited to them after every change (of size, offset, orientation, aspect ratio etc.)
	}
	
	
	
	@Override
	public int getOrientation() {
		int result;
		if(bounds.height() > bounds.width()) {
			result = ORIENTATION_PORTRAIT;
		}
		else if(bounds.height() < bounds.width()) {
			result = ORIENTATION_LANDSCAPE;
		}
		else {
			result = ORIENTATION_NONE;
		}
		
		return result;
	}

	
	@Override
	public float getWidth() {
		return bounds.width();
	}

	@Override
	public float getHeight() {
		return bounds.height();
	}

	
	@Override
	public float getHorizontalOffset() {
		return bounds.left();
	}

	@Override
	public float getVerticalOffset() {
		return bounds.top();
	}

	
	@Override
	public IRectangle2f getBounds() {
		return bounds;
	}
	
	

	@Override
	public float transformX(float sx) {
		return (int) (bounds.left() + sx);
	}

	@Override
	public float transformY(float sy) {
		return (int) (bounds.top() + sy);
	}

	@Override
	public void transform(float sx, float sy, IMutablePoint2f vResult) {
		vResult.set(transformX(sx), transformY(sy));
	}

	@Override
	public void transform(IPoint2f sPoint, IMutablePoint2f vResult) {
		vResult.set(transformX(sPoint.x()), transformY(sPoint.y()));
	}

	@Override
	public void transform(IRectangle2f sRect, IMutableRectangle2f vResult) {
		vResult.set(transformX(sRect.left()), transformY(sRect.top()), transformX(sRect.right()), transformY(sRect.bottom()));
	}

	
	@Override
	public float resolveX(float vx) {
		return (int) (vx - bounds.left());
	}

	@Override
	public float resolveY(float vy) {
		return (int) (vy - bounds.top());
	}

	@Override
	public void resolve(float vx, float vy, IMutablePoint2f sResult) {
		sResult.set(resolveX(vx), resolveY(vy));
	}
	
	@Override
	public void resolve(IPoint2f vPoint, IMutablePoint2f sResult) {
		sResult.set(resolveX(vPoint.x()), resolveY(vPoint.y()));
	}

	@Override
	public void resolve(IRectangle2f vRect, IMutableRectangle2f sResult) {
		sResult.set(resolveX(vRect.left()), resolveY(vRect.top()), resolveX(vRect.right()), resolveY(vRect.bottom()));
	}

	
}
