package com.tokelon.toktales.core.game.world;

public interface ICrossDirection {

	
	public static final int UP = 1;
	public static final int RIGHT = 2;
	public static final int DOWN = 3;
	public static final int LEFT = 4;
	
	
	
	
	public static final class Tools {
		private Tools() {}
		
		
		public static int horizontalVelocitySignFromDirection(int crossDirection) {
			
			switch (crossDirection) {
			case RIGHT:
				return 1;
			case LEFT:
				return -1;
			case UP:
			case DOWN:
			default:
				return 0;
			}
		}
		
		public static int verticalVelocitySignFromDirection(int crossDirection) {
			switch (crossDirection) {
			case UP:
				return 1;
			case DOWN:
				return -1;
			case RIGHT:
			case LEFT:
			default:
				return 0;
			}
		}
		
		// TODO: Remove and do better outside
		public static int verticalVelocitySignFromDirectionInvertY(int crossDirection) {
			switch (crossDirection) {
			case DOWN:
				return 1;
			case UP:
				return -1;
			case RIGHT:
			case LEFT:
			default:
				return 0;
			}
		}
	}
	
}
