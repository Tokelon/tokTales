package com.tokelon.toktales.core.game.model;

import com.tokelon.toktales.core.game.model.IPoint2f.IMutablePoint2f;

public interface IPolyline2f {

	
	public int getPointCount();
	
	public void getPointAt(int index, IMutablePoint2f result);
	
	public float getXAt(int index);
	
	public float getYAt(int index);
	
	
	public interface IMutablePolyline2f extends IPolyline2f {
		
		public void setPointAt(int index, float px, float py);
		public void setPointAt(int index, IPoint2f point);
	}
	
	public interface IExtendablePolyline2f extends IMutablePolyline2f {
		
		public void removePointAt(int index);
		
		public void addPointAt(int index);
		public void addPointAt(int index, float px, float py);
		public void addPointAt(int index, IPoint2f point);
	}
	
	
}
