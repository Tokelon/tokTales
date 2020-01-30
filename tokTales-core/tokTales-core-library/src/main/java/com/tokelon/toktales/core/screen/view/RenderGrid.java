package com.tokelon.toktales.core.screen.view;

public class RenderGrid implements IRenderGrid {

	private final Shift xAxisShift = new Shift();
	private final Shift yAxisShift = new Shift();
	
	private final int gridBlockWidth;
	private final int gridBlockHeight;
	
	private final int blockPixelSize;
	
	private final int gridPixelWidth;
	private final int gridPixelHeight;
	
	
	public RenderGrid(int gridBlockWidth, int gridBlockHeight, int blockPixelSize) {
		if(gridBlockWidth < 1 || gridBlockHeight < 1 || blockPixelSize < 1) {
			throw new IllegalArgumentException("All sizes must be positive");
		}
		
		this.gridBlockWidth = gridBlockWidth;
		this.gridBlockHeight = gridBlockHeight;
		this.blockPixelSize = blockPixelSize;
		this.gridPixelWidth = gridBlockWidth * blockPixelSize;
		this.gridPixelHeight = gridBlockHeight * blockPixelSize;
	}
	

	
	
	@Override
	public int getActualBlockWidth() {
		return xAxisShift.isShifted() ? gridBlockWidth + 1 : gridBlockWidth;
	}
	
	@Override
	public int getActualBlockHeight() {
		return yAxisShift.isShifted() ? gridBlockHeight + 1 : gridBlockHeight;
	}
	

	@Override
	public int getGridBlockWidth() {
		return gridBlockWidth;
	}

	@Override
	public int getGridBlockHeight() {
		return gridBlockHeight;
	}


	@Override
	public int getBlockPixelSize() {
		return blockPixelSize;
	}


	@Override
	public int getGridPixelWidth() {
		return gridPixelWidth;
	}
	
	@Override
	public int getGridPixelHeight() {
		return gridPixelHeight;
	}
	
	
	@Override
	public IShift getXAxisShift() {
		return xAxisShift; 
	}
	
	@Override
	public IShift getYAxisShift() {
		return yAxisShift;
	}
	
	
	public class Shift implements IShift {

		private int shiftValue = 0;
		
		@Override
		public boolean isShifted() {
			return shiftValue != 0;
		}

		@Override
		public int getShiftValue() {
			return shiftValue;
		}

		@Override
		public void setShiftValue(int value) {
			this.shiftValue = value;
		}
		
	}
	
	
	/* TODO: We are casting viewport sizes into ints here,
	 * so we are not considering fractual values - is this a problem ?
	 * 
	 * do we need to case after calculation ?
	 * 
	 */
	
	
	public static RenderGrid createFromViewportAndGridBlockSizes(IScreenViewport viewport, int gridBlockSizeShort, int gridBlockSizeLong) {
		if(gridBlockSizeShort < 1 || gridBlockSizeLong < 1) {
			throw new IllegalArgumentException("All sizes must be positive");
		}
		if(viewport == null) {
			throw new NullPointerException();
		}
		
		
		int blockPixelSize, gridBlockWidth, gridBlockHeight;
		
		if(viewport.getOrientation() == IScreenViewport.ORIENTATION_LANDSCAPE) {
			blockPixelSize = (int) viewport.getHeight() / gridBlockSizeShort;
			
			gridBlockWidth = gridBlockSizeLong;
			gridBlockHeight = gridBlockSizeShort;
		}
		else {
			blockPixelSize = (int) viewport.getWidth() / gridBlockSizeShort;
			
			gridBlockWidth = gridBlockSizeShort;
			gridBlockHeight = gridBlockSizeLong;
		}

		
		return new RenderGrid(gridBlockWidth, gridBlockHeight, blockPixelSize);
	}
	
	
	public static RenderGrid createFromViewportAndBlockPixelSize(IScreenViewport viewport, int blockPixelSize) {
		if(blockPixelSize < 1) {
			throw new IllegalArgumentException("All sizes must be positive");
		}
		if(viewport == null) {
			throw new NullPointerException();
		}
		
		
		int gridBlockWidth = (int) viewport.getWidth() / blockPixelSize;
		int gridBlockHeight = (int) viewport.getHeight() / blockPixelSize;

		
		return new RenderGrid(gridBlockWidth, gridBlockHeight, blockPixelSize);
	}
	
	
	public static RenderGrid createFromViewportAndShortGridBlockSize(IScreenViewport viewport, int shortGridBlockSize) {
		if(shortGridBlockSize < 1) {
			throw new IllegalArgumentException("All sizes must be positive");
		}
		if(viewport == null) {
			throw new NullPointerException();
		}
		
		
		int blockPixelSize, gridBlockWidth, gridBlockHeight;
		if(viewport.getOrientation() == IScreenViewport.ORIENTATION_LANDSCAPE) {
			blockPixelSize = (int) viewport.getHeight() / shortGridBlockSize;
		}
		else {
			blockPixelSize = (int) viewport.getWidth() / shortGridBlockSize;
		}

		gridBlockHeight = (int) viewport.getHeight() / blockPixelSize;
		gridBlockWidth = (int) viewport.getWidth() / blockPixelSize;
		
		
		return new RenderGrid(gridBlockWidth, gridBlockHeight, blockPixelSize);
	}
	

}
