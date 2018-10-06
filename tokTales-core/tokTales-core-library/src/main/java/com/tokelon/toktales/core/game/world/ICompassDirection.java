package com.tokelon.toktales.core.game.world;

public interface ICompassDirection {

	
	public static final int NORTH = 1;
	public static final int EAST = 2;
	public static final int SOUTH = 3;
	public static final int WEST = 4;
	public static final int NORTH_EAST = 5;
	public static final int SOUTH_EAST = 6;
	public static final int SOUTH_WEST = 7;
	public static final int NORTH_WEST = 8;
	
	
	
	
	public static final class Tools {
		private Tools() {}
		
		
		public static int horizontalVelocitySignFromDirection(int compassDirection) {
			switch (compassDirection) {
			case EAST:
			case NORTH_EAST:
			case SOUTH_EAST:
				return 1;
			case WEST:
			case SOUTH_WEST:
			case NORTH_WEST:
				return -1;
			default:
			case NORTH:
			case SOUTH:
				return 0;
			}
		}
		
		public static int verticalVelocitySignFromDirection(int compassDirection) {
			switch (compassDirection) {
			case NORTH:
			case NORTH_WEST:
			case NORTH_EAST:
				return 1;
			case SOUTH:
			case SOUTH_EAST:
			case SOUTH_WEST:
				return -1;
			default:
			case WEST:
			case EAST:
				return 0;
			}
		}
		
		
		// TODO: Remove and do better outside
		public static int verticalVelocitySignFromDirectionInvertY(int compassDirection) {
			switch (compassDirection) {
			case SOUTH:
			case SOUTH_EAST:
			case SOUTH_WEST:
				return 1;
			case NORTH:
			case NORTH_WEST:
			case NORTH_EAST:
				return -1;
			default:
			case WEST:
			case EAST:
				return 0;
			}
		}
	}
	
}
