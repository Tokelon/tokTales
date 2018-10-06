package com.tokelon.toktales.core.game.world;


public interface IWorld {

	//public static final int WORLD_GRID_TILE_SIZE = 32;
	
	/*
	 * WARNING: Making this number smaller than 1.0 will result in errors in calculations
	 * Meaning: It will break stuff
	 */
	public static final float GRID_TILE_SIZE = 32.0f;
	
	//public static final IWorldGrid WORLD_GRID = new WorldGrid(GRID_TILE_SIZE);
	

	
	public float getGridTileSize();
	
	public IWorldGrid getGrid();
	
	
	
	
	
	
	public static final class Tools {
		
		private Tools() {}
	
		
		/*
		public static int tileIndexFromPercentage(int percval) {
			return (int) (percval * WORLD_GRID_TILE_SIZE / 100.0);
		}
		*/
		
		
		
		/*
		
		public static int coordinateForGridIndex(int index) {
			return WORLD_GRID_TILE_SIZE * index;
		}
		

		public static void mapPositionForWorldCoordinate(IPoint worldCoord, IMutableMapPosition result) {
			result.set(worldCoord.x() / WORLD_GRID_TILE_SIZE, worldCoord.y() / WORLD_GRID_TILE_SIZE);
		}
		
		*/
		
		private void tileSizeTesting() {

			double doubleGood = 5 * 0.32;
			
			float floatBad = 5f * 0.32f;
			float floatGood = (float) (5 * 0.32);
			
			
			int index = 5;
			float findex = 5.0f;
			double dindex = 5.0;
			
			float fTS = 0.32f;
			double dTS = 0.32;
			
			
			float fR = fTS * index;		//1.5999999
			double dfR = fTS * index;	//1.5999999046325684
			double dR = dTS * index;	//1.6
			
			float ffR = fTS * findex;	//1.5999999
			double dffR = dTS * findex;	//1.6
			
			float ffdR = (float) (fTS * dindex);	//1.5999999
			
		}
		
		
	}
	
}
