package com.tokelon.toktales.core.game.model.map;

import com.tokelon.toktales.core.content.sprite.ISprite;
import com.tokelon.toktales.core.game.world.IWorldObject;

public interface IMapObject extends IWorldObject {

	
	// object id
	public int getID();
	
	
	// tile id
	public boolean hasGID();
	public int getGID();
	
	public boolean hasSprite();
	public ISprite getSprite();
	
	// Properties! TODO
	//public ITiledMapProperties getProperties();

	
	
	
	
}
