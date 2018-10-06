package com.tokelon.toktales.core.game.model.map;

public interface IBlockMapConfig {

	
	public String getConfigMapTitle();
	
	public int getConfigMapSpawnX();
	public int getConfigMapSpawnY();
	
	
	
	public class EmptyBlockMapConfig implements IBlockMapConfig {
		
		@Override
		public String getConfigMapTitle() {
			return "";
		}
		
		@Override
		public int getConfigMapSpawnX() {
			return 0;
		}
		
		@Override
		public int getConfigMapSpawnY() {
			return 0;
		}
	}
	
}
