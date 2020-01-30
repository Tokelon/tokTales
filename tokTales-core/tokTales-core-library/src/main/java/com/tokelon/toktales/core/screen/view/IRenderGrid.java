package com.tokelon.toktales.core.screen.view;

public interface IRenderGrid {
	
	// TODO: Why is this not used? Use or remove
	
	// Add shift

	public int getActualBlockWidth();
	
	public int getActualBlockHeight();
	
	
	public int getGridBlockWidth();
	
	public int getGridBlockHeight();
	
	
	public int getBlockPixelSize();
	
	public int getGridPixelWidth();
	
	public int getGridPixelHeight();
	
	
	public IShift getXAxisShift();
	public IShift getYAxisShift();
	
	
	public interface IShift {
		
		//public static final int DIRECTION_POSITIVE = 1;
		//public static final int DIRECTION_NEGATIVE = 2;
		
		public boolean isShifted();
		
		public int getShiftValue();
		public void setShiftValue(int value);
		
	}
	
}
