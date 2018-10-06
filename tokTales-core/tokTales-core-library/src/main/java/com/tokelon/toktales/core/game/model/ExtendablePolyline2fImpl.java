package com.tokelon.toktales.core.game.model;

import java.util.ArrayList;
import java.util.List;

import com.tokelon.toktales.core.game.model.IPoint2f.IMutablePoint2f;
import com.tokelon.toktales.core.game.model.IPolyline2f.IExtendablePolyline2f;

public class ExtendablePolyline2fImpl implements IExtendablePolyline2f {


	private final List<IMutablePoint2f> points;
	
	public ExtendablePolyline2fImpl() {
		points = new ArrayList<IMutablePoint2f>();
	}
	

	@Override
	public int getPointCount() {
		return points.size();
	}

	@Override
	public void getPointAt(int index, IMutablePoint2f result) {
		result.set(points.get(index));
	}

	@Override
	public float getXAt(int index) {
		return points.get(index).x();
	}

	@Override
	public float getYAt(int index) {
		return points.get(index).y();
	}
	
	
	@Override
	public void setPointAt(int index, float px, float py) {
		points.get(index).set(px, py);
	}

	@Override
	public void setPointAt(int index, IPoint2f point) {
		points.get(index).set(point);
	}

	
	@Override
	public void removePointAt(int index) {
		points.remove(index);
	}

	@Override
	public void addPointAt(int index) {
		points.add(index, new Point2fImpl());
	}

	@Override
	public void addPointAt(int index, float px, float py) {
		points.add(index, new Point2fImpl(px, py));
	}

	@Override
	public void addPointAt(int index, IPoint2f point) {
		points.add(index, new Point2fImpl(point.x(), point.y()));
	}

	
}
