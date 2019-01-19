package com.tokelon.toktales.core.game.model.map;

import com.tokelon.toktales.core.game.world.IObjectContainer;
import com.tokelon.toktales.core.resources.IResourceSet;

public interface IBlockMap { // extends IGameMap

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

}
