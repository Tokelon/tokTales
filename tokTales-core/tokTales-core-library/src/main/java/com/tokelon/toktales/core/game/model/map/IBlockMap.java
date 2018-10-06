package com.tokelon.toktales.core.game.model.map;

import com.tokelon.toktales.core.game.world.IObjectContainer;
import com.tokelon.toktales.core.resources.IResourceSet;


public interface IBlockMap { //extends IDataPlace<DataRead, DataWrite> {	// extends IGameMap

	public IObjectContainer<IMapObject> getObjectContainer();

	public IBlockMapConfig getConfig();
	
	public String getName();
	
	public boolean hasFileNameAttached();
	public String getFileName();
	
	public int getVerticalSize();
	public int getHorizontalSize();
	
	public IResourceSet getResources();

	public ILevelReference getLevelReference();		// maybe move into map controller?
	
	
	public IBlock getBlockAt(int posx, int posy);
	public IBlock getBlockAt(IMapPosition position);
	
	
	public IMapLayer getLayerOnLevel(int level);
	public IMapLayer getLayerForName(String name);
	
	// TODO: Refactor layer logic if neccessary, optimize implementation, rename methods, javadoc
	public int getLevelForLayer(String name);
	
	
	/*
	public void lock();
	
	public void unlock();
	*/
	
	
	
	//public void populate(IBLKMapPopulator populator);
	
	
	/*
	public interface DataRead extends IDataRead {
		
		//public IBlock getBlockAt(int posx, int posy);

		public IBlock startReadBlockAt(int posx, int posy);

		public void endReadBlock(IBlock block);
		
		
		
		
		public IBlock manualReadAt(int posx, int posy);
		
		
		//public IBlock lockBlock(int lockType, IMapPosition position);
		
		//public IBlockRegion lockBlockRegion(int lockType, IBlockRegion region);
		
		//public void unlockBlock(IMapPosition position);
		
	}
	
	public interface DataWrite extends IDataWrite {

		//public IBlock editBlockAt(int posx, int posy);

		public IBlock startEditBlockAt(int posx, int posy);

		public void endEditBlock(IBlock block);
	
		
		public IBlock manualEditAt(int posx, int posy);
		
	}
	
	*/

}
